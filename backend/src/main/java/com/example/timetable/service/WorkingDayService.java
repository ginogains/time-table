package com.example.timetable.service;

import com.example.timetable.domain.WorkingDay;
import com.example.timetable.dto.WorkingDayDto;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.repository.WorkingDayRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkingDayService {

  private final WorkingDayRepository workingDayRepository;

  @Transactional(readOnly = true)
  public List<WorkingDayDto> findAll() {
    return workingDayRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional
  public WorkingDayDto create(WorkingDayDto dto) {
    WorkingDay entity = toEntity(dto);
    entity.setId(null);
    return toDto(workingDayRepository.save(entity));
  }

  @Transactional
  public WorkingDayDto update(Long id, WorkingDayDto dto) {
    WorkingDay existing = workingDayRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Working day not found"));
    existing.setName(dto.getName());
    existing.setOrderIndex(dto.getOrderIndex());
    return toDto(workingDayRepository.save(existing));
  }

  @Transactional
  public void delete(Long id) {
    if (!workingDayRepository.existsById(id)) {
      throw new ResourceNotFoundException("Working day not found");
    }
    workingDayRepository.deleteById(id);
  }

  private WorkingDayDto toDto(WorkingDay entity) {
    WorkingDayDto dto = new WorkingDayDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setOrderIndex(entity.getOrderIndex());
    return dto;
  }

  private WorkingDay toEntity(WorkingDayDto dto) {
    return WorkingDay.builder()
        .id(dto.getId())
        .name(dto.getName())
        .orderIndex(dto.getOrderIndex())
        .build();
  }
}
