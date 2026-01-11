package com.example.timetable.service;

import com.example.timetable.domain.AcademicYear;
import com.example.timetable.domain.Department;
import com.example.timetable.domain.Semester;
import com.example.timetable.dto.SemesterDto;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.repository.AcademicYearRepository;
import com.example.timetable.repository.DepartmentRepository;
import com.example.timetable.repository.SemesterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SemesterService {

  private final SemesterRepository semesterRepository;
  private final AcademicYearRepository academicYearRepository;
  private final DepartmentRepository departmentRepository;

  @Transactional(readOnly = true)
  public List<SemesterDto> findAll() {
    return semesterRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional
  public SemesterDto create(SemesterDto dto) {
    Semester entity = toEntity(dto);
    entity.setId(null);
    return toDto(semesterRepository.save(entity));
  }

  @Transactional
  public SemesterDto update(Long id, SemesterDto dto) {
    Semester existing = semesterRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));
    existing.setNumber(dto.getNumber());
    existing.setAcademicYear(fetchAcademicYear(dto.getAcademicYearId()));
    existing.setDepartment(fetchDepartment(dto.getDepartmentId()));
    return toDto(semesterRepository.save(existing));
  }

  @Transactional
  public void delete(Long id) {
    if (!semesterRepository.existsById(id)) {
      throw new ResourceNotFoundException("Semester not found");
    }
    semesterRepository.deleteById(id);
  }

  private SemesterDto toDto(Semester entity) {
    SemesterDto dto = new SemesterDto();
    dto.setId(entity.getId());
    dto.setNumber(entity.getNumber());
    dto.setAcademicYearId(entity.getAcademicYear().getId());
    dto.setDepartmentId(entity.getDepartment().getId());
    dto.setName("Semester " + entity.getNumber());
    return dto;
  }

  private Semester toEntity(SemesterDto dto) {
    AcademicYear ay = fetchAcademicYear(dto.getAcademicYearId());
    Department dept = fetchDepartment(dto.getDepartmentId());
    return Semester.builder()
        .id(dto.getId())
        .number(dto.getNumber())
        .academicYear(ay)
        .department(dept)
        .build();
  }

  private AcademicYear fetchAcademicYear(Long id) {
    return academicYearRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Academic year not found"));
  }

  private Department fetchDepartment(Long id) {
    return departmentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
  }
}
