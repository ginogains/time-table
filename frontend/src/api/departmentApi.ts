import { api } from './http';

export interface Department {
  id?: number;
  code: string;
  name: string;
}

export const listDepartments = () => api.get<Department[]>('/departments');
export const createDepartment = (data: Department) => api.post<Department>('/departments', data);
export const updateDepartment = (id: number, data: Department) => api.put<Department>(`/departments/${id}`, data);
export const deleteDepartment = (id: number) => api.delete(`/departments/${id}`);
