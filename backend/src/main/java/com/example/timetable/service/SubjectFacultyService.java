package com.example.timetable.service;

import com.example.timetable.domain.Faculty;
import com.example.timetable.domain.Section;
import com.example.timetable.domain.Subject;
import com.example.timetable.domain.SubjectFaculty;
import com.example.timetable.dto.SubjectFacultyDto;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.repository.FacultyRepository;
import com.example.timetable.repository.SectionRepository;
import com.example.timetable.repository.SubjectFacultyRepository;
import com.example.timetable.repository.SubjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubjectFacultyService {

  private final SubjectFacultyRepository subjectFacultyRepository;
  private final SubjectRepository subjectRepository;
  private final FacultyRepository facultyRepository;
  private final SectionRepository sectionRepository;

  @Transactional(readOnly = true)
  public List<SubjectFacultyDto> findAll() {
    return subjectFacultyRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional
  public SubjectFacultyDto create(SubjectFacultyDto dto) {
    SubjectFaculty entity = toEntity(dto);
    entity.setId(null);
    return toDto(subjectFacultyRepository.save(entity));
  }

  @Transactional
  public SubjectFacultyDto update(Long id, SubjectFacultyDto dto) {
    SubjectFaculty existing = subjectFacultyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subject-faculty mapping not found"));
    existing.setSubject(fetchSubject(dto.getSubjectId()));
    existing.setFaculty(fetchFaculty(dto.getFacultyId()));
    existing.setSection(fetchSection(dto.getSectionId()));
    existing.setPriority(dto.getPriority());
    return toDto(subjectFacultyRepository.save(existing));
  }

  @Transactional
  public void delete(Long id) {
    if (!subjectFacultyRepository.existsById(id)) {
      throw new ResourceNotFoundException("Subject-faculty mapping not found");
    }
    subjectFacultyRepository.deleteById(id);
  }

  private SubjectFacultyDto toDto(SubjectFaculty entity) {
    SubjectFacultyDto dto = new SubjectFacultyDto();
    dto.setId(entity.getId());
    dto.setSubjectId(entity.getSubject().getId());
    dto.setFacultyId(entity.getFaculty().getId());
    dto.setSectionId(entity.getSection().getId());
    dto.setPriority(entity.getPriority());
    return dto;
  }

  private SubjectFaculty toEntity(SubjectFacultyDto dto) {
    return SubjectFaculty.builder()
        .id(dto.getId())
        .subject(fetchSubject(dto.getSubjectId()))
        .faculty(fetchFaculty(dto.getFacultyId()))
        .section(fetchSection(dto.getSectionId()))
        .priority(dto.getPriority())
        .build();
  }

  private Subject fetchSubject(Long id) {
    return subjectRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
  }

  private Faculty fetchFaculty(Long id) {
    return facultyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
  }

  private Section fetchSection(Long id) {
    return sectionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
  }
}
