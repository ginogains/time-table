package com.example.timetable.repository;

import com.example.timetable.domain.FacultyAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyAvailabilityRepository extends JpaRepository<FacultyAvailability, Long> {
}
