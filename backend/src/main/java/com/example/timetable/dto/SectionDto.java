package com.example.timetable.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionDto {
  private Long id;
  @NotBlank
  private String name;
  @NotNull
  private Integer year;
  @NotNull
  private Long semesterId;
  @NotNull
  private Long departmentId;
  private Integer strength;
}
