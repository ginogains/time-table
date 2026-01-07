import { api } from './http';

export interface Subject {
  id?: number;
  code: string;
  name: string;
  type?: string;
  credits?: number;
  weeklyLectureHours?: number;
  weeklyLabHours?: number;
  lab: boolean;
}

export const listSubjects = () => api.get<Subject[]>('/subjects');
export const createSubject = (data: Subject) => api.post<Subject>('/subjects', data);
export const updateSubject = (id: number, data: Subject) => api.put<Subject>(`/subjects/${id}`, data);
export const deleteSubject = (id: number) => api.delete(`/subjects/${id}`);
