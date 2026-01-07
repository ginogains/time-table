package com.example.timetable.controller;

import com.example.timetable.dto.TimeSlotDto;
import com.example.timetable.service.TimeSlotService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/time-slots")
@RequiredArgsConstructor
public class TimeSlotController {

  private final TimeSlotService timeSlotService;

  @GetMapping
  public List<TimeSlotDto> list() {
    return timeSlotService.findAll();
  }

  @PostMapping
  public ResponseEntity<TimeSlotDto> create(@Valid @RequestBody TimeSlotDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(timeSlotService.create(dto));
  }

  @PutMapping("/{id}")
  public TimeSlotDto update(@PathVariable Long id, @Valid @RequestBody TimeSlotDto dto) {
    return timeSlotService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    timeSlotService.delete(id);
  }
}
