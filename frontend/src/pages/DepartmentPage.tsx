import { useEffect, useState } from 'react';
import {
  listDepartments,
  createDepartment,
  updateDepartment,
  deleteDepartment,
  type Department,
} from '../api/departmentApi';
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

export function DepartmentPage() {
  const [departments, setDepartments] = useState<Department[]>([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState<Department | null>(null);

  const load = async () => {
    const res = await listDepartments();
    setDepartments(res.data);
  };

  useEffect(() => {
    load();
  }, []);

  const startCreate = () => {
    setEditing({ code: '', name: '' });
    setOpen(true);
  };

  const startEdit = (department: Department) => {
    setEditing(department);
    setOpen(true);
  };

  const handleSave = async () => {
    if (!editing) return;
    if (editing.id) {
      await updateDepartment(editing.id, editing);
    } else {
      await createDepartment(editing);
    }
    setOpen(false);
    setEditing(null);
    load();
  };

  const handleDelete = async (id?: number) => {
    if (!id) return;
    await deleteDepartment(id);
    load();
  };

  return (
    <Stack gap={2}>
      <Stack direction="row" justifyContent="space-between" alignItems="center">
        <h2>Departments</h2>
        <Button variant="contained" onClick={startCreate}>
          Add Department
        </Button>
      </Stack>

      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Code</TableCell>
            <TableCell>Name</TableCell>
            <TableCell align="right">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {departments.map(d => (
            <TableRow key={d.id}>
              <TableCell>{d.code}</TableCell>
              <TableCell>{d.name}</TableCell>
              <TableCell align="right">
                <Button size="small" onClick={() => startEdit(d)}>
                  Edit
                </Button>
                <Button size="small" color="error" onClick={() => handleDelete(d.id)}>
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>

      <Dialog open={open} onClose={() => setOpen(false)}>
        <DialogTitle>{editing?.id ? 'Edit Department' : 'Add Department'}</DialogTitle>
        <DialogContent>
          <Stack gap={2} mt={1}>
            <TextField
              label="Code"
              value={editing?.code ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as Department), code: e.target.value }))}
            />
            <TextField
              label="Name"
              value={editing?.name ?? ''}
              onChange={e => setEditing(prev => ({ ...(prev as Department), name: e.target.value }))}
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
