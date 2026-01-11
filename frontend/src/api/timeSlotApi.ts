import { api } from './http';

export interface TimeSlot {
  id?: number;
  dayId: string | number;
  startTime: string;
  endTime: string;
}

export const listTimeSlots = () => api.get<TimeSlot[]>('/time-slots');
export const createTimeSlot = (data: TimeSlot) => api.post<TimeSlot>('/time-slots', data);
export const updateTimeSlot = (id: number, data: TimeSlot) => api.put<TimeSlot>(`/time-slots/${id}`, data);
export const deleteTimeSlot = (id: number) => api.delete(`/time-slots/${id}`);
