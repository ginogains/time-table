package com.example.timetable.service;

import com.example.timetable.domain.Rules;
import com.example.timetable.dto.RulesDto;
import com.example.timetable.repository.RulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RulesService {

  private final RulesRepository rulesRepository;

  public List<RulesDto> findAll() {
    return rulesRepository.findAll().stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  public RulesDto create(RulesDto dto) {
    Rules rules = toEntity(dto);
    Rules saved = rulesRepository.save(rules);
    return toDto(saved);
  }

  public RulesDto update(Long id, RulesDto dto) {
    Rules rules = rulesRepository.findById(id).orElseThrow();
    rules.setMaxHoursPerDay(dto.getMaxHoursPerDay());
    rules.setMaxHoursPerWeek(dto.getMaxHoursPerWeek());
    rules.setMaxConsecutiveHours(dto.getMaxConsecutiveHours());
    rules.setBreakDuration(dto.getBreakDuration());
    Rules saved = rulesRepository.save(rules);
    return toDto(saved);
  }

  public void delete(Long id) {
    rulesRepository.deleteById(id);
  }

  private RulesDto toDto(Rules rules) {
    RulesDto dto = new RulesDto();
    dto.setId(rules.getId());
    dto.setMaxHoursPerDay(rules.getMaxHoursPerDay());
    dto.setMaxHoursPerWeek(rules.getMaxHoursPerWeek());
    dto.setMaxConsecutiveHours(rules.getMaxConsecutiveHours());
    dto.setBreakDuration(rules.getBreakDuration());
    return dto;
  }

  private Rules toEntity(RulesDto dto) {
    return Rules.builder()
        .id(dto.getId())
        .maxHoursPerDay(dto.getMaxHoursPerDay())
        .maxHoursPerWeek(dto.getMaxHoursPerWeek())
        .maxConsecutiveHours(dto.getMaxConsecutiveHours())
        .breakDuration(dto.getBreakDuration())
        .build();
  }
}
