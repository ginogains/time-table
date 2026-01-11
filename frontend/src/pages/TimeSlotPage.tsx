import { useEffect, useState } from 'react';
import {
  listTimeSlots,
  createTimeSlot,
  updateTimeSlot,
  deleteTimeSlot,
  type TimeSlot,
} from '../api/timeSlotApi';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TextField,
} from '@mui/material';

export function TimeSlotPage() {
  const [timeSlots, setTimeSlots] = useState<TimeSlot[]>([]);
  const [workingDays, setWorkingDays] = useState<WorkingDay[]>([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState<TimeSlot | null>(null);

  const load = async () => {
    const [timeSlotsRes, workingDaysRes] = await Promise.all([
      listTimeSlots(),
      listWorkingDays(),
    ]);
    setTimeSlots(timeSlotsRes.data);
    setWorkingDays(workingDaysRes.data);
  };

  useEffect(() => {
    load();
  }, []);

  const startCreate = () => {
    setEditing({ dayId: '', startTime: '', endTime: '' });
    setOpen(true);
  };

  const startEdit = (timeSlot: TimeSlot) => {
    setEditing(timeSlot);
    setOpen(true);
  };

  const handleSave = async () => {
    if (!editing) return;
    if (editing.dayId === '') {
      alert('Please select a working day');
      return;
    }
    if (!editing.startTime.trim() || !editing.endTime.trim()) {
      alert('Start time and end time are required');
      return;
    }
    const dataToSave = {
      ...editing,
      dayId: Number(editing.dayId),
    };
    if (editing.id) {
      await updateTimeSlot(editing.id, dataToSave);
    } else {
      await createTimeSlot(dataToSave);
    }
    setOpen(false);
    setEditing(null);
    load();
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;
    await deleteTimeSlot(id);
    load();
  };

  return (
    <Stack gap={2}>
      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <h2>Time Slots</h2>
        <Button variant="contained" onClick={startCreate}>
          Add Time Slot
        </Button>
      </Stack>

      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Day</TableCell>
            <TableCell>Start Time</TableCell>
            <TableCell>End Time</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {timeSlots.map(t => (
            <TableRow key={t.id}>
              <TableCell>{workingDays.find(d => d.id === t.dayId)?.name}</TableCell>
              <TableCell>{t.startTime}</TableCell>
              <TableCell>{t.endTime}</TableCell>
              <TableCell align="right">
                <Button size="small" onClick={() => startEdit(t)}>
                  Edit
                </Button>
                <Button size="small" color="error" onClick={() => handleDelete(t.id)}>
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>{editing?.id ? 'Edit Time Slot' : 'Add Time Slot'}</DialogTitle>
        <DialogContent>
          <Stack gap={2} mt={1}>
            <FormControl fullWidth>
              <InputLabel>Working Day</InputLabel>
              <Select
                value={editing?.dayId ?? ''}
                onChange={e => setEditing(prev => ({ ...(prev as TimeSlot), dayId: e.target.value }))}
              >
                {workingDays.map(d => (
                  <MenuItem key={d.id} value={d.id}>{d.name}</MenuItem>
                ))}
              </Select>
            </FormControl>
            <TextField
              label="Start Time"
              value={editing?.startTime ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as TimeSlot), startTime: e.target.value }))}
            />
            <TextField
              label="End Time"
              value={editing?.endTime ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as TimeSlot), endTime: e.target.value }))}
            />
          </Stack>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpen(false)}>Cancel</Button>
          <Button onClick={handleSave} variant="contained">
            Save
          </Button>
        </DialogActions>
      </Dialog>
    </Stack>
  );
}
