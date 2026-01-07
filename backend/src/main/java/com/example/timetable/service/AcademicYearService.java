package com.example.timetable.service;

import com.example.timetable.domain.AcademicYear;
import com.example.timetable.dto.AcademicYearDto;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.repository.AcademicYearRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AcademicYearService {

  private final AcademicYearRepository academicYearRepository;

  @Transactional(readOnly = true)
  public List<AcademicYearDto> findAll() {
    return academicYearRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional
  public AcademicYearDto create(AcademicYearDto dto) {
    AcademicYear entity = toEntity(dto);
    entity.setId(null);
    return toDto(academicYearRepository.save(entity));
  }

  @Transactional
  public AcademicYearDto update(Long id, AcademicYearDto dto) {
    AcademicYear existing = academicYearRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Academic year not found"));
    existing.setName(dto.getName());
    existing.setStartDate(dto.getStartDate());
    existing.setEndDate(dto.getEndDate());
    return toDto(academicYearRepository.save(existing));
  }

  @Transactional
  public void delete(Long id) {
    if (!academicYearRepository.existsById(id)) {
      throw new ResourceNotFoundException("Academic year not found");
    }
    academicYearRepository.deleteById(id);
  }

  private AcademicYearDto toDto(AcademicYear entity) {
    AcademicYearDto dto = new AcademicYearDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setStartDate(entity.getStartDate());
    dto.setEndDate(entity.getEndDate());
    return dto;
  }

  private AcademicYear toEntity(AcademicYearDto dto) {
    return AcademicYear.builder()
        .id(dto.getId())
        .name(dto.getName())
        .startDate(dto.getStartDate())
        .endDate(dto.getEndDate())
        .build();
  }
}
