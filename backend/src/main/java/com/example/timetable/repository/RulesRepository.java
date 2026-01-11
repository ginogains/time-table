package com.example.timetable.repository;

import com.example.timetable.domain.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulesRepository extends JpaRepository<Rules, Long> {
}
