package com.example.timetable.service;

import com.example.timetable.domain.Subject;
import com.example.timetable.dto.SubjectDto;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.mapper.SubjectMapper;
import com.example.timetable.repository.SubjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubjectService {

  private final SubjectRepository subjectRepository;

  @Transactional(readOnly = true)
  public List<SubjectDto> findAll() {
    return subjectRepository.findAll().stream().map(SubjectMapper::toDto).toList();
  }

  @Transactional
  public SubjectDto create(SubjectDto dto) {
    Subject entity = SubjectMapper.toEntity(dto);
    entity.setId(null);
    return SubjectMapper.toDto(subjectRepository.save(entity));
  }

  @Transactional
  public SubjectDto update(Long id, SubjectDto dto) {
    Subject existing = subjectRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
    existing.setName(dto.getName());
    existing.setType(dto.getType());
    existing.setCredits(dto.getCredits());
    existing.setWeeklyLectureHours(dto.getWeeklyLectureHours());
    existing.setWeeklyLabHours(dto.getWeeklyLabHours());
    existing.setLab(dto.isLab());
    existing.setCode(dto.getCode());
    return SubjectMapper.toDto(subjectRepository.save(existing));
  }

  @Transactional
  public void delete(Long id) {
    if (!subjectRepository.existsById(id)) {
      throw new ResourceNotFoundException("Subject not found");
    }
    subjectRepository.deleteById(id);
  }
}
