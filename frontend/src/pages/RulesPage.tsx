import { useEffect, useState } from 'react';
import { getRules, updateRules, type Rules } from '../api/rulesApi';
import {
  Button,
  Checkbox,
  FormControlLabel,
  Stack,
  Typography,
} from '@mui/material';

export function RulesPage() {
  const [rules, setRules] = useState<Rules>({ allowSoftConstraintViolations: false });

  const load = async () => {
    try {
      const res = await getRules();
      setRules(res.data);
    } catch {
      // If endpoint doesn't exist, use default
    }
  };

  useEffect(() => {
    load();
  }, []);

  const handleSave = async () => {
    await updateRules(rules);
    alert('Rules updated successfully');
  };

  return (
    <Stack gap={2} maxWidth={400}>
      <Typography variant="h5">Generation Rules</Typography>
      <FormControlLabel
        control={
          <Checkbox
            checked={rules.allowSoftConstraintViolations}
            onChange={e => setRules(prev => ({ ...prev, allowSoftConstraintViolations: e.target.checked }))}
          />
        }
        label="Allow soft constraint violations"
      />
      <Button variant="contained" onClick={handleSave}>
        Save Rules
      </Button>
    </Stack>
  );
}
