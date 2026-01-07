package com.example.timetable.controller;

import com.example.timetable.dto.FacultyAvailabilityDto;
import com.example.timetable.service.FacultyAvailabilityService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty-availability")
@RequiredArgsConstructor
public class FacultyAvailabilityController {

  private final FacultyAvailabilityService availabilityService;

  @GetMapping
  public List<FacultyAvailabilityDto> list() {
    return availabilityService.findAll();
  }

  @PostMapping
  public ResponseEntity<FacultyAvailabilityDto> create(@Valid @RequestBody FacultyAvailabilityDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(availabilityService.create(dto));
  }

  @PutMapping("/{id}")
  public FacultyAvailabilityDto update(@PathVariable Long id,
                                       @Valid @RequestBody FacultyAvailabilityDto dto) {
    return availabilityService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    availabilityService.delete(id);
  }
}
