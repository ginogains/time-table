package com.example.timetable.repository;

import com.example.timetable.domain.Section;
import com.example.timetable.domain.SubjectFaculty;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectFacultyRepository extends JpaRepository<SubjectFaculty, Long> {
  List<SubjectFaculty> findBySectionIn(List<Section> sections);
}
