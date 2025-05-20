package org.pet_adoption_system.controller;

import org.pet_adoption_system.dao.PetDao;
import org.pet_adoption_system.model.Pet;
import org.pet_adoption_system.view.components.TableComponents;
import org.pet_adoption_system.view.pets.PetButtonsHeader;
import org.pet_adoption_system.view.pets.PetFormDialog;
import org.pet_adoption_system.view.pets.PetView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PetController {

    private final PetView view;
    private final PetDao petDao;
    private final PetButtonsHeader topBar;
    private final TableComponents table;
    private final Pattern letterOnlyPattern = Pattern.compile("^[a-zA-Z\\s]+$");

    public PetController(PetView view) {
        this.view = view;
        this.petDao = new PetDao();
        this.topBar = view.getTopButtonscomponents();
        this.table = view.getTableComponent();
        attachListeners();
        loadAllPets();
    }

    private void attachListeners() {
        topBar.getAddButton().addActionListener(this::handleAdd);
        topBar.getEditButton().addActionListener(this::handleEdit);
        topBar.getDeleteButton().addActionListener(this::handleDelete);
        topBar.getSearchButton().addActionListener(this::handleSearch);
    }

    private void handleAdd(ActionEvent e) {
        new PetFormDialog(view, "Add New Pet", null, pet -> {
            if (pet.getAge() <= 0) {
                JOptionPane.showMessageDialog(view, "Age must be a positive number.");
                return;
            }

            if (!isValidSpecies(pet.getSpecies())) {
                JOptionPane.showMessageDialog(view, "Species must contain letters only.");
                return;
            }

            petDao.addPet(pet);
            loadAllPets();
            JOptionPane.showMessageDialog(view, "Pet added successfully.");
        }).setVisible(true);
    }

    private void handleEdit(ActionEvent e) {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a pet to edit.");
            return;
        }

        int petId = (int) table.getTableModel().getValueAt(selectedRow, 0);
        Pet pet = petDao.getAllPets().stream()
                .filter(p -> p.getPet_id() == petId)
                .findFirst()
                .orElse(null);

        if (pet == null) return;

        new PetFormDialog(view, "Edit Pet", pet, updatedPet -> {
            if (updatedPet.getAge() <= 0) {
                JOptionPane.showMessageDialog(view, "Age must be a positive number.");
                return;
            }

            if (!isValidSpecies(updatedPet.getSpecies())) {
                JOptionPane.showMessageDialog(view, "Species must contain letters only.");
                return;
            }

            petDao.updatePet(updatedPet);
            loadAllPets();
            JOptionPane.showMessageDialog(view, "Pet updated successfully.");
        }).setVisible(true);
    }

    private boolean isValidSpecies(String species) {
        if (species == null || species.trim().isEmpty()) {
            return false;
        }
        return letterOnlyPattern.matcher(species).matches();
    }

    private void handleDelete(ActionEvent e) {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select a pet to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this pet?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int petId = (int) table.getTableModel().getValueAt(selectedRow, 0);
            petDao.deletePet(petId);
            loadAllPets();
            JOptionPane.showMessageDialog(view, "Pet deleted successfully.");
        }
    }

    private void handleSearch(ActionEvent e) {
        Map<String, String> filters = topBar.getFilters();

        String idText = filters.get("id").trim();
        String gender = filters.get("gender");
        String size = filters.get("size");
        String status = filters.get("status");

        table.getTableModel().setRowCount(0);
        List<Pet> filteredPets = new ArrayList<>();

        try {
            if (!idText.isEmpty()) {
                int id = Integer.parseInt(idText);
                filteredPets = petDao.getPetsById(id);
            } else if (!"All Genders".equals(gender)) {
                filteredPets = petDao.getPetsByGender(gender);
            } else if (!"All Sizes".equals(size)) {
                filteredPets = petDao.getPetsBySize(size);
            } else if (!"All Statuses".equals(status)) {
                filteredPets = petDao.getPetsByStatus(status);
            } else {
                filteredPets = petDao.getAllPets();
            }

            for (Pet pet : filteredPets) {
                table.addRow(new Object[]{
                        pet.getPet_id(), pet.getSpecies(), pet.getAge(), pet.getGender(), pet.getSize(), pet.getStatus()
                });
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid ID format. Please enter a valid number.");
        }
    }

    private void loadAllPets() {
        table.getTableModel().setRowCount(0);
        List<Pet> pets = petDao.getAllPets();
        for (Pet pet : pets) {
            table.addRow(new Object[]{
                    pet.getPet_id(), pet.getSpecies(), pet.getAge(), pet.getGender(), pet.getSize(), pet.getStatus()
            });
        }
    }
}