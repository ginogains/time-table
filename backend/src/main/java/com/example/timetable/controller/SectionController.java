package com.example.timetable.controller;

import com.example.timetable.dto.SectionDto;
import com.example.timetable.service.SectionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
public class SectionController {

  private final SectionService sectionService;

  @GetMapping
  public List<SectionDto> list() {
    return sectionService.findAll();
  }

  @PostMapping
  public ResponseEntity<SectionDto> create(@Valid @RequestBody SectionDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(sectionService.create(dto));
  }

  @PutMapping("/{id}")
  public SectionDto update(@PathVariable Long id, @Valid @RequestBody SectionDto dto) {
    return sectionService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    sectionService.delete(id);
  }
}
