package com.example.timetable.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimetableEntryView {
  private Long timeSlotId;
  private String dayName;
  private Integer slotIndex;
  private String subjectCode;
  private String subjectName;
  private String facultyName;
  private String roomName;
  private Long sectionId;
  private String sectionName;
  private Long facultyId;
}
