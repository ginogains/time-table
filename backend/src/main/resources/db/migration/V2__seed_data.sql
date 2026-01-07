-- Seed data for timetable application
-- Run this after V1__init.sql to populate the database with test data

USE timetable;

-- Insert working days
INSERT INTO working_day (name, order_index) VALUES 
('Monday', 1),
('Tuesday', 2),
('Wednesday', 3),
('Thursday', 4),
('Friday', 5);

-- Insert time slots (5 days x 6 slots per day)
-- Monday slots
INSERT INTO time_slot (day_id, start_time, end_time, slot_index) VALUES 
(1, '09:00:00', '10:00:00', 1),
(1, '10:00:00', '11:00:00', 2),
(1, '11:00:00', '12:00:00', 3),
(1, '13:00:00', '14:00:00', 4),
(1, '14:00:00', '15:00:00', 5),
(1, '15:00:00', '16:00:00', 6);

-- Tuesday slots
INSERT INTO time_slot (day_id, start_time, end_time, slot_index) VALUES 
(2, '09:00:00', '10:00:00', 1),
(2, '10:00:00', '11:00:00', 2),
(2, '11:00:00', '12:00:00', 3),
(2, '13:00:00', '14:00:00', 4),
(2, '14:00:00', '15:00:00', 5),
(2, '15:00:00', '16:00:00', 6);

-- Wednesday slots
INSERT INTO time_slot (day_id, start_time, end_time, slot_index) VALUES 
(3, '09:00:00', '10:00:00', 1),
(3, '10:00:00', '11:00:00', 2),
(3, '11:00:00', '12:00:00', 3),
(3, '13:00:00', '14:00:00', 4),
(3, '14:00:00', '15:00:00', 5),
(3, '15:00:00', '16:00:00', 6);

-- Thursday slots
INSERT INTO time_slot (day_id, start_time, end_time, slot_index) VALUES 
(4, '09:00:00', '10:00:00', 1),
(4, '10:00:00', '11:00:00', 2),
(4, '11:00:00', '12:00:00', 3),
(4, '13:00:00', '14:00:00', 4),
(4, '14:00:00', '15:00:00', 5),
(4, '15:00:00', '16:00:00', 6);

-- Friday slots
INSERT INTO time_slot (day_id, start_time, end_time, slot_index) VALUES 
(5, '09:00:00', '10:00:00', 1),
(5, '10:00:00', '11:00:00', 2),
(5, '11:00:00', '12:00:00', 3),
(5, '13:00:00', '14:00:00', 4),
(5, '14:00:00', '15:00:00', 5),
(5, '15:00:00', '16:00:00', 6);

-- Insert academic year
INSERT INTO academic_year (name, start_date, end_date) VALUES 
('2024-2025', '2024-08-01', '2025-07-31');

-- Insert department
INSERT INTO department (code, name) VALUES 
('CSE', 'Computer Science and Engineering'),
('IT', 'Information Technology'),
('ECE', 'Electronics and Communication Engineering');

-- Insert semester (linked to academic year and department)
INSERT INTO semester (number, academic_year_id, department_id) VALUES 
(1, 1, 1),
(2, 1, 1),
(3, 1, 1),
(4, 1, 1),
(5, 1, 1),
(6, 1, 1),
(1, 1, 2),
(2, 1, 2),
(3, 1, 2),
(4, 1, 2),
(5, 1, 2),
(6, 1, 2);

-- Insert sections (for CSE department - semester 1)
INSERT INTO section (name, year, semester_id, department_id, strength) VALUES 
('CSE-A', 1, 1, 1, 60),
('CSE-B', 1, 1, 1, 60),
('CSE-C', 1, 1, 1, 60);

-- Insert faculty
INSERT INTO faculty (code, name, email, designation, max_hours_per_day, max_hours_per_week) VALUES 
('FAC001', 'Dr. John Smith', 'john.smith@college.edu', 'Professor', 6, 30),
('FAC002', 'Dr. Jane Doe', 'jane.doe@college.edu', 'Associate Professor', 6, 30),
('FAC003', 'Prof. Robert Johnson', 'robert.johnson@college.edu', 'Assistant Professor', 6, 30),
('FAC004', 'Dr. Emily Brown', 'emily.brown@college.edu', 'Professor', 6, 30),
('FAC005', 'Prof. Michael Wilson', 'michael.wilson@college.edu', 'Assistant Professor', 6, 30),
('FAC006', 'Dr. Sarah Davis', 'sarah.davis@college.edu', 'Associate Professor', 6, 30);

