import { useState, useEffect } from 'react';
import { Button, Stack, TextField, Typography, Alert, Box, CircularProgress, Autocomplete } from '@mui/material';
import { generateTimetable } from '../api/timetableApi';
import { api } from '../api/http';
import { listDepartments, type Department } from '../api/departmentApi';
import { listSemesters, type Semester } from '../api/semesterApi';
import { listSections, type Section } from '../api/sectionApi';

interface HealthResponse {
  status: string;
  timestamp: string;
  service: string;
  version: string;
}

export function GenerateTimetablePage() {
  const [departmentId, setDepartmentId] = useState(1);
  const [semesterId, setSemesterId] = useState(1);
  const [selectedSections, setSelectedSections] = useState<number[]>([]);
  const [departments, setDepartments] = useState<Department[]>([]);
  const [semesters, setSemesters] = useState<Semester[]>([]);
  const [sections, setSections] = useState<Section[]>([]);
  const [result, setResult] = useState<{ id: number; status: string } | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [backendStatus, setBackendStatus] = useState<'unknown' | 'online' | 'offline'>('unknown');

  const loadData = async () => {
    try {
      const [departmentsRes, semestersRes, sectionsRes] = await Promise.all([
        listDepartments(),
        listSemesters(),
        listSections(),
      ]);
      setDepartments(departmentsRes.data);
      setSemesters(semestersRes.data);
      setSections(sectionsRes.data);
    } catch (e) {
      console.error('Failed to load data', e);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const checkBackendHealth = async () => {
    try {
      const res = await api.get<HealthResponse>('/health');
      if (res.data.status === 'UP') {
        setBackendStatus('online');
      } else {
        setBackendStatus('offline');
      }
    } catch {
      setBackendStatus('offline');
    }
  };

  const handleGenerate = async () => {
    setError(null);
    setLoading(true);
    
    // First check if backend is available
    await checkBackendHealth();
    
    if (backendStatus === 'offline') {
      setError('Backend server is not running. Please start the backend server on http://localhost:8080');
      setLoading(false);
      return;
    }

    try {
      if (selectedSections.length === 0) {
        setError('Please select at least one section');
        setLoading(false);
        return;
      }

      const res = await generateTimetable({
        departmentId,
        semesterId,
        sectionIds: selectedSections,
      });
      setResult(res.data);
    } catch (e: any) {
      const status = e.response?.status;
      const data = e.response?.data;
      
      if (status === 404) {
        const errorCode = data?.code || 'UNKNOWN';
        const errorMessage = data?.message || 'Endpoint not found';
        const hint = data?.hint || '';
        
        if (errorCode === 'ENDPOINT_NOT_FOUND') {
          setError(`404: ${errorMessage}. ${hint}`);
        } else {
          setError(`404: Resource not found - ${errorMessage}`);
        }
      } else if (status === 500) {
        const errorMessage = data?.message || 'Internal server error';
        const hint = data?.hint || '';
        setError(`500: ${errorMessage}. ${hint}`);
      } else if (status === 400) {
        setError(`400: ${data?.message || 'Validation error'}`);
      } else if (status === 0) {
        setError('Cannot connect to backend server. Please ensure it is running on http://localhost:8080');
      } else {
        setError(`Error: ${e.message || 'Unknown error'} (Status: ${status || 'N/A'})`);
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <Stack gap={2} maxWidth={420}>
      <Typography variant="h5">Generate Timetable</Typography>
      
      {/* Backend Status */}
      <Box>
        <Typography variant="body2" color="text.secondary" gutterBottom>
          Backend Status: 
          {backendStatus === 'online' && <Typography component="span" color="success.main"> Online</Typography>}
          {backendStatus === 'offline' && <Typography component="span" color="error.main"> Offline</Typography>}
          {backendStatus === 'unknown' && <Typography component="span" color="warning.main"> Unknown</Typography>}
        </Typography>
        <Button size="small" variant="outlined" onClick={checkBackendHealth} disabled={loading}>
          Check Backend Health
        </Button>
      </Box>

      <Autocomplete
        fullWidth
        disabled={loading}
        options={departments}
        getOptionLabel={(option) => `${option.name} (${option.code})`}
        value={departments.find(d => d.id === departmentId) || null}
        onChange={(_, newValue) => setDepartmentId(newValue?.id || 1)}
        renderInput={(params) => <TextField {...params} label="Department" />}
      />
      <Autocomplete
        fullWidth
        disabled={loading}
        options={semesters}
        getOptionLabel={(option) => option.name}
        value={semesters.find(s => s.id === semesterId) || null}
        onChange={(_, newValue) => setSemesterId(newValue?.id || 1)}
        renderInput={(params) => <TextField {...params} label="Semester" />}
      />
      <Autocomplete
        multiple
        fullWidth
        disabled={loading}
        options={sections}
        getOptionLabel={(option) => option.name}
        value={sections.filter(s => selectedSections.includes(s.id))}
        onChange={(_, newValue) => setSelectedSections(newValue.map(s => s.id))}
        renderInput={(params) => <TextField {...params} label="Sections" />}
      />
      
      <Button 
        variant="contained" 
        onClick={handleGenerate}
        disabled={loading}
      >
        {loading ? <CircularProgress size={24} color="inherit" /> : 'Generate'}
      </Button>
      
      {result && (
        <Alert severity="success">
          Generated timetable #{result.id} (status: {result.status})
        </Alert>
      )}
      
      {error && (
        <Alert severity="error">
          {error}
        </Alert>
      )}
    </Stack>
  );
}
