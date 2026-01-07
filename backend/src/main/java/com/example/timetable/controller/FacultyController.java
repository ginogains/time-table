package com.example.timetable.controller;

import com.example.timetable.dto.FacultyDto;
import com.example.timetable.service.FacultyService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty")
@RequiredArgsConstructor
public class FacultyController {

  private final FacultyService facultyService;

  @GetMapping
  public List<FacultyDto> list() {
    return facultyService.findAll();
  }

  @PostMapping
  public ResponseEntity<FacultyDto> create(@Valid @RequestBody FacultyDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(facultyService.create(dto));
  }

  @PutMapping("/{id}")
  public FacultyDto update(@PathVariable Long id, @Valid @RequestBody FacultyDto dto) {
    return facultyService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    facultyService.delete(id);
  }
}
