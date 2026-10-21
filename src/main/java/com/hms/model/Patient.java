package com.hms.model;
import java.time.LocalDate;
public record Patient(int patientId, String registrationNo, String fullName, String gender, LocalDate dateOfBirth, String phone, String email, String address, String bloodGroup, String emergencyContact) {}
