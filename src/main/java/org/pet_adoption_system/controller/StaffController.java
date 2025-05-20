package org.pet_adoption_system.controller;

import org.pet_adoption_system.dao.StaffDao;
import org.pet_adoption_system.model.Staff;
import org.pet_adoption_system.view.components.TableComponents;
import org.pet_adoption_system.view.staff.StaffButtonsHeader;
import org.pet_adoption_system.view.staff.StaffFormDialog;
import org.pet_adoption_system.view.staff.StaffView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.regex.Pattern;

public class StaffController {

    private final StaffView view;
    private final StaffDao staffDao;
    private final StaffButtonsHeader topBar;
    private final TableComponents table;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");

    public StaffController(StaffView view) {
        this.view = view;
        this.staffDao = new StaffDao();
        this.topBar = view.getButtonsHeader();
        this.table = view.getTable();
        attachListeners();
        loadAllStaff();
    }

    private void attachListeners() {
        topBar.getAddButton().addActionListener(this::handleAdd);
        topBar.getEditButton().addActionListener(this::handleEdit);
        topBar.getDeleteButton().addActionListener(this::handleDelete);
        topBar.getSearchButton().addActionListener(this::handleSearch);
    }

    private boolean validateStaff(Staff staff) {
        StringBuilder errorMessage = new StringBuilder();
        boolean isValid = true;

        if (staff.getFirst_name() == null || staff.getFirst_name().isEmpty()) {
            errorMessage.append("First name cannot be empty.\n");
            isValid = false;
        } else if (!Character.isUpperCase(staff.getFirst_name().charAt(0))) {
            errorMessage.append("First name must start with a capital letter.\n");
            isValid = false;
        } else if (!NAME_PATTERN.matcher(staff.getFirst_name()).matches()) {
            errorMessage.append("First name must contain letters only.\n");
            isValid = false;
        }

        if (staff.getLast_name() == null || staff.getLast_name().isEmpty()) {
            errorMessage.append("Last name cannot be empty.\n");
            isValid = false;
        } else if (!Character.isUpperCase(staff.getLast_name().charAt(0))) {
            errorMessage.append("Last name must start with a capital letter.\n");
            isValid = false;
        } else if (!NAME_PATTERN.matcher(staff.getLast_name()).matches()) {
            errorMessage.append("Last name must contain letters only.\n");
            isValid = false;
        }

        if (!EMAIL_PATTERN.matcher(staff.getEmail()).matches()) {
            errorMessage.append("Please enter a valid email address.\n");
            isValid = false;
        }

        if (!PHONE_PATTERN.matcher(staff.getPhone_number()).matches()) {
            errorMessage.append("Phone number must be exactly 10 digits.\n");
            isValid = false;
        }

        if (staff.getPassword() == null || staff.getPassword().isEmpty()) {
            errorMessage.append("Password cannot be empty.\n");
            isValid = false;
        } else if (!PASSWORD_PATTERN.matcher(staff.getPassword()).matches()) {
            errorMessage.append("Password must contain at least one number, one capital letter, and one special character.\n");
            isValid = false;
        }

        if (!isValid) {
            JOptionPane.showMessageDialog(view, errorMessage.toString(),
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        }

        return isValid;
    }

    private void handleAdd(ActionEvent e) {
        new StaffFormDialog(view, "Add New Staff", null, staff -> {
            if (!validateStaff(staff)) {
                return;
            }
            staffDao.addStaff(staff);
            loadAllStaff();
            JOptionPane.showMessageDialog(view, "Staff added successfully.");
        }).setVisible(true);
    }

    private void handleEdit(ActionEvent e) {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a staff member to edit.");
            return;
        }

        int staffId = (int) table.getTableModel().getValueAt(selectedRow, 0);
        Staff staff = staffDao.getStaffById(staffId).stream().findFirst().orElse(null);

        if (staff == null) {
            JOptionPane.showMessageDialog(view, "Staff member not found.");
            return;
        }

        new StaffFormDialog(view, "Edit Staff", staff, updatedStaff -> {
            if (!validateStaff(updatedStaff)) {
                return;
            }
            staffDao.updateStaff(updatedStaff);
            loadAllStaff();
            JOptionPane.showMessageDialog(view, "Staff updated successfully.");
        }).setVisible(true);
    }

    private void handleDelete(ActionEvent e) {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a staff member to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete this staff member?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int staffId = (int) table.getTableModel().getValueAt(selectedRow, 0);
            staffDao.deleteStaff(staffId);
            loadAllStaff();
            JOptionPane.showMessageDialog(view, "Staff deleted successfully.");
        }
    }

    private void handleSearch(ActionEvent e) {
        String idText = topBar.getIdField().getText().trim();
        table.getTableModel().setRowCount(0);

        if (idText.isEmpty()) {
            loadAllStaff();
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            Staff staff = staffDao.getStaffById(id).stream().findFirst().orElse(null);
            if (staff != null) {
                table.addRow(new Object[]{
                        staff.getStaff_id(),
                        staff.getFirst_name(),
                        staff.getLast_name(),
                        staff.getEmail(),
                        staff.getPhone_number(),
                        staff.getPosition()
                });
            } else {
                JOptionPane.showMessageDialog(view, "No staff found with ID: " + id);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.");
        }
    }

    private void loadAllStaff() {
        table.getTableModel().setRowCount(0);
        List<Staff> staffList = staffDao.getAllStaff();

        for (Staff staff : staffList) {
            table.addRow(new Object[]{
                    staff.getStaff_id(),
                    staff.getFirst_name(),
                    staff.getLast_name(),
                    staff.getEmail(),
                    staff.getPhone_number(),
                    staff.getPosition()
            });
        }
    }
}