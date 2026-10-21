package com.hms.ui;

import com.hms.model.Appointment;
import com.hms.model.Billing;
import com.hms.service.AppointmentService;
import com.hms.service.BillingService;
import com.hms.util.UiUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BillingPanel extends JPanel {
    private final BillingService service;
    private final AppointmentService appointments;
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"Bill", "Appt.", "Patient", "Doctor", "Visit", "Consultation", "Service", "Discount", "Tax", "Total", "Payment"}, 0);
    private final JTable table = new JTable(model);
    private List<Billing> rows = List.of();

    public BillingPanel(BillingService service, AppointmentService appointments) {
        this.service = service;
        this.appointments = appointments;
        setLayout(new BorderLayout(8, 8));
        JPanel top = new JPanel();
        JButton refresh = new JButton("Refresh"); refresh.addActionListener(e -> load());
        JButton issue = new JButton("Generate visit slip"); issue.addActionListener(e -> issue());
        JButton display = new JButton("Display selected slip"); display.addActionListener(e -> display());
        top.add(refresh); top.add(issue); top.add(display);
        add(top, BorderLayout.NORTH); add(new JScrollPane(table), BorderLayout.CENTER); load();
    }

    private void load() {
        try {
            rows = service.findAll(); model.setRowCount(0);
            for (Billing b : rows) model.addRow(new Object[]{b.billId(), b.appointmentId(), b.patientName(), b.doctorName(), b.visitDate(), b.consultationFee(), b.serviceFee(), b.discount(), b.tax(), b.totalAmount(), b.paymentStatus()});
        } catch (Exception e) { UiUtil.showError(this, e); }
    }

    private void issue() {
        try {
            List<Appointment> as = appointments.findAll("Completed", null);
            if (as.isEmpty()) { JOptionPane.showMessageDialog(this, "Mark an appointment Completed before billing."); return; }
            JComboBox<Appointment> ac = new JComboBox<>(as.toArray(Appointment[]::new));
            ac.setRenderer(new DefaultListCellRenderer() {
                public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                    super.getListCellRendererComponent(l, v, i, s, f);
                    if (v instanceof Appointment a) setText("#" + a.appointmentId() + " - " + a.patientName() + " / " + a.doctorName() + " / " + a.appointmentDate());
                    return this;
                }
            });
            JTextField consultation = new JTextField("500.00"), serviceFee = new JTextField("0.00"), discount = new JTextField("0.00"), tax = new JTextField("0.00"), paid = new JTextField("0.00");
            JComboBox<String> status = new JComboBox<>(new String[]{"Unpaid", "Paid", "Partially Paid"});
            JPanel p = UiUtil.formPanel(); GridBagConstraints c = new GridBagConstraints(); c.insets = new Insets(3, 3, 3, 3);
            UiUtil.addField(p, c, 0, "Completed appointment", ac); UiUtil.addField(p, c, 1, "Consultation fee", consultation); UiUtil.addField(p, c, 2, "Service fee", serviceFee); UiUtil.addField(p, c, 3, "Discount", discount); UiUtil.addField(p, c, 4, "Tax", tax); UiUtil.addField(p, c, 5, "Payment status", status); UiUtil.addField(p, c, 6, "Paid amount", paid);
            if (JOptionPane.showConfirmDialog(this, p, "Generate billing slip", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                Appointment a = (Appointment) ac.getSelectedItem();
                BigDecimal cFee = new BigDecimal(consultation.getText()), sFee = new BigDecimal(serviceFee.getText()), disc = new BigDecimal(discount.getText()), taxValue = new BigDecimal(tax.getText());
                Billing b = new Billing(0, a.appointmentId(), a.patientName(), a.doctorName(), LocalDateTime.now(), cFee, sFee, disc, taxValue, BillingService.total(cFee, sFee, disc, taxValue), (String) status.getSelectedItem(), new BigDecimal(paid.getText()));
                service.save(b); load();
            }
        } catch (Exception e) { UiUtil.showError(this, e); }
    }

    private void display() {
        int i = table.getSelectedRow(); if (i < 0) return;
        Billing b = rows.get(i);
        String slip = "HOSPITAL MANAGEMENT SYSTEM\n\n" +
                "VISIT SLIP / INVOICE\n" +
                "Bill No: " + b.billId() + "\n" +
                "Appointment: " + b.appointmentId() + "\n" +
                "Patient: " + b.patientName() + "\n" +
                "Doctor: " + b.doctorName() + "\n" +
                "Visit: " + b.visitDate() + "\n\n" +
                "Consultation: " + b.consultationFee() + "\n" +
                "Service: " + b.serviceFee() + "\n" +
                "Discount: " + b.discount() + "\n" +
                "Tax: " + b.tax() + "\n" +
                "TOTAL: " + b.totalAmount() + "\n" +
                "Payment: " + b.paymentStatus() + "\n\n" +
                "Thank you.";
        JTextArea area = new JTextArea(slip, 18, 45); area.setEditable(false); area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Visit slip", JOptionPane.INFORMATION_MESSAGE);
    }
}
