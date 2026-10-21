package com.hms.dao;
import com.hms.model.Billing; import java.sql.SQLException; import java.util.List;
public interface BillingDao { List<Billing> findAll() throws SQLException; Billing save(Billing b) throws SQLException; }
