package com.example.timetable.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacultyDto {
  private Long id;
  @NotBlank
  private String code;
  @NotBlank
  private String name;
  private String email;
  private String designation;
  private Integer maxHoursPerDay;
  private Integer maxHoursPerWeek;
}
