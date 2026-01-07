package com.example.timetable.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateTimetableRequest {
  @NotNull
  private Long departmentId;
  @NotNull
  private Long semesterId;
  @NotEmpty
  private List<Long> sectionIds;
  private boolean allowSoftConstraintViolations;
}
