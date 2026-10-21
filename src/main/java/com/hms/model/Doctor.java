package com.hms.model;
import java.time.LocalTime;
public record Doctor(int doctorId, String doctorCode, String fullName, String specialization, String department, String phone, String availableDays, LocalTime availableFrom, LocalTime availableTo, boolean active) {}
