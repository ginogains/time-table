import { api } from './http';

export interface Semester {
  id?: number;
  name: string;
}

export const listSemesters = () => api.get<Semester[]>('/semesters');
export const createSemester = (data: Semester) => api.post<Semester>('/semesters', data);
export const updateSemester = (id: number, data: Semester) => api.put<Semester>(`/semesters/${id}`, data);
export const deleteSemester = (id: number) => api.delete(`/semesters/${id}`);
