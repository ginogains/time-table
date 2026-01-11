import { api } from './http';

export interface WorkingDay {
  id?: number;
  name: string;
}

export const listWorkingDays = () => api.get<WorkingDay[]>('/working-days');
export const createWorkingDay = (data: WorkingDay) => api.post<WorkingDay>('/working-days', data);
export const updateWorkingDay = (id: number, data: WorkingDay) => api.put<WorkingDay>(`/working-days/${id}`, data);
export const deleteWorkingDay = (id: number) => api.delete(`/working-days/${id}`);
