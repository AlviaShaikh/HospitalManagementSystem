package com.hms.model;
import java.math.BigDecimal; import java.time.LocalDateTime;
public record Billing(int billId, int appointmentId, String patientName, String doctorName, LocalDateTime visitDate, BigDecimal consultationFee, BigDecimal serviceFee, BigDecimal discount, BigDecimal tax, BigDecimal totalAmount, String paymentStatus, BigDecimal paidAmount) {}
