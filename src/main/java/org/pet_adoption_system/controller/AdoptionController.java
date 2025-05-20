package org.pet_adoption_system.controller;

import org.pet_adoption_system.dao.AdoptionDao;
import org.pet_adoption_system.dao.PetDao;
import org.pet_adoption_system.model.Adoption;
import org.pet_adoption_system.model.Pet;
import org.pet_adoption_system.view.adoption.AdoptionButtonsHeader;
import org.pet_adoption_system.view.adoption.AdoptionFormDialog;
import org.pet_adoption_system.view.adoption.AdoptionView;
import org.pet_adoption_system.view.components.TableComponents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AdoptionController {

    private final AdoptionView view;
    private final AdoptionDao adoptionDao;
    private final AdoptionButtonsHeader topBar;
    private final TableComponents table;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final BigDecimal MIN_ADOPTION_FEE = new BigDecimal("100.00");

    public AdoptionController(AdoptionView view) {
        this.view = view;
        this.adoptionDao = new AdoptionDao();
        this.topBar = view.getButtonsHeader();
        this.table = view.getTable();
        attachListeners();
        loadAllAdoptions();
    }

    private void attachListeners() {
        topBar.getAddButton().addActionListener(this::handleAdd);
        topBar.getEditButton().addActionListener(this::handleEdit);
        topBar.getDeleteButton().addActionListener(this::handleDelete);
        topBar.getSearchButton().addActionListener(this::handleSearchById);
    }

    private void handleAdd(ActionEvent e) {
        new AdoptionFormDialog(view, "Add New Adoption", null, adoption -> {
            try {
                if (!validateAdoption(adoption)) {
                    return;
                }

                PetDao petDao = new PetDao();
                List<Pet> pets = petDao.getPetsById(adoption.getPet_id());

                if (pets.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Pet not found with ID: " + adoption.getPet_id(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Pet pet = pets.get(0);

                if ("Adopted".equals(pet.getStatus())) {
                    JOptionPane.showMessageDialog(view,
                            "This pet is already adopted. Please select another pet.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                pet.setStatus("Adopted");
                if (petDao.updatePet(pet) <= 0) {
                    JOptionPane.showMessageDialog(view, "Failed to update pet status.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int result = adoptionDao.addAdoption(adoption);
                if (result > 0) {
                    loadAllAdoptions();
                    JOptionPane.showMessageDialog(view, "Adoption record added successfully.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    pet.setStatus("Available");
                    petDao.updatePet(pet);
                    JOptionPane.showMessageDialog(view, "Failed to add adoption record.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (DateTimeParseException dtpe) {
                JOptionPane.showMessageDialog(view,
                        "Invalid date format. Please use yyyy-MM-dd format for the adoption date.",
                        "Date Format Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }).setVisible(true);
    }

    private boolean validateAdoption(Adoption adoption) {
        StringBuilder errorMessage = new StringBuilder();

        if (adoption.getPet_id() <= 0) {
            errorMessage.append("- Invalid Pet ID\n");
        }

        if (adoption.getAdopter_id() <= 0) {
            errorMessage.append("- Invalid Adopter ID\n");
        }

        if (adoption.getAdoption_date() == null) {
            errorMessage.append("- Adoption Date is required\n");
        } else {
            try {
                String dateStr = adoption.getAdoption_date().format(dateFormatter);
                LocalDate parsedDate = LocalDate.parse(dateStr, dateFormatter);

                if (!parsedDate.equals(adoption.getAdoption_date())) {
                    errorMessage.append("- Invalid date format. Please use yyyy-MM-dd\n");
                }

                // Add check for future dates
                if (adoption.getAdoption_date().isAfter(LocalDate.now())) {
                    errorMessage.append("- Adoption Date cannot be in the future\n");
                }
            } catch (Exception e) {
                errorMessage.append("- Invalid date format. Please use yyyy-MM-dd\n");
            }
        }

        // Rest of the validation remains the same
        if (adoption.getAdoption_fee() == null) {
            errorMessage.append("- Adoption Fee is required\n");
        } else if (adoption.getAdoption_fee().compareTo(BigDecimal.ZERO) < 0) {
            errorMessage.append("- Adoption Fee cannot be negative\n");
        } else if (adoption.getAdoption_fee().compareTo(MIN_ADOPTION_FEE) < 0) {
            errorMessage.append("- Adoption Fee must be at least $100.00\n");
        }

        if (adoption.getStatus() == null || adoption.getStatus().trim().isEmpty()) {
            errorMessage.append("- Status is required\n");
        }

        if (adoption.getStaff_id() <= 0) {
            errorMessage.append("- Invalid Staff ID\n");
        }

        if (errorMessage.length() > 0) {
            JOptionPane.showMessageDialog(view,
                    "Please fix the following issues:\n" + errorMessage.toString(),
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void handleEdit(ActionEvent e) {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select an adoption record to edit.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int adoptionId = (int) table.getTableModel().getValueAt(selectedRow, 0);
        Adoption adoption = adoptionDao.getAdoptionById(adoptionId);

        if (adoption == null) {
            JOptionPane.showMessageDialog(view, "Adoption record not found.",
                    "Not Found", JOptionPane.ERROR_MESSAGE);
            return;
        }

        final int originalPetId = adoption.getPet_id();

        new AdoptionFormDialog(view, "Edit Adoption", adoption, updatedAdoption -> {
            try {
                if (!validateAdoption(updatedAdoption)) {
                    return;
                }

                PetDao petDao = new PetDao();

                if (originalPetId != updatedAdoption.getPet_id()) {
                    List<Pet> originalPets = petDao.getPetsById(originalPetId);
                    if (!originalPets.isEmpty()) {
                        Pet originalPet = originalPets.get(0);
                        originalPet.setStatus("Available");
                        petDao.updatePet(originalPet);
                    }

                    List<Pet> newPets = petDao.getPetsById(updatedAdoption.getPet_id());
                    if (!newPets.isEmpty()) {
                        Pet newPet = newPets.get(0);
                        if ("Adopted".equals(newPet.getStatus())) {
                            JOptionPane.showMessageDialog(view,
                                    "The new pet is already adopted. Please select another pet.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        newPet.setStatus("Adopted");
                        petDao.updatePet(newPet);
                    }
                }

                int result = adoptionDao.updateAdoption(updatedAdoption);
                if (result > 0) {
                    loadAllAdoptions();
                    JOptionPane.showMessageDialog(view, "Adoption record updated successfully.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to update adoption record.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (DateTimeParseException dtpe) {
                JOptionPane.showMessageDialog(view,
                        "Invalid date format. Please use yyyy-MM-dd format for the adoption date.",
                        "Date Format Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }).setVisible(true);
    }

    private void handleDelete(ActionEvent e) {
        int selectedRow = table.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Please select an adoption record to delete.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Are you sure you want to delete this adoption record?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int adoptionId = (int) table.getTableModel().getValueAt(selectedRow, 0);
                Adoption adoption = adoptionDao.getAdoptionById(adoptionId);

                if (adoption != null) {
                    PetDao petDao = new PetDao();
                    List<Pet> pets = petDao.getPetsById(adoption.getPet_id());
                    if (!pets.isEmpty()) {
                        Pet pet = pets.get(0);
                        pet.setStatus("Available");
                        petDao.updatePet(pet);
                    }
                }

                int result = adoptionDao.deleteAdoption(adoptionId);
                if (result > 0) {
                    loadAllAdoptions();
                    JOptionPane.showMessageDialog(view, "Adoption record deleted successfully.",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, "Failed to delete adoption record.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void handleSearchById(ActionEvent e) {
        String idText = topBar.getIdField().getText().trim();
        table.getTableModel().setRowCount(0);

        if (idText.isEmpty()) {
            loadAllAdoptions();
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            Adoption adoption = adoptionDao.getAdoptionById(id);

            if (adoption != null) {
                table.addRow(new Object[]{
                        adoption.getAdoption_id(),
                        adoption.getPet_id(),
                        adoption.getAdopter_id(),
                        formatDate(adoption.getAdoption_date()),
                        adoption.getAdoption_fee(),
                        adoption.getStatus(),
                        adoption.getStaff_id()
                });
            } else {
                JOptionPane.showMessageDialog(view, "Adoption record not found with ID: " + id,
                        "Not Found", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Please enter a valid numeric ID.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAllAdoptions() {
        table.getTableModel().setRowCount(0);
        try {
            List<Adoption> adoptions = adoptionDao.getAllAdoptions();
            if (adoptions.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No adoption records found.",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Adoption adoption : adoptions) {
                    table.addRow(new Object[]{
                            adoption.getAdoption_id(),
                            adoption.getPet_id(),
                            adoption.getAdopter_id(),
                            formatDate(adoption.getAdoption_date()),
                            adoption.getAdoption_fee(),
                            adoption.getStatus(),
                            adoption.getStaff_id()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error loading adoption records: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(dateFormatter);
    }
}