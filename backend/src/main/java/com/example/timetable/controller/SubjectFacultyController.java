package com.example.timetable.controller;

import com.example.timetable.dto.SubjectFacultyDto;
import com.example.timetable.service.SubjectFacultyService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject-faculty-mappings")
@RequiredArgsConstructor
public class SubjectFacultyController {

  private final SubjectFacultyService subjectFacultyService;

  @GetMapping
  public List<SubjectFacultyDto> list() {
    return subjectFacultyService.findAll();
  }

  @PostMapping
  public ResponseEntity<SubjectFacultyDto> create(@Valid @RequestBody SubjectFacultyDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(subjectFacultyService.create(dto));
  }

  @PutMapping("/{id}")
  public SubjectFacultyDto update(@PathVariable Long id, @Valid @RequestBody SubjectFacultyDto dto) {
    return subjectFacultyService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    subjectFacultyService.delete(id);
  }
}
