package com.example.timetable.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDto {
  private Long id;
  @NotBlank
  private String code;
  @NotBlank
  private String name;
}
