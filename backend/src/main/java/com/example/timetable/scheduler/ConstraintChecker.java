package com.example.timetable.scheduler;

import com.example.timetable.domain.Faculty;
import com.example.timetable.domain.Room;
import com.example.timetable.domain.Section;
import com.example.timetable.domain.TimeSlot;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ConstraintChecker {

  public boolean isFacultyFree(List<ScheduledSlot> current, Faculty faculty, TimeSlot timeSlot) {
    return current.stream().noneMatch(s ->
        s.getFaculty().getId().equals(faculty.getId()) &&
            s.getTimeSlot().getId().equals(timeSlot.getId()));
  }

  public boolean isRoomFree(List<ScheduledSlot> current, Room room, TimeSlot timeSlot) {
    return current.stream().noneMatch(s ->
        s.getRoom().getId().equals(room.getId()) &&
            s.getTimeSlot().getId().equals(timeSlot.getId()));
  }

  public boolean isSectionFree(List<ScheduledSlot> current, Section section, TimeSlot timeSlot) {
    return current.stream().noneMatch(s ->
        s.getSection().getId().equals(section.getId()) &&
            s.getTimeSlot().getId().equals(timeSlot.getId()));
  }

  public boolean isFacultyAvailable(Map<Long, Set<Long>> facultyAvailableSlotIds,
                                    Faculty faculty, TimeSlot timeSlot) {
    Set<Long> slots = facultyAvailableSlotIds.get(faculty.getId());
    return slots != null && slots.contains(timeSlot.getId());
  }
}
