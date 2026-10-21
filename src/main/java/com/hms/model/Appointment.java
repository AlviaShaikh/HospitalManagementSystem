package com.hms.model;
import java.time.LocalDate; import java.time.LocalTime;
public record Appointment(int appointmentId, int patientId, String patientName, int doctorId, String doctorName, LocalDate appointmentDate, LocalTime appointmentTime, String reason, String status, String notes) {}
