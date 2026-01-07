package com.example.timetable.mapper;

import com.example.timetable.domain.Subject;
import com.example.timetable.dto.SubjectDto;

public final class SubjectMapper {

  private SubjectMapper() {}

  public static SubjectDto toDto(Subject entity) {
    SubjectDto dto = new SubjectDto();
    dto.setId(entity.getId());
    dto.setCode(entity.getCode());
    dto.setName(entity.getName());
    dto.setType(entity.getType());
    dto.setCredits(entity.getCredits());
    dto.setWeeklyLectureHours(entity.getWeeklyLectureHours());
    dto.setWeeklyLabHours(entity.getWeeklyLabHours());
    dto.setLab(entity.isLab());
    return dto;
  }

  public static Subject toEntity(SubjectDto dto) {
    return Subject.builder()
        .id(dto.getId())
        .code(dto.getCode())
        .name(dto.getName())
        .type(dto.getType())
        .credits(dto.getCredits())
        .weeklyLectureHours(dto.getWeeklyLectureHours())
        .weeklyLabHours(dto.getWeeklyLabHours())
        .lab(dto.isLab())
        .build();
  }
}
