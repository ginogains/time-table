import { api } from './http';

export interface Rules {
  id?: number;
  maxHoursPerDay: number;
  maxHoursPerWeek: number;
  maxConsecutiveHours: number;
  breakDuration: number;
}

export const getRules = () => api.get<Rules[]>('/rules');
export const updateRules = (id: number, data: Rules) => api.put<Rules>(`/rules/${id}`, data);
