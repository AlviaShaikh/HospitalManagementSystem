package com.hms.dao;
import com.hms.model.User; import java.sql.SQLException; import java.util.Optional;
public interface UserDao { Optional<User> authenticate(String username, String passwordHash) throws SQLException; }
