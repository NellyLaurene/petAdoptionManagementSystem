package org.pet_adoption_system.view.adoption;

import org.pet_adoption_system.controller.AdoptionController;
import org.pet_adoption_system.view.components.HeaderComponent;
import org.pet_adoption_system.view.components.Sidebar;
import org.pet_adoption_system.view.components.TableComponents;
import org.pet_adoption_system.view.dashboard.DashboardView;
import org.pet_adoption_system.view.login.LoginView;
import org.pet_adoption_system.view.pets.PetView;
import org.pet_adoption_system.view.staff.StaffView;
import org.pet_adoption_system.view.adopter.AdopterView;

import javax.swing.*;
import java.awt.*;

public class AdoptionView extends JFrame {

    private final TableComponents table;
    private final AdoptionButtonsHeader buttonsHeader;

    public AdoptionView(String staffName) {
        setTitle("Pet Adoption System - Adoptions");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainContainer = new JPanel(new BorderLayout());

        Sidebar sidebar = new Sidebar();
        sidebar.setActiveButton("adoption");
        sidebar.addNavigationListener(button -> {
            switch (button) {
                case "dashboard" -> {
                    dispose();
                    DashboardView dashboardView = new DashboardView(staffName);
                    dashboardView.setVisible(true);
                }
                case "pets" -> {
                    dispose();
                    PetView petView = new PetView(staffName);
                    new org.pet_adoption_system.controller.PetController(petView);
                    petView.setVisible(true);
                }
                case "staff" -> {
                    dispose();
                    StaffView staffView = new StaffView(staffName);
                    new org.pet_adoption_system.controller.StaffController(staffView);
                    staffView.setVisible(true);
                }
                case "adopters" -> {
                    dispose();
                    AdopterView adopterView = new AdopterView(staffName);
                    new org.pet_adoption_system.controller.AdopterController(adopterView);
                    adopterView.setVisible(true);
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

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        HeaderComponent header = new HeaderComponent();
        header.setTitle("Adoption Management");
        header.setDescription("View and manage pet adoptions.");
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, header.getPreferredSize().height));
        contentPanel.add(header);

        buttonsHeader = new AdoptionButtonsHeader();
        buttonsHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(buttonsHeader);

        String[] columns = {"Adoption ID", "Pet ID", "Adopter ID", "Adoption Date", "Adoption Fee", "Status", "Staff ID"};
        table = new TableComponents(columns);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(scrollPane);

        mainContainer.add(contentPanel, BorderLayout.CENTER);
        add(mainContainer);
    }

    public AdoptionButtonsHeader getButtonsHeader() {
        return buttonsHeader;
    }

    public TableComponents getTable() {
        return table;
    }

}
