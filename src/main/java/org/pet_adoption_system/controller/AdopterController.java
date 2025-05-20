package org.pet_adoption_system.controller;

import org.pet_adoption_system.dao.AdopterDao;
import org.pet_adoption_system.model.Adopter;
import org.pet_adoption_system.view.adopter.AdopterButtonsHeader;
import org.pet_adoption_system.view.adopter.AdopterFormDialog;
import org.pet_adoption_system.view.adopter.AdopterView;
import org.pet_adoption_system.view.components.TableComponents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.regex.Pattern;

public class AdopterController {

    private final AdopterView view;
    private final AdopterDao adopterDao;
    private final AdopterButtonsHeader topBar;
    private final TableComponents table;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");

    public AdopterController(AdopterView view) {
        this.view = view;
        this.adopterDao = new AdopterDao();
        this.topBar = view.getButtonsHeader();
        this.table = view.getTable();
        attachListeners();
        loadAllAdopters();
    }

    private void attachListeners() {
        topBar.getAddButton().addActionListener(this::handleAdd);
        topBar.getEditButton().addActionListener(this::handleEdit);
        topBar.getDeleteButton().addActionListener(this::handleDelete);
        topBar.getSearchButton().addActionListener(this::handleSearchById);
    }

    private boolean validateAdopter(Adopter adopter) {
        StringBuilder errorMessage = new StringBuilder();
        boolean isValid = true;

        if (adopter.getFirst_name() == null || adopter.getFirst_name().isEmpty()) {
            errorMessage.append("First name cannot be empty.\n");
            isValid = false;
        }
        else if (!Character.isUpperCase(adopter.getFirst_name().charAt(0))) {
            errorMessage.append("First name must start with a capital letter.\n");
            isValid = false;
        }
        else if (!NAME_PATTERN.matcher(adopter.getFirst_name()).matches()) {
            errorMessage.append("First name must contain letters only.\n");
            isValid = false;
        }

        if (adopter.getLast_name() == null || adopter.getLast_name().isEmpty()) {
            errorMessage.append("Last name cannot be empty.\n");
            isValid = false;
        }
        else if (!Character.isUpperCase(adopter.getLast_name().charAt(0))) {
            errorMessage.append("Last name must start with a capital letter.\n");
            isValid = false;
        }
        else if (!NAME_PATTERN.matcher(adopter.getLast_name()).matches()) {
            errorMessage.append("Last name must contain letters only.\n");
            isValid = false;
        }

        if (!EMAIL_PATTERN.matcher(adopter.getEmail()).matches()) {
            errorMessage.append("Please enter a valid email address.\n");
            isValid = false;
        }

        if (!PHONE_PATTERN.matcher(adopter.getPhone_number()).matches()) {
            errorMessage.append("Phone number must be exactly 10 digits.\n");
            isValid = false;
        }

        if (!isValid) {
            JOptionPane.showMessageDialog(null, errorMessage.toString(),
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        }

        return isValid;
    }

    private void handleAdd(ActionEvent e) {
        new AdopterFormDialog(view, "Add New Adopter", null, adopter -> {
            if (!validateAdopter(adopter)) {
                return;
            }
            adopterDao.addAdopter(adopter);
            loadAllAdopters();
            JOptionPane.showMessageDialog(view, "Adopter added successfully.");
        }).setVisible(true);
    }

    private void handleEdit(ActionEvent e) {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select an adopter to edit.");
            return;
        }

        int adopterId = (int) table.getTableModel().getValueAt(selectedRow, 0);
        Adopter adopter = adopterDao.getAdopterById(adopterId);

        if (adopter == null) {
            JOptionPane.showMessageDialog(view, "Adopter not found.");
            return;
        }

        new AdopterFormDialog(view, "Edit Adopter", adopter, updatedAdopter -> {
            if (!validateAdopter(updatedAdopter)) {
                return;
            }
            adopterDao.updateAdopter(updatedAdopter);
            loadAllAdopters();
            JOptionPane.showMessageDialog(view, "Adopter updated successfully.");
        }).setVisible(true);
    }

    private void handleDelete(ActionEvent e) {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select an adopter to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this adopter?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int adopterId = (int) table.getTableModel().getValueAt(selectedRow, 0);
            adopterDao.deleteAdopter(adopterId);
            loadAllAdopters();
            JOptionPane.showMessageDialog(view, "Adopter deleted successfully.");
        }
    }

    private void handleSearchById(ActionEvent e) {
        String idText = topBar.getIdField().getText().trim();
        table.getTableModel().setRowCount(0);

        if (idText.isEmpty()) {
            loadAllAdopters();
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            Adopter adopter = adopterDao.getAdopterById(id);

            if (adopter != null) {
                table.addRow(new Object[]{
                        adopter.getAdopter_id(),
                        adopter.getFirst_name(),
                        adopter.getLast_name(),
                        adopter.getEmail(),
                        adopter.getPhone_number(),
                        adopter.getlocation()
                });
            } else {
                JOptionPane.showMessageDialog(view, "Adopter not found with ID: " + id);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.");
        }
    }

    private void loadAllAdopters() {
        table.getTableModel().setRowCount(0);
        List<Adopter> adopters = adopterDao.getAllAdopters();

        for (Adopter adopter : adopters) {
            table.addRow(new Object[]{
                    adopter.getAdopter_id(),
                    adopter.getFirst_name(),
                    adopter.getLast_name(),
                    adopter.getEmail(),
                    adopter.getPhone_number(),
                    adopter.getlocation()
            });
        }
    }
}