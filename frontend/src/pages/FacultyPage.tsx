import { useEffect, useState } from 'react';
import {
  listFaculty,
  createFaculty,
  updateFaculty,
  deleteFaculty,
  type Faculty,
} from '../api/facultyApi';
import { listDepartments, type Department } from '../api/departmentApi';
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TextField,
} from '@mui/material';

export function FacultyPage() {
  const [faculty, setFaculty] = useState<Faculty[]>([]);
  const [departments, setDepartments] = useState<Department[]>([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState<Faculty | null>(null);

  const load = async () => {
    const [facultyRes, deptsRes] = await Promise.all([
      listFaculty(),
      listDepartments(),
    ]);
    setFaculty(facultyRes.data);
    setDepartments(deptsRes.data);
  };

  useEffect(() => {
    load();
  }, []);

  const startCreate = () => {
    setEditing({ name: '', email: '', departmentId: 1 });
    setOpen(true);
  };

  const startEdit = (fac: Faculty) => {
    setEditing(fac);
    setOpen(true);
  };

  const handleSave = async () => {
    if (!editing) return;
    if (!editing.name.trim() || !editing.email.trim()) {
      alert('Name and email are required');
      return;
    }
    if (editing.departmentId === '') {
      alert('Please select a department');
      return;
    }
    const dataToSave = {
      ...editing,
      departmentId: Number(editing.departmentId),
    };
    if (editing.id) {
      await updateFaculty(editing.id, dataToSave);
    } else {
      await createFaculty(dataToSave);
    }
    setOpen(false);
    setEditing(null);
    load();
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;
    await deleteFaculty(id);
    load();
  };

  return (
    <Stack gap={2}>
      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <h2>Faculty</h2>
        <Button variant="contained" onClick={startCreate}>
          Add Faculty
        </Button>
      </Stack>

      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell>Email</TableCell>
            <TableCell>Department</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {faculty.map(f => (
            <TableRow key={f.id}>
              <TableCell>{f.name}</TableCell>
              <TableCell>{f.email}</TableCell>
              <TableCell>{departments.find(d => d.id === f.departmentId)?.name}</TableCell>
              <TableCell align="right">
                <Button size="small" onClick={() => startEdit(f)}>
                  Edit
                </Button>
                <Button size="small" color="error" onClick={() => handleDelete(f.id)}>
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>{editing?.id ? 'Edit Faculty' : 'Add Faculty'}</DialogTitle>
        <DialogContent>
          <Stack gap={2} mt={1}>
            <TextField
              label="Name"
              value={editing?.name ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as Faculty), name: e.target.value }))}
            />
            <TextField
              label="Email"
              value={editing?.email ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as Faculty), email: e.target.value }))}
            />
            <FormControl fullWidth>
              <InputLabel>Department</InputLabel>
              <Select
                value={editing?.departmentId ?? ''}
                onChange={e => setEditing(prev => ({ ...(prev as Faculty), departmentId: Number(e.target.value) }))}
              >
                {departments.map(d => (
                  <MenuItem key={d.id} value={d.id}>{d.name}</MenuItem>
                ))}
              </Select>
            </FormControl>
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
