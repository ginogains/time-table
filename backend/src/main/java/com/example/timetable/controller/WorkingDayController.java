package com.example.timetable.controller;

import com.example.timetable.dto.WorkingDayDto;
import com.example.timetable.service.WorkingDayService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/working-days")
@RequiredArgsConstructor
public class WorkingDayController {

  private final WorkingDayService workingDayService;

  @GetMapping
  public List<WorkingDayDto> list() {
    return workingDayService.findAll();
  }

  @PostMapping
  public ResponseEntity<WorkingDayDto> create(@Valid @RequestBody WorkingDayDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(workingDayService.create(dto));
  }

  @PutMapping("/{id}")
  public WorkingDayDto update(@PathVariable Long id, @Valid @RequestBody WorkingDayDto dto) {
    return workingDayService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    workingDayService.delete(id);
  }
}
