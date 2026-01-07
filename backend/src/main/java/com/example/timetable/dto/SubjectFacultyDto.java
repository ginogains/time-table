package com.example.timetable.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectFacultyDto {
  private Long id;
  @NotNull
  private Long subjectId;
  @NotNull
  private Long facultyId;
  @NotNull
  private Long sectionId;
  private Integer priority;
}
