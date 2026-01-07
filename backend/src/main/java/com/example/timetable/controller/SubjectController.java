package com.example.timetable.controller;

import com.example.timetable.dto.SubjectDto;
import com.example.timetable.service.SubjectService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

  private final SubjectService subjectService;

  @GetMapping
  public List<SubjectDto> list() {
    return subjectService.findAll();
  }

  @PostMapping
  public ResponseEntity<SubjectDto> create(@Valid @RequestBody SubjectDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.create(dto));
  }

  @PutMapping("/{id}")
  public SubjectDto update(@PathVariable Long id, @Valid @RequestBody SubjectDto dto) {
    return subjectService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    subjectService.delete(id);
  }
}
