package com.example.timetable.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectDto {
  private Long id;

  @NotBlank
  private String code;

  @NotBlank
  private String name;

  private String type;
  private Integer credits;
  private Integer weeklyLectureHours;
  private Integer weeklyLabHours;
  private boolean lab;
}
