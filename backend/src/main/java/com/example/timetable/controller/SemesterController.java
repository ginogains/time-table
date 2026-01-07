package com.example.timetable.controller;

import com.example.timetable.dto.SemesterDto;
import com.example.timetable.service.SemesterService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/semesters")
@RequiredArgsConstructor
public class SemesterController {

  private final SemesterService semesterService;

  @GetMapping
  public List<SemesterDto> list() {
    return semesterService.findAll();
  }

  @PostMapping
  public ResponseEntity<SemesterDto> create(@Valid @RequestBody SemesterDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(semesterService.create(dto));
  }

  @PutMapping("/{id}")
  public SemesterDto update(@PathVariable Long id, @Valid @RequestBody SemesterDto dto) {
    return semesterService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    semesterService.delete(id);
  }
}
