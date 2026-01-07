import React from 'react';
import './TimetableGrid.css';

export type EntryView = {
  timeSlotId: number;
  dayName: string;
  slotIndex: number;
  subjectCode: string;
  subjectName: string;
  facultyName: string;
  roomName: string;
};

export interface TimetableGridProps {
  days: string[];
  slots: { slotIndex: number }[];
  entries: EntryView[];
}

export const TimetableGrid: React.FC<TimetableGridProps> = ({ days, slots, entries }) => {
  const uniqueSlots = Array.from(new Set(slots.map(s => s.slotIndex))).sort((a, b) => a - b);

  const getEntry = (day: string, slotIndex: number) =>
    entries.find(e => e.dayName === day && e.slotIndex === slotIndex);

  return (
    <table className="timetable-grid">
      <thead>
        <tr>
          <th>Period</th>
          {days.map(day => (
            <th key={day}>{day}</th>
          ))}
        </tr>
      </thead>
      <tbody>
        {uniqueSlots.map(slot => (
          <tr key={slot}>
            <td>{slot}</td>
            {days.map(day => {
              const entry = getEntry(day, slot);
              return (
                <td key={`${day}-${slot}`}>
                  {entry ? (
                    <div className="timetable-cell">
                      <div className="subject">{entry.subjectCode}</div>
                      <div className="faculty">{entry.facultyName}</div>
                      <div className="room">{entry.roomName}</div>
                    </div>
                  ) : null}
                </td>
              );
            })}
          </tr>
        ))}
      </tbody>
    </table>
  );
};
