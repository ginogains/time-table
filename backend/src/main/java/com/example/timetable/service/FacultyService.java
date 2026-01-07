package com.example.timetable.service;

import com.example.timetable.domain.Faculty;
import com.example.timetable.dto.FacultyDto;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.repository.FacultyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FacultyService {

  private final FacultyRepository facultyRepository;

  @Transactional(readOnly = true)
  public List<FacultyDto> findAll() {
    return facultyRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional
  public FacultyDto create(FacultyDto dto) {
    Faculty entity = toEntity(dto);
    entity.setId(null);
    return toDto(facultyRepository.save(entity));
  }

  @Transactional
  public FacultyDto update(Long id, FacultyDto dto) {
    Faculty existing = facultyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
    existing.setCode(dto.getCode());
    existing.setName(dto.getName());
    existing.setEmail(dto.getEmail());
    existing.setDesignation(dto.getDesignation());
    existing.setMaxHoursPerDay(dto.getMaxHoursPerDay());
    existing.setMaxHoursPerWeek(dto.getMaxHoursPerWeek());
    return toDto(facultyRepository.save(existing));
  }

  @Transactional
  public void delete(Long id) {
    if (!facultyRepository.existsById(id)) {
      throw new ResourceNotFoundException("Faculty not found");
    }
    facultyRepository.deleteById(id);
  }

  private FacultyDto toDto(Faculty entity) {
    FacultyDto dto = new FacultyDto();
    dto.setId(entity.getId());
    dto.setCode(entity.getCode());
    dto.setName(entity.getName());
    dto.setEmail(entity.getEmail());
    dto.setDesignation(entity.getDesignation());
    dto.setMaxHoursPerDay(entity.getMaxHoursPerDay());
    dto.setMaxHoursPerWeek(entity.getMaxHoursPerWeek());
    return dto;
  }

  private Faculty toEntity(FacultyDto dto) {
    return Faculty.builder()
        .id(dto.getId())
        .code(dto.getCode())
        .name(dto.getName())
        .email(dto.getEmail())
        .designation(dto.getDesignation())
        .maxHoursPerDay(dto.getMaxHoursPerDay())
        .maxHoursPerWeek(dto.getMaxHoursPerWeek())
        .build();
  }
}
