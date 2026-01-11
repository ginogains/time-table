import { useEffect, useState } from 'react';
import {
  listRooms,
  createRoom,
  updateRoom,
  deleteRoom,
  type Room,
} from '../api/roomApi';
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

export function RoomPage() {
  const [rooms, setRooms] = useState<Room[]>([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState<Room | null>(null);

  const load = async () => {
    const res = await listRooms();
    setRooms(res.data);
  };

  useEffect(() => {
    load();
  }, []);

  const startCreate = () => {
    setEditing({ name: '', type: 'classroom', capacity: 0 });
    setOpen(true);
  };

  const startEdit = (room: Room) => {
    setEditing(room);
    setOpen(true);
  };

  const handleSave = async () => {
    if (!editing) return;
    if (editing.id) {
      await updateRoom(editing.id, editing);
    } else {
      await createRoom(editing);
    }
    setOpen(false);
    setEditing(null);
    load();
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;
    await deleteRoom(id);
    load();
  };

  return (
    <Stack gap={2}>
      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <h2>Rooms</h2>
        <Button variant="contained" onClick={startCreate}>
          Add Room
        </Button>
      </Stack>

      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell>Type</TableCell>
            <TableCell>Capacity</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rooms.map(r => (
            <TableRow key={r.id}>
              <TableCell>{r.name}</TableCell>
              <TableCell>{r.type}</TableCell>
              <TableCell>{r.capacity}</TableCell>
              <TableCell align="right">
                <Button size="small" onClick={() => startEdit(r)}>
                  Edit
                </Button>
                <Button size="small" color="error" onClick={() => handleDelete(r.id)}>
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>{editing?.id ? 'Edit Room' : 'Add Room'}</DialogTitle>
        <DialogContent>
          <Stack gap={2} mt={1}>
            <TextField
              label="Name"
              value={editing?.name ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as Room), name: e.target.value }))}
            />
            <TextField
              label="Type"
              value={editing?.type ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as Room), type: e.target.value }))}
            />
            <TextField
              type="number"
              label="Capacity"
              value={editing?.capacity ?? 0}
              onChange={e => setEditing(prev => ({ ...(prev as Room), capacity: Number(e.target.value) }))}
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
