import { useEffect, useState } from 'react';
import { getClassView, type TimetableEntryView } from '../api/timetableApi';
import { TimetableGrid, type EntryView } from '../components/TimetableGrid';
import { Button, Stack, TextField } from '@mui/material';

export function PrintableTimetablePage() {
  const [timetableId, setTimetableId] = useState(1);
  const [sectionId, setSectionId] = useState(1);
  const [entries, setEntries] = useState<EntryView[]>([]);
  const [days, setDays] = useState<string[]>([]);
  const [slots, setSlots] = useState<{ slotIndex: number }[]>([]);

  const load = async () => {
    const res = await getClassView(timetableId, sectionId);
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

  const handlePrint = () => {
    window.print();
  };

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
          label="Section ID"
          value={sectionId}
          onChange={e => setSectionId(Number(e.target.value))}
        />
        <Button variant="contained" onClick={load}>
          Load
        </Button>
        <Button variant="outlined" onClick={handlePrint}>
          Print
        </Button>
      </Stack>

      <TimetableGrid days={days} slots={slots} entries={entries} />
    </Stack>
  );
}
