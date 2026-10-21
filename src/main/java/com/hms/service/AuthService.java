package com.hms.service;
import com.hms.dao.UserDao; import com.hms.model.User; import com.hms.util.PasswordUtil; import java.sql.SQLException;
public class AuthService { private final UserDao dao; public AuthService(UserDao dao){this.dao=dao;} public User login(String u,String p)throws SQLException{if(u==null||u.isBlank()||p==null||p.isBlank())return null;return dao.authenticate(u.trim(),PasswordUtil.sha256(p)).orElse(null);} }
