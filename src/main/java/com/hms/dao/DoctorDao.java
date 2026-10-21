package com.hms.dao;
import com.hms.model.Doctor; import java.sql.SQLException; import java.util.List;
public interface DoctorDao { List<Doctor> findAll(String search) throws SQLException; }
