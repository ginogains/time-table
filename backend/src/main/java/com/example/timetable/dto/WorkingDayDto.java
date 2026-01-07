package com.example.timetable.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkingDayDto {
  private Long id;
  @NotBlank
  private String name;
  @NotNull
  private Integer orderIndex;
}
