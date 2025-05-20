package org.pet_adoption_system.view.adopter;

import org.pet_adoption_system.view.components.HeaderComponent;
import org.pet_adoption_system.view.components.Sidebar;
import org.pet_adoption_system.view.components.TableComponents;
import org.pet_adoption_system.view.dashboard.DashboardView;
import org.pet_adoption_system.view.login.LoginView;
import org.pet_adoption_system.view.pets.PetView;
import org.pet_adoption_system.view.staff.StaffView;

import javax.swing.*;
import java.awt.*;

public class AdopterView extends JFrame {
    private final TableComponents table;
    private final AdopterButtonsHeader buttonsHeader;

    public AdopterView(String staffName) {
        setTitle("Pet Adoption System - Adopters");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainContainer = new JPanel(new BorderLayout());

        Sidebar sidebar = new Sidebar();
        sidebar.setActiveButton("adopters");
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
                case "adoption" -> {
                    dispose();
                    org.pet_adoption_system.view.adoption.AdoptionView adoptionView = new org.pet_adoption_system.view.adoption.AdoptionView(staffName);
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

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // Header
        HeaderComponent header = new HeaderComponent();
        header.setTitle("Adopter Management");
        header.setDescription("View and manage adopter information.");
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, header.getPreferredSize().height));
        contentPanel.add(header);

        // Buttons Header
        buttonsHeader = new AdopterButtonsHeader();
        buttonsHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(buttonsHeader);

        // Table
        String[] columns = {"ID", "First Name", "Last Name", "Email", "Phone Number", "Location"};
        table = new TableComponents(columns);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(scrollPane);

        mainContainer.add(contentPanel, BorderLayout.CENTER);
        add(mainContainer);
    }

    public AdopterButtonsHeader getButtonsHeader() {
        return buttonsHeader;
    }

    public TableComponents getTable() {
        return table;
    }
}
