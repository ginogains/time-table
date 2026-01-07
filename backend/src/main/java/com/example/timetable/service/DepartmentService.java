package com.example.timetable.service;

import com.example.timetable.domain.Department;
import com.example.timetable.dto.DepartmentDto;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.repository.DepartmentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

  private final DepartmentRepository departmentRepository;

  @Transactional(readOnly = true)
  public List<DepartmentDto> findAll() {
    return departmentRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional
  public DepartmentDto create(DepartmentDto dto) {
    Department entity = toEntity(dto);
    entity.setId(null);
    return toDto(departmentRepository.save(entity));
  }

  @Transactional
  public DepartmentDto update(Long id, DepartmentDto dto) {
    Department existing = departmentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    existing.setName(dto.getName());
    existing.setCode(dto.getCode());
    return toDto(departmentRepository.save(existing));
  }

  @Transactional
  public void delete(Long id) {
    if (!departmentRepository.existsById(id)) {
      throw new ResourceNotFoundException("Department not found");
    }
    departmentRepository.deleteById(id);
  }

  private DepartmentDto toDto(Department entity) {
    DepartmentDto dto = new DepartmentDto();
    dto.setId(entity.getId());
    dto.setCode(entity.getCode());
    dto.setName(entity.getName());
    return dto;
  }

  private Department toEntity(DepartmentDto dto) {
    return Department.builder()
        .id(dto.getId())
        .code(dto.getCode())
        .name(dto.getName())
        .build();
  }
}
