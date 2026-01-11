package com.example.timetable.controller;

import com.example.timetable.dto.RulesDto;
import com.example.timetable.service.RulesService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
public class RulesController {

  private final RulesService rulesService;

  @GetMapping
  public List<RulesDto> list() {
    return rulesService.findAll();
  }

  @PostMapping
  public ResponseEntity<RulesDto> create(@Valid @RequestBody RulesDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(rulesService.create(dto));
  }

  @PutMapping("/{id}")
  public RulesDto update(@PathVariable Long id, @Valid @RequestBody RulesDto dto) {
    return rulesService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    rulesService.delete(id);
  }
}
