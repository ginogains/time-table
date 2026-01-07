package com.example.timetable.dto;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimetableDto {
  private Long id;
  private Long departmentId;
  private Long semesterId;
  private Instant generatedAt;
  private String status;
}
