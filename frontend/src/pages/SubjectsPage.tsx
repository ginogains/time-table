import { useEffect, useState } from 'react';
import {
  listSubjects,
  createSubject,
  updateSubject,
  deleteSubject,
  type Subject,
} from '../api/subjectApi';
import {
  Button,
  Checkbox,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControlLabel,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TextField,
} from '@mui/material';

export function SubjectsPage() {
  const [subjects, setSubjects] = useState<Subject[]>([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState<Subject | null>(null);

  const load = async () => {
    const res = await listSubjects();
    setSubjects(res.data);
  };

  useEffect(() => {
    load();
  }, []);

  const startCreate = () => {
    setEditing({ code: '', name: '', lab: false });
    setOpen(true);
  };

  const startEdit = (subject: Subject) => {
    setEditing(subject);
    setOpen(true);
  };

  const handleSave = async () => {
    if (!editing) return;
    if (editing.id) {
      await updateSubject(editing.id, editing);
    } else {
      await createSubject(editing);
    }
    setOpen(false);
    setEditing(null);
    load();
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;
    await deleteSubject(id);
    load();
  };

  return (
    <Stack gap={2}>
      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <h2>Subjects</h2>
        <Button variant="contained" onClick={startCreate}>
          Add Subject
        </Button>
      </Stack>

      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Code</TableCell>
            <TableCell>Name</TableCell>
            <TableCell>Type</TableCell>
            <TableCell>Lab?</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {subjects.map(s => (
            <TableRow key={s.id}>
              <TableCell>{s.code}</TableCell>
              <TableCell>{s.name}</TableCell>
              <TableCell>{s.type}</TableCell>
              <TableCell>{s.lab ? 'Yes' : 'No'}</TableCell>
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
        <DialogTitle>{editing?.id ? 'Edit Subject' : 'Add Subject'}</DialogTitle>
        <DialogContent>
          <Stack gap={2} mt={1}>
            <TextField
              label="Code"
              value={editing?.code ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as Subject), code: e.target.value }))}
            />
            <TextField
              label="Name"
              value={editing?.name ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as Subject), name: e.target.value }))}
            />
            <TextField
              label="Type"
              value={editing?.type ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as Subject), type: e.target.value }))}
            />
            <FormControlLabel
              control={
                <Checkbox
                  checked={editing?.lab ?? false}
                  onChange={e =>
                    setEditing(prev => ({ ...(prev as Subject), lab: e.target.checked }))
                  }
                />
              }
              label="Is Lab"
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
