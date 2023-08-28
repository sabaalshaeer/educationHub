-- Drop Teacher_Student Table (Many-to-Many Relationship)
DROP TABLE IF EXISTS teacher_student;
-- Drop Student Table
DROP TABLE IF EXISTS student;
-- Drop Teacher Table
DROP TABLE IF EXISTS teacher;
-- Drop School Table
DROP TABLE IF EXISTS school;

-- Create School Table
CREATE TABLE school (
    school_id INT NOT NULL AUTO_INCREMENT,
    school_name VARCHAR(255),
    school_address VARCHAR(255),
    school_city VARCHAR(255),
    school_state VARCHAR(255),
    school_zip VARCHAR(10),
    school_phone VARCHAR(20),
    PRIMARY KEY (school_id)
);

-- Create Teacher Table
CREATE TABLE teacher (
    teacher_id INT NOT NULL AUTO_INCREMENT,
    teacher_first_name VARCHAR(255),
    teacher_last_name VARCHAR(255),
    teacher_email VARCHAR(255),
    teacher_subject VARCHAR(255),
    school_id INT,
    PRIMARY KEY (teacher_id),
    FOREIGN KEY (school_id) REFERENCES school(school_id) ON DELETE CASCADE,
    UNIQUE KEY (teacher_email)
);

-- Create Student Table
CREATE TABLE student (
    student_id INT NOT NULL AUTO_INCREMENT,
    student_first_name VARCHAR(255),
    student_last_name VARCHAR(255),
    age INT,
    student_email VARCHAR(255),
    grade INT,
    school_id INT,
    PRIMARY KEY (student_id),
    FOREIGN KEY (school_id) REFERENCES school(school_id) ON DELETE CASCADE,
    UNIQUE KEY (student_email)
);

-- Create Teacher_Student Table (Many-to-Many Relationship)
CREATE TABLE teacher_student (
    teacher_id INT,
    student_id INT,
    PRIMARY KEY (teacher_id, student_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES student(student_id)ON DELETE CASCADE
);
