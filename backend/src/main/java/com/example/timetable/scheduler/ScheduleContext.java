package com.example.timetable.scheduler;

import com.example.timetable.domain.*;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleContext {
  private Department department;
  private Semester semester;
  private List<Section> sections;
  private List<Subject> subjects;
  private List<Faculty> faculty;
  private List<Room> rooms;
  private List<WorkingDay> workingDays;
  private List<TimeSlot> timeSlots;
  private List<SubjectFaculty> subjectFacultyMappings;
  private List<FacultyAvailability> facultyAvailability;
}
