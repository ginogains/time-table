package com.example.timetable.service;

import com.example.timetable.domain.*;
import com.example.timetable.dto.GenerateTimetableRequest;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.repository.*;
import com.example.timetable.scheduler.AssignmentRequest;
import com.example.timetable.scheduler.ConstraintChecker;
import com.example.timetable.scheduler.ScheduleContext;
import com.example.timetable.scheduler.ScheduledSlot;
import java.time.Instant;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TimetableGeneratorService {

  private final ConstraintChecker constraintChecker;
  private final TimetableRepository timetableRepository;
  private final TimetableEntryRepository timetableEntryRepository;
  private final SectionRepository sectionRepository;
  private final SubjectRepository subjectRepository;
  private final FacultyRepository facultyRepository;
  private final RoomRepository roomRepository;
  private final WorkingDayRepository workingDayRepository;
  private final TimeSlotRepository timeSlotRepository;
  private final SubjectFacultyRepository subjectFacultyRepository;
  private final FacultyAvailabilityRepository facultyAvailabilityRepository;
  private final SemesterRepository semesterRepository;
  private final DepartmentRepository departmentRepository;

  @Transactional
  public Timetable generate(GenerateTimetableRequest req) {
    ScheduleContext ctx = loadContext(req);
    Map<Long, Set<Long>> facultyAvailabilityIndex = buildAvailabilityIndex(ctx.getFacultyAvailability());

    List<ScheduledSlot> solution = new ArrayList<>();
    List<AssignmentRequest> tasks = buildAssignmentRequests(ctx);

    boolean success = backtrack(0, tasks, ctx, facultyAvailabilityIndex, solution);
    if (!success) {
      throw new IllegalStateException("Could not generate clash-free timetable with given constraints");
    }

    Timetable timetable = Timetable.builder()
        .department(ctx.getDepartment())
        .semester(ctx.getSemester())
        .generatedAt(Instant.now())
        .status("COMPLETED")
        .build();
    timetableRepository.save(timetable);

    for (ScheduledSlot s : solution) {
      TimetableEntry entry = TimetableEntry.builder()
          .timetable(timetable)
          .section(s.getSection())
          .subject(s.getSubject())
          .faculty(s.getFaculty())
          .room(s.getRoom())
          .timeSlot(s.getTimeSlot())
          .labBlockStart(s.isLabBlockStart())
          .blockSize(s.getBlockSize())
          .build();
      timetableEntryRepository.save(entry);
    }

    return timetable;
  }

  private ScheduleContext loadContext(GenerateTimetableRequest req) {
    Department dept = departmentRepository.findById(req.getDepartmentId())
        .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    Semester sem = semesterRepository.findById(req.getSemesterId())
        .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));
    List<Section> sections = sectionRepository.findAllById(req.getSectionIds());
    List<Subject> subjects = subjectRepository.findAll();
    List<Faculty> faculty = facultyRepository.findAll();
    List<Room> rooms = roomRepository.findAll();
    List<WorkingDay> days = workingDayRepository.findAll();
    List<TimeSlot> slots = timeSlotRepository.findAll();
    List<SubjectFaculty> mappings = subjectFacultyRepository.findAll();
    List<FacultyAvailability> availability = facultyAvailabilityRepository.findAll();

    return ScheduleContext.builder()
        .department(dept)
        .semester(sem)
        .sections(sections)
        .subjects(subjects)
        .faculty(faculty)
        .rooms(rooms)
        .workingDays(days)
        .timeSlots(slots)
        .subjectFacultyMappings(mappings)
        .facultyAvailability(availability)
        .build();
  }

  private Map<Long, Set<Long>> buildAvailabilityIndex(List<FacultyAvailability> availabilities) {
    Map<Long, Set<Long>> result = new HashMap<>();
    for (FacultyAvailability fa : availabilities) {
      if (fa.isAvailable()) {
        result.computeIfAbsent(fa.getFaculty().getId(), k -> new HashSet<>())
            .add(fa.getTimeSlot().getId());
      }
    }
    return result;
  }

  private List<AssignmentRequest> buildAssignmentRequests(ScheduleContext ctx) {
    Map<Long, List<SubjectFaculty>> bySection = new HashMap<>();
    for (SubjectFaculty sf : ctx.getSubjectFacultyMappings()) {
      bySection.computeIfAbsent(sf.getSection().getId(), k -> new ArrayList<>()).add(sf);
    }
    List<AssignmentRequest> tasks = new ArrayList<>();
    for (Section section : ctx.getSections()) {
      List<SubjectFaculty> list = bySection.getOrDefault(section.getId(), List.of());
      for (SubjectFaculty sf : list) {
        int units = sf.getSubject().isLab()
            ? Optional.ofNullable(sf.getSubject().getWeeklyLabHours()).orElse(2) / 2
            : Optional.ofNullable(sf.getSubject().getWeeklyLectureHours()).orElse(2);
        tasks.add(AssignmentRequest.builder()
            .section(section)
            .candidates(List.of(sf))
            .unitsNeeded(Math.max(1, units))
            .labBlock(sf.getSubject().isLab())
            .build());
      }
    }
    tasks.sort(Comparator.comparing(AssignmentRequest::isLabBlock).reversed()
        .thenComparing(AssignmentRequest::getUnitsNeeded).reversed());
    return tasks;
  }

  private boolean backtrack(int idx,
                            List<AssignmentRequest> tasks,
                            ScheduleContext ctx,
                            Map<Long, Set<Long>> facultyAvailabilityIndex,
                            List<ScheduledSlot> current) {
    if (idx == tasks.size()) {
      return true;
    }

    AssignmentRequest task = tasks.get(idx);

    for (TimeSlot slot : ctx.getTimeSlots()) {
      for (Room room : ctx.getRooms()) {
        for (SubjectFaculty sf : task.getCandidates()) {
          Faculty faculty = sf.getFaculty();
          Section section = task.getSection();
          if (!constraintChecker.isSectionFree(current, section, slot)) continue;
          if (!constraintChecker.isFacultyFree(current, faculty, slot)) continue;
          if (!constraintChecker.isRoomFree(current, room, slot)) continue;
          if (!constraintChecker.isFacultyAvailable(facultyAvailabilityIndex, faculty, slot)) continue;

          ScheduledSlot scheduled = ScheduledSlot.builder()
              .section(section)
              .subject(sf.getSubject())
              .faculty(faculty)
              .room(room)
              .timeSlot(slot)
              .labBlockStart(task.isLabBlock())
              .blockSize(task.isLabBlock() ? 2 : 1)
              .build();

          current.add(scheduled);
          if (backtrack(idx + 1, tasks, ctx, facultyAvailabilityIndex, current)) {
            return true;
          }
          current.remove(current.size() - 1);
        }
      }
    }
    return false;
  }
}
