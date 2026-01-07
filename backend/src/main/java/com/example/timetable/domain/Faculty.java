package com.example.timetable.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "faculty")
public class Faculty {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 20)
  private String code;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(length = 100)
  private String email;

  @Column(length = 50)
  private String designation;

  @Column(name = "max_hours_per_day")
  private Integer maxHoursPerDay;

  @Column(name = "max_hours_per_week")
  private Integer maxHoursPerWeek;
}
