package com.example.timetable.service;

import com.example.timetable.domain.Department;
import com.example.timetable.domain.Section;
import com.example.timetable.domain.Semester;
import com.example.timetable.dto.SectionDto;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.repository.DepartmentRepository;
import com.example.timetable.repository.SectionRepository;
import com.example.timetable.repository.SemesterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SectionService {

  private final SectionRepository sectionRepository;
  private final SemesterRepository semesterRepository;
  private final DepartmentRepository departmentRepository;

  @Transactional(readOnly = true)
  public List<SectionDto> findAll() {
    return sectionRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional
  public SectionDto create(SectionDto dto) {
    Section entity = toEntity(dto);
    entity.setId(null);
    return toDto(sectionRepository.save(entity));
  }

  @Transactional
  public SectionDto update(Long id, SectionDto dto) {
    Section existing = sectionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
    existing.setName(dto.getName());
    existing.setYear(dto.getYear());
    existing.setSemester(fetchSemester(dto.getSemesterId()));
    existing.setDepartment(fetchDepartment(dto.getDepartmentId()));
    existing.setStrength(dto.getStrength());
    return toDto(sectionRepository.save(existing));
  }

  @Transactional
  public void delete(Long id) {
    if (!sectionRepository.existsById(id)) {
      throw new ResourceNotFoundException("Section not found");
    }
    sectionRepository.deleteById(id);
  }

  private SectionDto toDto(Section entity) {
    SectionDto dto = new SectionDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setYear(entity.getYear());
    dto.setSemesterId(entity.getSemester().getId());
    dto.setDepartmentId(entity.getDepartment().getId());
    dto.setStrength(entity.getStrength());
    return dto;
  }

  private Section toEntity(SectionDto dto) {
    Semester semester = fetchSemester(dto.getSemesterId());
    Department department = fetchDepartment(dto.getDepartmentId());
    return Section.builder()
        .id(dto.getId())
        .name(dto.getName())
        .year(dto.getYear())
        .semester(semester)
        .department(department)
        .strength(dto.getStrength())
        .build();
  }

  private Semester fetchSemester(Long id) {
    return semesterRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));
  }

  private Department fetchDepartment(Long id) {
    return departmentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
  }
}
