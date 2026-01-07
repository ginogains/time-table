package com.example.timetable.scheduler;

import com.example.timetable.domain.Section;
import com.example.timetable.domain.SubjectFaculty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AssignmentRequest {
  private Section section;
  private List<SubjectFaculty> candidates;
  private int unitsNeeded;
  private boolean labBlock;
}
