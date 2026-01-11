import { useEffect, useState } from 'react';
import {
  listSections,
  createSection,
  updateSection,
  deleteSection,
  type Section,
} from '../api/sectionApi';
import { listDepartments, type Department } from '../api/departmentApi';
import { listSemesters, type Semester } from '../api/semesterApi';
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

export function SectionPage() {
  const [sections, setSections] = useState<Section[]>([]);
  const [departments, setDepartments] = useState<Department[]>([]);
  const [semesters, setSemesters] = useState<Semester[]>([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState<Section | null>(null);

  const load = async () => {
    const [sectionsRes, deptsRes, semsRes] = await Promise.all([
      listSections(),
      listDepartments(),
      listSemesters(),
    ]);
    setSections(sectionsRes.data);
    setDepartments(deptsRes.data);
    setSemesters(semsRes.data);
  };

  useEffect(() => {
    load();
  }, []);

  const startCreate = () => {
    setEditing({ name: '', year: 1, semesterId: '', departmentId: '', strength: 0 });
    setOpen(true);
  };

  const startEdit = (section: Section) => {
    setEditing(section);
    setOpen(true);
  };

  const handleSave = async () => {
    if (!editing) return;
    if (!editing.name.trim()) {
      alert('Name is required');
      return;
    }
    if (editing.semesterId === '') {
      alert('Please select a semester');
      return;
    }
    if (editing.departmentId === '') {
      alert('Please select a department');
      return;
    }
    const dataToSave = {
      ...editing,
      semesterId: Number(editing.semesterId),
      departmentId: Number(editing.departmentId),
    };
    if (editing.id) {
      await updateSection(editing.id, dataToSave);
    } else {
      await createSection(dataToSave);
    }
    setOpen(false);
    setEditing(null);
    load();
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;
    await deleteSection(id);
    load();
  };

  return (
    <Stack gap={2}>
      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <h2>Sections</h2>
        <Button variant="contained" onClick={startCreate}>
          Add Section
        </Button>
      </Stack>

      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell>Year</TableCell>
            <TableCell>Semester</TableCell>
            <TableCell>Department</TableCell>
            <TableCell>Strength</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {sections.map(s => (
            <TableRow key={s.id}>
              <TableCell>{s.name}</TableCell>
              <TableCell>{s.year}</TableCell>
              <TableCell>{semesters.find(sem => sem.id === s.semesterId)?.name}</TableCell>
              <TableCell>{departments.find(d => d.id === s.departmentId)?.name}</TableCell>
              <TableCell>{s.strength}</TableCell>
              <TableCell align="right">
                <Button size="small" onClick={() => startEdit(s)}>
                  Edit
                </Button>
                <Button size="small" color="error" onClick={() => handleDelete(s.id)}>
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>{editing?.id ? 'Edit Section' : 'Add Section'}</DialogTitle>
        <DialogContent>
          <Stack gap={2} mt={1}>
            <TextField
              label="Name"
              value={editing?.name ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as Section), name: e.target.value }))}
            />
            <TextField
              type="number"
              label="Year"
              value={editing?.year ?? 1}
              onChange={e => setEditing(prev => ({ ...(prev as Section), year: Number(e.target.value) }))}
            />
            <FormControl fullWidth>
              <InputLabel>Semester</InputLabel>
              <Select
                value={editing?.semesterId ?? ''}
                onChange={e => setEditing(prev => ({ ...(prev as Section), semesterId: Number(e.target.value) }))}
              >
                {semesters.map(s => (
                  <MenuItem key={s.id} value={s.id}>{s.name}</MenuItem>
                ))}
              </Select>
            </FormControl>
            <FormControl fullWidth>
              <InputLabel>Department</InputLabel>
              <Select
                value={editing?.departmentId ?? ''}
                onChange={e => setEditing(prev => ({ ...(prev as Section), departmentId: Number(e.target.value) }))}
              >
                {departments.map(d => (
                  <MenuItem key={d.id} value={d.id}>{d.name}</MenuItem>
                ))}
              </Select>
            </FormControl>
            <TextField
              type="number"
              label="Strength"
              value={editing?.strength ?? 0}
              onChange={e => setEditing(prev => ({ ...(prev as Section), strength: Number(e.target.value) }))}
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
