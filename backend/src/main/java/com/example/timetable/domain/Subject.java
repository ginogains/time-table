package com.example.timetable.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "subject")
public class Subject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 20)
  private String code;

  @Column(nullable = false, length = 200)
  private String name;

  @Column(length = 30)
  private String type;

  private Integer credits;

  @Column(name = "weekly_lecture_hours")
  private Integer weeklyLectureHours;

  @Column(name = "weekly_lab_hours")
  private Integer weeklyLabHours;

  @Column(name = "is_lab", nullable = false)
  private boolean lab;
}
