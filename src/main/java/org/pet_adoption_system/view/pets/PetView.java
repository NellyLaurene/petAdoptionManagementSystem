package org.pet_adoption_system.view.pets;

import org.pet_adoption_system.controller.PetController;
import org.pet_adoption_system.dao.PetDao;
import org.pet_adoption_system.model.Pet;
import org.pet_adoption_system.view.adopter.AdopterView;
import org.pet_adoption_system.view.adoption.AdoptionView;
import org.pet_adoption_system.view.components.HeaderComponent;
import org.pet_adoption_system.view.components.Sidebar;
import org.pet_adoption_system.view.components.TableComponents;
import org.pet_adoption_system.view.dashboard.DashboardView;
import org.pet_adoption_system.view.login.LoginView;
import org.pet_adoption_system.view.staff.StaffView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class PetView extends JFrame {
    private final TableComponents tableComponent;
    private final PetButtonsHeader topButtonscomponents;
    private final PetDao petDao = new PetDao();

    public PetView(String staffName) {
        setTitle("Pet Adoption System - Pets");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        topButtonscomponents = new PetButtonsHeader();

        JPanel mainContainer = new JPanel(new BorderLayout());

        Sidebar sidebar = new Sidebar();
        sidebar.setActiveButton("pets");
        sidebar.addNavigationListener(button -> {
            switch (button) {
                case "dashboard" -> {
                    dispose();
                    new DashboardView(staffName).setVisible(true);
                }
                case "adopters" -> {
                    dispose();
                    AdopterView adopterView = new AdopterView(staffName);
                    new org.pet_adoption_system.controller.AdopterController(adopterView);
                    adopterView.setVisible(true);
                }
                case "staff" -> {
                    dispose();
                    StaffView staffView = new StaffView(staffName);
                    new org.pet_adoption_system.controller.StaffController(staffView);
                    staffView.setVisible(true);
                }
                case "adoption" -> {
                    dispose();
                    AdoptionView adoptionView = new AdoptionView(staffName);
                    new org.pet_adoption_system.controller.AdoptionController(adoptionView);
                    adoptionView.setVisible(true);
                }
                case "logout" -> {
                    dispose();
                    new LoginView().setVisible(true);
                }
            }
        });
        mainContainer.add(sidebar, BorderLayout.WEST);

        // Content Panel with vertical layout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Header
        HeaderComponent header = new HeaderComponent();
        header.setTitle("Pet Management");
        header.setDescription("Manage all pets currently registered in the shelter.");
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, header.getPreferredSize().height));
        contentPanel.add(header);

        // Top Buttons Component (Filter Bar)
        topButtonscomponents.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(topButtonscomponents);
        topButtonscomponents.getSearchButton().addActionListener(this::handleSearch);

        // Table
        String[] columns = {"ID", "Species", "Age", "Gender", "Size", "Status"};
        tableComponent = new TableComponents(columns);
        JScrollPane scrollPane = new JScrollPane(tableComponent);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(scrollPane);

        // Initial data
        loadAllPets();

        mainContainer.add(contentPanel, BorderLayout.CENTER);
        add(mainContainer);
    }

    private void handleSearch(ActionEvent e) {
        String idText = topButtonscomponents.getIdField().getText().trim();
        String gender = (String) topButtonscomponents.getGenderCombo().getSelectedItem();
        String size = (String) topButtonscomponents.getSizeCombo().getSelectedItem();
        String status = (String) topButtonscomponents.getStatusCombo().getSelectedItem();

        tableComponent.getTableModel().setRowCount(0);

        if (!idText.isEmpty()) {
            try {
                int id = Integer.parseInt(idText);
                Pet pet = petDao.getAllPets().stream()
                        .filter(p -> p.getPet_id() == id)
                        .findFirst()
                        .orElse(null);
                if (pet != null) {
                    tableComponent.addRow(new Object[]{
                            pet.getPet_id(), pet.getSpecies(), pet.getAge(), pet.getGender(), pet.getSize(), pet.getStatus()
                    });
                }
            } catch (NumberFormatException ignored) {
            }
        } else {
            List<Pet> filteredPets = petDao.getAllPets();

            if (!"All Genders".equals(gender)) {
                filteredPets = filteredPets.stream()
                        .filter(p -> p.getGender().equalsIgnoreCase(gender))
                        .toList();
            }

            if (!"All Sizes".equals(size)) {
                filteredPets = filteredPets.stream()
                        .filter(p -> p.getSize().equalsIgnoreCase(size))
                        .toList();
            }

            if (!"All Statuses".equals(status)) {
                filteredPets = filteredPets.stream()
                        .filter(p -> p.getStatus().equalsIgnoreCase(status))
                        .toList();
            }

            for (Pet pet : filteredPets) {
                tableComponent.addRow(new Object[]{
                        pet.getPet_id(), pet.getSpecies(), pet.getAge(), pet.getGender(), pet.getSize(), pet.getStatus()
                });
            }
        }
    }

    private void loadAllPets() {
        List<Pet> pets = petDao.getAllPets();
        for (Pet pet : pets) {
            tableComponent.addRow(new Object[]{
                    pet.getPet_id(), pet.getSpecies(), pet.getAge(), pet.getGender(), pet.getSize(), pet.getStatus()
            });
        }
    }

    public PetButtonsHeader getTopButtonscomponents() {
        return topButtonscomponents;
    }

    public TableComponents getTableComponent() {
        return tableComponent;
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            PetView view = new PetView("Nelly");
//            new PetController(view);
//            view.setVisible(true);
//        });
//    }
}
