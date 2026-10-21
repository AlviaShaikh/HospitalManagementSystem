package com.hms.ui;

import com.hms.model.Appointment;
import com.hms.model.Doctor;
import com.hms.model.Patient;
import com.hms.service.AppointmentService;
import com.hms.service.DoctorService;
import com.hms.service.PatientService;
import com.hms.util.UiUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentPanel extends JPanel {
    private final AppointmentService service;
    private final PatientService patients;
    private final DoctorService doctors;
    private final JComboBox<String> filter = new JComboBox<>(new String[]{"ALL", "Scheduled", "Completed", "Cancelled"});
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Date", "Time", "Patient", "Doctor", "Reason", "Status"}, 0);
    private final JTable table = new JTable(model);
    private List<Appointment> rows = List.of();

    public AppointmentPanel(AppointmentService service, PatientService patients, DoctorService doctors) {
        this.service = service;
        this.patients = patients;
        this.doctors = doctors;
        setLayout(new BorderLayout(8, 8));
        JPanel top = new JPanel();
        top.add(new JLabel("Status:"));
        top.add(filter);
        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> load());
        JButton add = new JButton("Book appointment");
        add.addActionListener(e -> book());
        JButton reschedule = new JButton("Reschedule");
        reschedule.addActionListener(e -> reschedule());
        JButton complete = new JButton("Mark completed");
        complete.addActionListener(e -> change("Completed"));
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> change("Cancelled"));
        top.add(refresh); top.add(add); top.add(reschedule); top.add(complete); top.add(cancel);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        filter.addActionListener(e -> load());
        load();
    }

    private void load() {
        try {
            rows = service.findAll((String) filter.getSelectedItem(), null);
            model.setRowCount(0);
            for (Appointment a : rows) model.addRow(new Object[]{a.appointmentId(), a.appointmentDate(), a.appointmentTime(), a.patientName(), a.doctorName(), a.reason(), a.status()});
        } catch (Exception e) { UiUtil.showError(this, e); }
    }

    private void change(String status) {
        int i = table.getSelectedRow();
        if (i < 0) return;
        try { service.updateStatus(rows.get(i).appointmentId(), status); load(); }
        catch (Exception e) { UiUtil.showError(this, e); }
    }

    private void reschedule() {
        int i = table.getSelectedRow();
        if (i < 0) return;
        Appointment old = rows.get(i);
        JTextField date = new JTextField(old.appointmentDate().toString());
        JTextField time = new JTextField(old.appointmentTime().toString());
        JPanel p = UiUtil.formPanel();
        GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(3, 3, 3, 3);
        UiUtil.addField(p, c, 0, "New date (yyyy-mm-dd)", date);
        UiUtil.addField(p, c, 1, "New time (HH:mm)", time);
        if (JOptionPane.showConfirmDialog(this, p, "Reschedule appointment #" + old.appointmentId(), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try { service.reschedule(old.appointmentId(), LocalDate.parse(date.getText()), LocalTime.parse(time.getText())); load(); }
            catch (Exception e) { UiUtil.showError(this, e); }
        }
    }

    private void book() {
        try {
            List<Patient> ps = patients.findAll("");
            List<Doctor> ds = doctors.findAll("");
            if (ps.isEmpty() || ds.isEmpty()) { JOptionPane.showMessageDialog(this, "Add at least one patient and doctor first."); return; }
            JComboBox<Patient> pc = new JComboBox<>(ps.toArray(Patient[]::new));
            pc.setRenderer(new DefaultListCellRenderer() { public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) { super.getListCellRendererComponent(l, v, i, s, f); if (v instanceof Patient p) setText(p.registrationNo() + " - " + p.fullName()); return this; }});
            JComboBox<Doctor> dc = new JComboBox<>(ds.toArray(Doctor[]::new));
            dc.setRenderer(new DefaultListCellRenderer() { public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) { super.getListCellRendererComponent(l, v, i, s, f); if (v instanceof Doctor d) setText(d.doctorCode() + " - " + d.fullName() + " (" + d.specialization() + ")"); return this; }});
            JTextField date = new JTextField(LocalDate.now().plusDays(1).toString()), time = new JTextField("10:00"), reason = new JTextField();
            JPanel p = UiUtil.formPanel(); GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(3, 3, 3, 3);
            UiUtil.addField(p, c, 0, "Patient", pc); UiUtil.addField(p, c, 1, "Doctor", dc); UiUtil.addField(p, c, 2, "Date (yyyy-mm-dd)", date); UiUtil.addField(p, c, 3, "Time (HH:mm)", time); UiUtil.addField(p, c, 4, "Reason", reason);
            if (JOptionPane.showConfirmDialog(this, p, "Book appointment", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                Patient pat = (Patient) pc.getSelectedItem(); Doctor doc = (Doctor) dc.getSelectedItem();
                service.save(new Appointment(0, pat.patientId(), pat.fullName(), doc.doctorId(), doc.fullName(), LocalDate.parse(date.getText()), LocalTime.parse(time.getText()), reason.getText(), "Scheduled", null));
                load();
            }
        } catch (Exception e) { UiUtil.showError(this, e); }
    }
}
