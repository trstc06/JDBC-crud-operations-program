--Creates the students table. Run this while connected to students_db.
CREATE TABLE students (
    student_id      SERIAL PRIMARY KEY, --int that postgres increments by 1
    first_name      TEXT NOT NULL,
    last_name       TEXT NOT NULL,
    email           TEXT NOT NULL UNIQUE, --2 students cant share email
    enrollment_date DATE
);