package com.example.timetable.service;

import com.example.timetable.domain.Department;
import com.example.timetable.domain.Room;
import com.example.timetable.dto.RoomDto;
import com.example.timetable.exception.ResourceNotFoundException;
import com.example.timetable.repository.DepartmentRepository;
import com.example.timetable.repository.RoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

  private final RoomRepository roomRepository;
  private final DepartmentRepository departmentRepository;

  @Transactional(readOnly = true)
  public List<RoomDto> findAll() {
    return roomRepository.findAll().stream().map(this::toDto).toList();
  }

  @Transactional
  public RoomDto create(RoomDto dto) {
    Room entity = toEntity(dto);
    entity.setId(null);
    return toDto(roomRepository.save(entity));
  }

  @Transactional
  public RoomDto update(Long id, RoomDto dto) {
    Room existing = roomRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
    existing.setName(dto.getName());
    existing.setType(dto.getType());
    existing.setCapacity(dto.getCapacity());
    existing.setLab(dto.isLab());
    existing.setDepartment(fetchDepartment(dto.getDepartmentId()));
    return toDto(roomRepository.save(existing));
  }

  @Transactional
  public void delete(Long id) {
    if (!roomRepository.existsById(id)) {
      throw new ResourceNotFoundException("Room not found");
    }
    roomRepository.deleteById(id);
  }

  private RoomDto toDto(Room entity) {
    RoomDto dto = new RoomDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setType(entity.getType());
    dto.setCapacity(entity.getCapacity());
    dto.setLab(entity.isLab());
    dto.setDepartmentId(entity.getDepartment() != null ? entity.getDepartment().getId() : null);
    return dto;
  }

  private Room toEntity(RoomDto dto) {
    return Room.builder()
        .id(dto.getId())
        .name(dto.getName())
        .type(dto.getType())
        .capacity(dto.getCapacity())
        .lab(dto.isLab())
        .department(fetchDepartment(dto.getDepartmentId()))
        .build();
  }

  private Department fetchDepartment(Long id) {
    if (id == null) {
      return null;
    }
    return departmentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
  }
}
