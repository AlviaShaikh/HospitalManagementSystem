package com.hms;
import com.hms.dao.impl.UserDaoImpl; import com.hms.service.AuthService; import com.hms.ui.LoginFrame; import javax.swing.*;
public class HospitalManagementApplication { public static void main(String[] args){SwingUtilities.invokeLater(()->{try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception ignored){}new LoginFrame(new AuthService(new UserDaoImpl())).setVisible(true);});} }
