package com.example.timetable.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "faculty_availability")
public class FacultyAvailability {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "faculty_id")
  private Faculty faculty;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "time_slot_id")
  private TimeSlot timeSlot;

  @Column(name = "is_available", nullable = false)
  private boolean available;
}
