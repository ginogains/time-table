package com.example.timetable.controller;

import com.example.timetable.dto.DepartmentDto;
import com.example.timetable.service.DepartmentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

  private final DepartmentService departmentService;

  @GetMapping
  public List<DepartmentDto> list() {
    return departmentService.findAll();
  }

  @PostMapping
  public ResponseEntity<DepartmentDto> create(@Valid @RequestBody DepartmentDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.create(dto));
  }

  @PutMapping("/{id}")
  public DepartmentDto update(@PathVariable Long id, @Valid @RequestBody DepartmentDto dto) {
    return departmentService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    departmentService.delete(id);
  }
}
