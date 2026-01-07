package com.example.timetable.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
  private Long id;
  @NotBlank
  private String name;
  private String type;
  private Integer capacity;
  private boolean lab;
  private Long departmentId;
}
