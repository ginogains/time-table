package com.example.timetable.repository;

import com.example.timetable.domain.TimetableEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimetableEntryRepository extends JpaRepository<TimetableEntry, Long> {
  List<TimetableEntry> findByTimetableId(Long timetableId);
  List<TimetableEntry> findByTimetableIdAndSectionId(Long timetableId, Long sectionId);
  List<TimetableEntry> findByTimetableIdAndFacultyId(Long timetableId, Long facultyId);
}
