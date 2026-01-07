package com.example.timetable.controller;

import com.example.timetable.dto.AcademicYearDto;
import com.example.timetable.service.AcademicYearService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/academic-years")
@RequiredArgsConstructor
public class AcademicYearController {

  private final AcademicYearService academicYearService;

  @GetMapping
  public List<AcademicYearDto> list() {
    return academicYearService.findAll();
  }

  @PostMapping
  public ResponseEntity<AcademicYearDto> create(@Valid @RequestBody AcademicYearDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(academicYearService.create(dto));
  }

  @PutMapping("/{id}")
  public AcademicYearDto update(@PathVariable Long id, @Valid @RequestBody AcademicYearDto dto) {
    return academicYearService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    academicYearService.delete(id);
  }
}
