package com.example.timetable.controller;

import com.example.timetable.dto.TimetableEntryView;
import com.example.timetable.service.TimetableQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timetables")
@RequiredArgsConstructor
public class TimetableQueryController {

  private final TimetableQueryService queryService;

  @GetMapping("/{id}/class-view")
  public List<TimetableEntryView> classView(@PathVariable Long id, @RequestParam Long sectionId) {
    return queryService.getClassView(id, sectionId);
  }

  @GetMapping("/{id}/faculty-view")
  public List<TimetableEntryView> facultyView(@PathVariable Long id, @RequestParam Long facultyId) {
    return queryService.getFacultyView(id, facultyId);
  }

  @GetMapping("/{id}/department-view")
  public List<TimetableEntryView> departmentView(@PathVariable Long id) {
    return queryService.getDepartmentView(id);
  }
}
