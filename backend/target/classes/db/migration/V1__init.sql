-- Create database if not exists
CREATE DATABASE IF NOT EXISTS timetable;
USE timetable;

-- Core reference data
CREATE TABLE department (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL
);

CREATE TABLE academic_year (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL
);

CREATE TABLE semester (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  number INT NOT NULL,
  academic_year_id BIGINT NOT NULL,
  department_id BIGINT NOT NULL,
  CONSTRAINT fk_sem_ay FOREIGN KEY (academic_year_id) REFERENCES academic_year (id),
  CONSTRAINT fk_sem_dept FOREIGN KEY (department_id) REFERENCES department (id)
);

CREATE TABLE working_day (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  order_index INT NOT NULL
);

CREATE TABLE time_slot (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  day_id BIGINT NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  slot_index INT NOT NULL,
  CONSTRAINT fk_ts_day FOREIGN KEY (day_id) REFERENCES working_day (id)
);

-- Academic structure
CREATE TABLE section (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  year INT NOT NULL,
  semester_id BIGINT NOT NULL,
  department_id BIGINT NOT NULL,
  strength INT,
  CONSTRAINT fk_sec_sem FOREIGN KEY (semester_id) REFERENCES semester (id),
  CONSTRAINT fk_sec_dept FOREIGN KEY (department_id) REFERENCES department (id)
);

CREATE TABLE subject (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(200) NOT NULL,
  type VARCHAR(30),
  credits INT,
  weekly_lecture_hours INT,
  weekly_lab_hours INT,
  is_lab BOOLEAN NOT NULL
);

CREATE TABLE faculty (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100),
  designation VARCHAR(50),
  max_hours_per_day INT,
  max_hours_per_week INT
);

CREATE TABLE room (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  type VARCHAR(30),
  capacity INT,
  is_lab BOOLEAN NOT NULL,
  department_id BIGINT,
  CONSTRAINT fk_room_dept FOREIGN KEY (department_id) REFERENCES department (id)
);

CREATE TABLE subject_faculty (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  subject_id BIGINT NOT NULL,
  faculty_id BIGINT NOT NULL,
  section_id BIGINT NOT NULL,
  priority INT,
  CONSTRAINT fk_sf_sub FOREIGN KEY (subject_id) REFERENCES subject (id),
  CONSTRAINT fk_sf_fac FOREIGN KEY (faculty_id) REFERENCES faculty (id),
  CONSTRAINT fk_sf_sec FOREIGN KEY (section_id) REFERENCES section (id)
);

CREATE TABLE faculty_availability (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  faculty_id BIGINT NOT NULL,
  time_slot_id BIGINT NOT NULL,
  is_available BOOLEAN NOT NULL,
  CONSTRAINT fk_fa_fac FOREIGN KEY (faculty_id) REFERENCES faculty (id),
  CONSTRAINT fk_fa_ts FOREIGN KEY (time_slot_id) REFERENCES time_slot (id)
);

-- Timetable storage
CREATE TABLE timetable (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  department_id BIGINT NOT NULL,
  semester_id BIGINT NOT NULL,
  generated_at TIMESTAMP NOT NULL,
  status VARCHAR(30) NOT NULL,
  CONSTRAINT fk_tt_dept FOREIGN KEY (department_id) REFERENCES department (id),
  CONSTRAINT fk_tt_sem FOREIGN KEY (semester_id) REFERENCES semester (id)
);

CREATE TABLE timetable_entry (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  timetable_id BIGINT NOT NULL,
  section_id BIGINT NOT NULL,
  subject_id BIGINT NOT NULL,
  faculty_id BIGINT NOT NULL,
  room_id BIGINT NOT NULL,
  time_slot_id BIGINT NOT NULL,
  is_lab_block_start BOOLEAN NOT NULL DEFAULT FALSE,
  block_size INT,
  CONSTRAINT fk_te_tt FOREIGN KEY (timetable_id) REFERENCES timetable (id),
  CONSTRAINT fk_te_sec FOREIGN KEY (section_id) REFERENCES section (id),
  CONSTRAINT fk_te_sub FOREIGN KEY (subject_id) REFERENCES subject (id),
  CONSTRAINT fk_te_fac FOREIGN KEY (faculty_id) REFERENCES faculty (id),
  CONSTRAINT fk_te_room FOREIGN KEY (room_id) REFERENCES room (id),
  CONSTRAINT fk_te_ts FOREIGN KEY (time_slot_id) REFERENCES time_slot (id)
);
