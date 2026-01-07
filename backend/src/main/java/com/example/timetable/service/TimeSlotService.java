package com.example.timetable.service;

import com.example.timetable.domain.TimeSlot;
import com.example.timetable.domain.WorkingDay;
import com.example.timetable.dto.TimeSlotDto;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.repository.TimeSlotRepository;
import com.example.timetable.repository.WorkingDayRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

  private final TimeSlotRepository timeSlotRepository;
  private final WorkingDayRepository workingDayRepository;

  @Transactional(readOnly = true)
  public List<TimeSlotDto> findAll() {
    return timeSlotRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional
  public TimeSlotDto create(TimeSlotDto dto) {
    TimeSlot entity = toEntity(dto);
    entity.setId(null);
    return toDto(timeSlotRepository.save(entity));
  }

  @Transactional
  public TimeSlotDto update(Long id, TimeSlotDto dto) {
    TimeSlot existing = timeSlotRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Time slot not found"));
    existing.setWorkingDay(fetchDay(dto.getWorkingDayId()));
    existing.setStartTime(dto.getStartTime());
    existing.setEndTime(dto.getEndTime());
    existing.setSlotIndex(dto.getSlotIndex());
    return toDto(timeSlotRepository.save(existing));
  }

  @Transactional
  public void delete(Long id) {
    if (!timeSlotRepository.existsById(id)) {
      throw new ResourceNotFoundException("Time slot not found");
    }
    timeSlotRepository.deleteById(id);
  }

  private TimeSlotDto toDto(TimeSlot entity) {
    TimeSlotDto dto = new TimeSlotDto();
    dto.setId(entity.getId());
    dto.setWorkingDayId(entity.getWorkingDay().getId());
    dto.setStartTime(entity.getStartTime());
    dto.setEndTime(entity.getEndTime());
    dto.setSlotIndex(entity.getSlotIndex());
    return dto;
  }

  private TimeSlot toEntity(TimeSlotDto dto) {
    WorkingDay day = fetchDay(dto.getWorkingDayId());
    return TimeSlot.builder()
        .id(dto.getId())
        .workingDay(day)
        .startTime(dto.getStartTime())
        .endTime(dto.getEndTime())
        .slotIndex(dto.getSlotIndex())
        .build();
  }

  private WorkingDay fetchDay(Long id) {
    return workingDayRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Working day not found"));
    }
}
