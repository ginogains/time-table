package com.example.timetable.controller;

import com.example.timetable.dto.RoomDto;
import com.example.timetable.service.RoomService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  @GetMapping
  public List<RoomDto> list() {
    return roomService.findAll();
  }

  @PostMapping
  public ResponseEntity<RoomDto> create(@Valid @RequestBody RoomDto dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(dto));
  }

  @PutMapping("/{id}")
  public RoomDto update(@PathVariable Long id, @Valid @RequestBody RoomDto dto) {
    return roomService.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    roomService.delete(id);
  }
}
