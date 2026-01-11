import { api } from './http';

export interface Room {
  id?: number;
  name: string;
  type: string;
  capacity: number;
}

export const listRooms = () => api.get<Room[]>('/rooms');
export const createRoom = (data: Room) => api.post<Room>('/rooms', data);
export const updateRoom = (id: number, data: Room) => api.put<Room>(`/rooms/${id}`, data);
export const deleteRoom = (id: number) => api.delete(`/rooms/${id}`);
