import { api } from './http';

export interface Faculty {
  id?: number;
  name: string;
  email: string;
  departmentId: string | number;
}

export const listFaculty = () => api.get<Faculty[]>('/faculty');
export const createFaculty = (data: Faculty) => api.post<Faculty>('/faculty', data);
export const updateFaculty = (id: number, data: Faculty) => api.put<Faculty>(`/faculty/${id}`, data);
export const deleteFaculty = (id: number) => api.delete(`/faculty/${id}`);
