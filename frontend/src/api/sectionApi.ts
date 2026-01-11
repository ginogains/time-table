import { api } from './http';

export interface Section {
  id?: number;
  name: string;
  year: number;
  semesterId: string | number;
  departmentId: string | number;
  strength?: number;
}

export const listSections = () => api.get<Section[]>('/sections');
export const createSection = (data: Section) => api.post<Section>('/sections', data);
export const updateSection = (id: number, data: Section) => api.put<Section>(`/sections/${id}`, data);
export const deleteSection = (id: number) => api.delete(`/sections/${id}`);
