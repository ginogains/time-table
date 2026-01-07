package com.example.timetable.service;

import com.example.timetable.domain.Faculty;
import com.example.timetable.domain.FacultyAvailability;
import com.example.timetable.domain.TimeSlot;
import com.example.timetable.dto.FacultyAvailabilityDto;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.repository.FacultyAvailabilityRepository;
import com.example.timetable.repository.FacultyRepository;
import com.example.timetable.repository.TimeSlotRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FacultyAvailabilityService {

  private final FacultyAvailabilityRepository availabilityRepository;
  private final FacultyRepository facultyRepository;
  private final TimeSlotRepository timeSlotRepository;

  @Transactional(readOnly = true)
  public List<FacultyAvailabilityDto> findAll() {
    return availabilityRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional
  public FacultyAvailabilityDto create(FacultyAvailabilityDto dto) {
    FacultyAvailability entity = toEntity(dto);
    entity.setId(null);
    return toDto(availabilityRepository.save(entity));
  }

  @Transactional
  public FacultyAvailabilityDto update(Long id, FacultyAvailabilityDto dto) {
    FacultyAvailability existing = availabilityRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Faculty availability not found"));
    existing.setFaculty(fetchFaculty(dto.getFacultyId()));
    existing.setTimeSlot(fetchTimeSlot(dto.getTimeSlotId()));
    existing.setAvailable(dto.isAvailable());
    return toDto(availabilityRepository.save(existing));
  }

  @Transactional
  public void delete(Long id) {
    if (!availabilityRepository.existsById(id)) {
      throw new ResourceNotFoundException("Faculty availability not found");
    }
    availabilityRepository.deleteById(id);
  }

  private FacultyAvailabilityDto toDto(FacultyAvailability entity) {
    FacultyAvailabilityDto dto = new FacultyAvailabilityDto();
    dto.setId(entity.getId());
    dto.setFacultyId(entity.getFaculty().getId());
    dto.setTimeSlotId(entity.getTimeSlot().getId());
    dto.setAvailable(entity.isAvailable());
    return dto;
  }

  private FacultyAvailability toEntity(FacultyAvailabilityDto dto) {
    return FacultyAvailability.builder()
        .id(dto.getId())
        .faculty(fetchFaculty(dto.getFacultyId()))
        .timeSlot(fetchTimeSlot(dto.getTimeSlotId()))
        .available(dto.isAvailable())
        .build();
  }

  private Faculty fetchFaculty(Long id) {
    return facultyRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
  }

  private TimeSlot fetchTimeSlot(Long id) {
    return timeSlotRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Time slot not found"));
  }
}