-- Insert subjects
INSERT INTO subject (code, name, type, credits, weekly_lecture_hours, weekly_lab_hours, is_lab) VALUES 
('CS101', 'Programming Fundamentals', 'Core', 4, 3, 2, FALSE),
('CS102', 'Data Structures', 'Core', 4, 3, 2, FALSE),
('CS103', 'Digital Electronics', 'Core', 3, 3, 0, FALSE),
('CS104', 'Computer Organization', 'Core', 3, 3, 0, FALSE),
('CS105', 'Mathematics-I', 'Core', 4, 4, 0, FALSE),
('CS106', 'Physics', 'Core', 4, 3, 2, FALSE),
('CS107', 'English Communication', 'HS', 2, 2, 0, FALSE),
('CS201', 'Algorithms', 'Core', 4, 3, 2, TRUE),
('CS202', 'Database Systems', 'Core', 4, 3, 2, TRUE),
('CS203', 'Operating Systems', 'Core', 4, 3, 2, TRUE);

-- Insert rooms
INSERT INTO room (name, type, capacity, is_lab, department_id) VALUES 
('Room 101', 'Classroom', 60, FALSE, 1),
('Room 102', 'Classroom', 60, FALSE, 1),
('Room 103', 'Classroom', 60, FALSE, 1),
('Lab 201', 'Laboratory', 30, TRUE, 1),
('Lab 202', 'Laboratory', 30, TRUE, 1),
('Lab 203', 'Laboratory', 30, TRUE, 1);

-- Insert subject-faculty-section mappings
INSERT INTO subject_faculty (subject_id, faculty_id, section_id, priority) VALUES 
(1, 1, 1, 1),
(1, 1, 2, 2),
(2, 2, 1, 1),
(2, 2, 2, 2),
(3, 3, 1, 1),
(4, 3, 2, 1),
(5, 4, 1, 1),
(6, 4, 2, 1),
(7, 5, 1, 1),
(1, 1, 3, 3),
(2, 2, 3, 3),
(3, 3, 3, 2);

