package com.example.timetable.scheduler;

import com.example.timetable.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScheduledSlot {
  private Section section;
  private TimeSlot timeSlot;
  private Subject subject;
  private Faculty faculty;
  private Room room;
  private boolean labBlockStart;
  private int blockSize;
}
