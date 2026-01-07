package com.example.timetable.repository;

import com.example.timetable.domain.Section;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
  List<Section> findByDepartmentId(Long departmentId);
}
