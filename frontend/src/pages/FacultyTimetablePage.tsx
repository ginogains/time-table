import { useEffect, useState } from 'react';
import { getFacultyView, type TimetableEntryView } from '../api/timetableApi';
import { TimetableGrid, type EntryView } from '../components/TimetableGrid';
import { Button, Stack, TextField } from '@mui/material';

export function FacultyTimetablePage() {
  const [timetableId, setTimetableId] = useState(1);
  const [facultyId, setFacultyId] = useState(1);
  const [entries, setEntries] = useState<EntryView[]>([]);
  const [days, setDays] = useState<string[]>([]);
  const [slots, setSlots] = useState<{ slotIndex: number }[]>([]);

  const load = async () => {
    const res = await getFacultyView(timetableId, facultyId);
    setEntries(res.data as EntryView[]);
    const uniqueDays = Array.from(new Set(res.data.map(e => e.dayName)));
    setDays(uniqueDays);
    const uniqueSlots = Array.from(new Set(res.data.map(e => e.slotIndex))).map(slotIndex => ({
      slotIndex,
    }));
    setSlots(uniqueSlots);
  };

  useEffect(() => {
    load();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <Stack gap={2}>
      <Stack direction="row" gap={2} alignItems="center">
        <TextField
          type="number"
          label="Timetable ID"
          value={timetableId}
          onChange={e => setTimetableId(Number(e.target.value))}
        />
        <TextField
          type="number"
          label="Faculty ID"
          value={facultyId}
          onChange={e => setFacultyId(Number(e.target.value))}
        />
        <Button variant="contained" onClick={load}>
          Load
        </Button>
      </Stack>

      <TimetableGrid days={days} slots={slots} entries={entries} />
    </Stack>
  );
}
