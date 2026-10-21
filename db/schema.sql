CREATE DATABASE IF NOT EXISTS hospital_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hospital_management;

CREATE TABLE IF NOT EXISTS users (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash CHAR(64) NOT NULL,
  role ENUM('ADMIN','RECEPTIONIST') NOT NULL DEFAULT 'RECEPTIONIST',
  full_name VARCHAR(100) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS patients (
  patient_id INT PRIMARY KEY AUTO_INCREMENT,
  registration_no VARCHAR(30) NOT NULL UNIQUE,
  full_name VARCHAR(120) NOT NULL,
  gender ENUM('Male','Female','Other') NOT NULL,
  date_of_birth DATE NOT NULL,
  phone VARCHAR(20) NOT NULL,
  email VARCHAR(120),
  address VARCHAR(255),
  blood_group VARCHAR(5),
  emergency_contact VARCHAR(120),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS doctors (
  doctor_id INT PRIMARY KEY AUTO_INCREMENT,
  doctor_code VARCHAR(30) NOT NULL UNIQUE,
  full_name VARCHAR(120) NOT NULL,
  specialization VARCHAR(100) NOT NULL,
  department VARCHAR(100) NOT NULL,
  phone VARCHAR(20),
  available_days VARCHAR(100) NOT NULL,
  available_from TIME NOT NULL,
  available_to TIME NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS appointments (
  appointment_id INT PRIMARY KEY AUTO_INCREMENT,
  patient_id INT NOT NULL,
  doctor_id INT NOT NULL,
  appointment_date DATE NOT NULL,
  appointment_time TIME NOT NULL,
  reason VARCHAR(255),
  status ENUM('Scheduled','Completed','Cancelled') NOT NULL DEFAULT 'Scheduled',
  notes VARCHAR(500),
  created_by INT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
  CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id),
  CONSTRAINT fk_appointment_user FOREIGN KEY (created_by) REFERENCES users(user_id),
  INDEX idx_appointment_date (appointment_date, appointment_time),
  INDEX idx_appointment_patient (patient_id),
  INDEX idx_appointment_doctor (doctor_id)
);
CREATE TABLE IF NOT EXISTS billing (
  bill_id INT PRIMARY KEY AUTO_INCREMENT,
  appointment_id INT NOT NULL UNIQUE,
  consultation_fee DECIMAL(10,2) NOT NULL,
  service_fee DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  discount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  tax DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  total_amount DECIMAL(10,2) GENERATED ALWAYS AS (consultation_fee + service_fee + tax - discount) STORED,
  payment_status ENUM('Unpaid','Paid','Partially Paid') NOT NULL DEFAULT 'Unpaid',
  paid_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  issued_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_bill_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id) ON DELETE CASCADE
);

INSERT INTO users(username,password_hash,role,full_name) VALUES
('admin','240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9','ADMIN','System Administrator'),
('reception','5145dba3b6bda2d610d2c5c435a1c2481eefd3146b6a7e004ad73f794386e031','RECEPTIONIST','Front Desk Receptionist')
ON DUPLICATE KEY UPDATE username=username;
INSERT INTO doctors(doctor_code,full_name,specialization,department,phone,available_days,available_from,available_to) VALUES
('D001','Dr. Ananya Sharma','Cardiology','Cardiology','9876543210','Mon,Wed,Fri','09:00:00','13:00:00'),
('D002','Dr. Rahul Mehta','General Medicine','Outpatient','9876501234','Tue,Thu,Sat','10:00:00','16:00:00'),
('D003','Dr. Neha Iyer','Pediatrics','Pediatrics','9876512345','Mon,Tue,Thu','09:30:00','14:30:00')
ON DUPLICATE KEY UPDATE doctor_code=doctor_code;
INSERT INTO patients(registration_no,full_name,gender,date_of_birth,phone,email,address,blood_group,emergency_contact) VALUES
('P-1001','Aarav Kumar','Male','1990-04-12','9000000001','aarav@example.com','12 Lake Road','B+','Meera Kumar - 9000000002'),
('P-1002','Diya Patel','Female','1986-09-21','9000000003','diya@example.com','44 Park Avenue','O+','Ravi Patel - 9000000004')
ON DUPLICATE KEY UPDATE registration_no=registration_no;
