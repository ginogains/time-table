package com.example.timetable.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeSlotDto {
  private Long id;
  @NotNull
  private Long workingDayId;
  @NotNull
  private LocalTime startTime;
  @NotNull
  private LocalTime endTime;
  @NotNull
  private Integer slotIndex;
}
