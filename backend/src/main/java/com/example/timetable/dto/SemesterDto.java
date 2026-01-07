package com.example.timetable.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SemesterDto {
  private Long id;
  @NotNull
  private Integer number;
  @NotNull
  private Long academicYearId;
  @NotNull
  private Long departmentId;
}
