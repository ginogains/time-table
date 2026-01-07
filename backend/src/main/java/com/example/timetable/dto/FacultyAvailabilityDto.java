package com.example.timetable.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FacultyAvailabilityDto {
  private Long id;
  @NotNull
  private Long facultyId;
  @NotNull
  private Long timeSlotId;
  private boolean available;
}
