import { useState } from 'react';
import { Button, Stack, TextField, Typography, Alert, Box, CircularProgress } from '@mui/material';
import { generateTimetable } from '../api/timetableApi';
import { api } from '../api/http';

interface HealthResponse {
  status: string;
  timestamp: string;
  service: string;
  version: string;
}

export function GenerateTimetablePage() {
  const [departmentId, setDepartmentId] = useState(1);
  const [semesterId, setSemesterId] = useState(1);
  const [sectionIds, setSectionIds] = useState('1');
  const [result, setResult] = useState<{ id: number; status: string } | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [backendStatus, setBackendStatus] = useState<'unknown' | 'online' | 'offline'>('unknown');

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
      const parsedSectionIds = sectionIds
          .split(',')
          .map(s => s.trim())
          .filter(Boolean)
          .map(Number);
      
      if (parsedSectionIds.length === 0) {
        setError('Please enter at least one section ID');
        setLoading(false);
        return;
      }
      
      const res = await generateTimetable({
        departmentId,
        semesterId,
        sectionIds: parsedSectionIds,
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

      <TextField
        type="number"
        label="Department ID"
        value={departmentId}
        onChange={e => setDepartmentId(Number(e.target.value))}
        disabled={loading}
      />
      <TextField
        type="number"
        label="Semester ID"
        value={semesterId}
        onChange={e => setSemesterId(Number(e.target.value))}
        disabled={loading}
      />
      <TextField
        label="Section IDs (comma separated)"
        value={sectionIds}
        onChange={e => setSectionIds(e.target.value)}
        disabled={loading}
        helperText="Enter section IDs separated by commas, e.g., 1, 2, 3"
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
