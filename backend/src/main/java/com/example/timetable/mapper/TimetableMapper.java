package com.example.timetable.mapper;

import com.example.timetable.domain.Timetable;
import com.example.timetable.dto.TimetableDto;

public final class TimetableMapper {

  private TimetableMapper() {}

  public static TimetableDto toDto(Timetable entity) {
    TimetableDto dto = new TimetableDto();
    dto.setId(entity.getId());
    dto.setDepartmentId(entity.getDepartment().getId());
    dto.setSemesterId(entity.getSemester().getId());
    dto.setGeneratedAt(entity.getGeneratedAt());
    dto.setStatus(entity.getStatus());
    return dto;
  }
}
