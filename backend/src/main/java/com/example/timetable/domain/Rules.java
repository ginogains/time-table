package com.example.timetable.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rules")
public class Rules {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "max_hours_per_day")
  private Integer maxHoursPerDay;

  @Column(name = "max_hours_per_week")
  private Integer maxHoursPerWeek;

  @Column(name = "max_consecutive_hours")
  private Integer maxConsecutiveHours;

  @Column(name = "break_duration")
  private Integer breakDuration;
}
