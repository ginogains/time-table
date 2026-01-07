package com.example.timetable.service;

import com.example.timetable.domain.TimetableEntry;
import com.example.timetable.dto.TimetableEntryView;
import com.example.timetable.repository.TimetableEntryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TimetableQueryService {

  private final TimetableEntryRepository timetableEntryRepository;

  @Transactional(readOnly = true)
  public List<TimetableEntryView> getClassView(Long timetableId, Long sectionId) {
    return timetableEntryRepository.findByTimetableIdAndSectionId(timetableId, sectionId)
        .stream().map(this::toView).toList();
  }

  @Transactional(readOnly = true)
  public List<TimetableEntryView> getFacultyView(Long timetableId, Long facultyId) {
    return timetableEntryRepository.findByTimetableIdAndFacultyId(timetableId, facultyId)
        .stream().map(this::toView).toList();
  }

  @Transactional(readOnly = true)
  public List<TimetableEntryView> getDepartmentView(Long timetableId) {
    return timetableEntryRepository.findByTimetableId(timetableId)
        .stream().map(this::toView).toList();
  }

  private TimetableEntryView toView(TimetableEntry e) {
    TimetableEntryView v = new TimetableEntryView();
    v.setTimeSlotId(e.getTimeSlot().getId());
    v.setDayName(e.getTimeSlot().getWorkingDay().getName());
    v.setSlotIndex(e.getTimeSlot().getSlotIndex());
    v.setSubjectCode(e.getSubject().getCode());
    v.setSubjectName(e.getSubject().getName());
    v.setFacultyName(e.getFaculty().getName());
    v.setRoomName(e.getRoom().getName());
    v.setSectionId(e.getSection().getId());
    v.setSectionName(e.getSection().getName());
    v.setFacultyId(e.getFaculty().getId());
    return v;
  }
}
