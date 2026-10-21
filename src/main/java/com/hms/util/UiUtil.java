package com.hms.util;
import javax.swing.*; import java.awt.*;
public final class UiUtil {
    private UiUtil() {}
    public static void showError(Component parent, Exception e) { JOptionPane.showMessageDialog(parent, e.getMessage(), "Operation failed", JOptionPane.ERROR_MESSAGE); }
    public static boolean confirm(Component parent, String message) { return JOptionPane.showConfirmDialog(parent,message,"Confirm",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION; }
    public static JPanel formPanel() { JPanel p=new JPanel(new GridBagLayout()); p.setBorder(BorderFactory.createEmptyBorder(8,8,8,8)); return p; }
    public static void addField(JPanel p, GridBagConstraints c, int row, String label, Component field) { c.gridx=0;c.gridy=row;c.weightx=0;c.fill=GridBagConstraints.NONE;p.add(new JLabel(label),c); c.gridx=1;c.weightx=1;c.fill=GridBagConstraints.HORIZONTAL;p.add(field,c); }
}
