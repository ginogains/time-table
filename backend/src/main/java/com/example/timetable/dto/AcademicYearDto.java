package com.example.timetable.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcademicYearDto {
  private Long id;
  @NotBlank
  private String name;
  @NotNull
  private LocalDate startDate;
  @NotNull
  private LocalDate endDate;
}
