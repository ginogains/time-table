package com.example.timetable.controller;

import com.example.timetable.dto.GenerateTimetableRequest;
import com.example.timetable.dto.TimetableDto;
import com.example.timetable.mapper.TimetableMapper;
import com.example.timetable.service.TimetableGeneratorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timetables")
@RequiredArgsConstructor
public class TimetableGenerationController {

  private final TimetableGeneratorService generatorService;

  @PostMapping("/generate")
  public TimetableDto generate(@Valid @RequestBody GenerateTimetableRequest request) {
    return TimetableMapper.toDto(generatorService.generate(request));
  }
}
