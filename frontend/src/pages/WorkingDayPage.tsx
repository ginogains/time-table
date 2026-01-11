import { useEffect, useState } from 'react';
import {
  listWorkingDays,
  createWorkingDay,
  updateWorkingDay,
  deleteWorkingDay,
  type WorkingDay,
} from '../api/workingDayApi';
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

export function WorkingDayPage() {
  const [workingDays, setWorkingDays] = useState<WorkingDay[]>([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState<WorkingDay | null>(null);

  const load = async () => {
    const res = await listWorkingDays();
    setWorkingDays(res.data);
  };

  useEffect(() => {
    load();
  }, []);

  const startCreate = () => {
    setEditing({ name: '', orderIndex: 0 });
    setOpen(true);
  };

  const startEdit = (workingDay: WorkingDay) => {
    setEditing(workingDay);
    setOpen(true);
  };

  const handleSave = async () => {
    if (!editing) return;
    if (editing.id) {
      await updateWorkingDay(editing.id, editing);
    } else {
      await createWorkingDay(editing);
    }
    setOpen(false);
    setEditing(null);
    load();
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;
    await deleteWorkingDay(id);
    load();
  };

  return (
    <Stack gap={2}>
      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <h2>Working Days</h2>
        <Button variant="contained" onClick={startCreate}>
          Add Working Day
        </Button>
      </Stack>

      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {workingDays.map(w => (
            <TableRow key={w.id}>
              <TableCell>{w.name}</TableCell>
              <TableCell align="right">
                <Button size="small" onClick={() => startEdit(w)}>
                  Edit
                </Button>
                <Button size="small" color="error" onClick={() => handleDelete(w.id)}>
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>{editing?.id ? 'Edit Working Day' : 'Add Working Day'}</DialogTitle>
        <DialogContent>
          <Stack gap={2} mt={1}>
            <TextField
              label="Name"
              value={editing?.name ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as WorkingDay), name: e.target.value }))}
            />
            <TextField
              type="number"
              label="Order Index"
              value={editing?.orderIndex ?? 0}
              onChange={e => setEditing(prev => ({ ...(prev as WorkingDay), orderIndex: Number(e.target.value) }))}
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
