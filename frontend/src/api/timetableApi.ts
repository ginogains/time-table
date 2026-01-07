import { api } from './http';

export interface GenerateTimetableRequest {
  departmentId: number;
  semesterId: number;
  sectionIds: number[];
  allowSoftConstraintViolations?: boolean;
}

export interface TimetableSummary {
  id: number;
  status: string;
}

export interface TimetableEntryView {
  timeSlotId: number;
  dayName: string;
  slotIndex: number;
  subjectCode: string;
  subjectName: string;
  facultyName: string;
  roomName: string;
  sectionId: number;
  sectionName: string;
  facultyId: number;
}

export const generateTimetable = (payload: GenerateTimetableRequest) =>
  api.post<TimetableSummary>('/timetables/generate', payload);

export const getClassView = (timetableId: number, sectionId: number) =>
  api.get<TimetableEntryView[]>(`/timetables/${timetableId}/class-view`, {
    params: { sectionId },
  });

export const getFacultyView = (timetableId: number, facultyId: number) =>
  api.get<TimetableEntryView[]>(`/timetables/${timetableId}/faculty-view`, {
    params: { facultyId },
  });

export const getDepartmentView = (timetableId: number) =>
  api.get<TimetableEntryView[]>(`/timetables/${timetableId}/department-view`);

export const exportTimetable = (timetableId: number, format: 'excel' | 'pdf' = 'excel') =>
  api.get(`/timetables/${timetableId}/export`, {
    params: { format },
    responseType: 'blob',
  });