-- Insert faculty availability (all available for simplicity)
INSERT INTO faculty_availability (faculty_id, time_slot_id, is_available) VALUES 
(1, 1, TRUE), (1, 2, TRUE), (1, 3, TRUE), (1, 4, TRUE), (1, 5, TRUE), (1, 6, TRUE),
(1, 7, TRUE), (1, 8, TRUE), (1, 9, TRUE), (1, 10, TRUE), (1, 11, TRUE), (1, 12, TRUE),
(1, 13, TRUE), (1, 14, TRUE), (1, 15, TRUE), (1, 16, TRUE), (1, 17, TRUE), (1, 18, TRUE),
(1, 19, TRUE), (1, 20, TRUE), (1, 21, TRUE), (1, 22, TRUE), (1, 23, TRUE), (1, 24, TRUE),
(1, 25, TRUE), (1, 26, TRUE), (1, 27, TRUE), (1, 28, TRUE), (1, 29, TRUE), (1, 30, TRUE),
(2, 1, TRUE), (2, 2, TRUE), (2, 3, TRUE), (2, 4, TRUE), (2, 5, TRUE), (2, 6, TRUE),
(2, 7, TRUE), (2, 8, TRUE), (2, 9, TRUE), (2, 10, TRUE), (2, 11, TRUE), (2, 12, TRUE),
(2, 13, TRUE), (2, 14, TRUE), (2, 15, TRUE), (2, 16, TRUE), (2, 17, TRUE), (2, 18, TRUE),
(2, 19, TRUE), (2, 20, TRUE), (2, 21, TRUE), (2, 22, TRUE), (2, 23, TRUE), (2, 24, TRUE),
(2, 25, TRUE), (2, 26, TRUE), (2, 27, TRUE), (2, 28, TRUE), (2, 29, TRUE), (2, 30, TRUE),
(3, 1, TRUE), (3, 2, TRUE), (3, 3, TRUE), (3, 4, TRUE), (3, 5, TRUE), (3, 6, TRUE),
(3, 7, TRUE), (3, 8, TRUE), (3, 9, TRUE), (3, 10, TRUE), (3, 11, TRUE), (3, 12, TRUE),
(3, 13, TRUE), (3, 14, TRUE), (3, 15, TRUE), (3, 16, TRUE), (3, 17, TRUE), (3, 18, TRUE),
(3, 19, TRUE), (3, 20, TRUE), (3, 21, TRUE), (3, 22, TRUE), (3, 23, TRUE), (3, 24, TRUE),
(3, 25, TRUE), (3, 26, TRUE), (3, 27, TRUE), (3, 28, TRUE), (3, 29, TRUE), (3, 30, TRUE),
(4, 1, TRUE), (4, 2, TRUE), (4, 3, TRUE), (4, 4, TRUE), (4, 5, TRUE), (4, 6, TRUE),
(4, 7, TRUE), (4, 8, TRUE), (4, 9, TRUE), (4, 10, TRUE), (4, 11, TRUE), (4, 12, TRUE),
(4, 13, TRUE), (4, 14, TRUE), (4, 15, TRUE), (4, 16, TRUE), (4, 17, TRUE), (4, 18, TRUE),
(4, 19, TRUE), (4, 20, TRUE), (4, 21, TRUE), (4, 22, TRUE), (4, 23, TRUE), (4, 24, TRUE),
(4, 25, TRUE), (4, 26, TRUE), (4, 27, TRUE), (4, 28, TRUE), (4, 29, TRUE), (4, 30, TRUE),
(5, 1, TRUE), (5, 2, TRUE), (5, 3, TRUE), (5, 4, TRUE), (5, 5, TRUE), (5, 6, TRUE),
(5, 7, TRUE), (5, 8, TRUE), (5, 9, TRUE), (5, 10, TRUE), (5, 11, TRUE), (5, 12, TRUE),
(5, 13, TRUE), (5, 14, TRUE), (5, 15, TRUE), (5, 16, TRUE), (5, 17, TRUE), (5, 18, TRUE),
(5, 19, TRUE), (5, 20, TRUE), (5, 21, TRUE), (5, 22, TRUE), (5, 23, TRUE), (5, 24, TRUE),
(5, 25, TRUE), (5, 26, TRUE), (5, 27, TRUE), (5, 28, TRUE), (5, 29, TRUE), (5, 30, TRUE),
(6, 1, TRUE), (6, 2, TRUE), (6, 3, TRUE), (6, 4, TRUE), (6, 5, TRUE), (6, 6, TRUE),
(6, 7, TRUE), (6, 8, TRUE), (6, 9, TRUE), (6, 10, TRUE), (6, 11, TRUE), (6, 12, TRUE),
(6, 13, TRUE), (6, 14, TRUE), (6, 15, TRUE), (6, 16, TRUE), (6, 17, TRUE), (6, 18, TRUE),
(6, 19, TRUE), (6, 20, TRUE), (6, 21, TRUE), (6, 22, TRUE), (6, 23, TRUE), (6, 24, TRUE),
(6, 25, TRUE), (6, 26, TRUE), (6, 27, TRUE), (6, 28, TRUE), (6, 29, TRUE), (6, 30, TRUE);

-- Verify data
SELECT 'Departments created:' AS message, COUNT(*) AS count FROM department;
SELECT 'Semesters created:' AS message, COUNT(*) AS count FROM semester;
SELECT 'Sections created:' AS message, COUNT(*) AS count FROM section;
SELECT 'Faculty created:' AS message, COUNT(*) AS count FROM faculty;
SELECT 'Subjects created:' AS message, COUNT(*) AS count FROM subject;
SELECT 'Rooms created:' AS message, COUNT(*) AS count FROM room;
SELECT 'Time slots created:' AS message, COUNT(*) AS count FROM time_slot;
SELECT 'Subject-faculty mappings created:' AS message, COUNT(*) AS count FROM subject_faculty;
SELECT 'Faculty availability records created:' AS message, COUNT(*) AS count FROM faculty_availability;

