package com.hms.dao;
import com.hms.model.Appointment; import java.sql.SQLException; import java.time.LocalDate; import java.util.List;
public interface AppointmentDao { List<Appointment> findAll(String status, LocalDate from) throws SQLException; Appointment save(Appointment a) throws SQLException; void reschedule(int id, LocalDate date, java.time.LocalTime time) throws SQLException; void updateStatus(int id,String status) throws SQLException; void delete(int id) throws SQLException; }
