package com.example.timetable.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RulesDto {
  private Long id;
  private Integer maxHoursPerDay;
  private Integer maxHoursPerWeek;
  private Integer maxConsecutiveHours;
  private Integer breakDuration;
}
