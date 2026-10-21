package com.hms.dao;
import com.hms.model.Patient; import java.sql.SQLException; import java.util.List;
public interface PatientDao { List<Patient> findAll(String search) throws SQLException; Patient save(Patient p) throws SQLException; Patient update(Patient p) throws SQLException; void delete(int id) throws SQLException; }
