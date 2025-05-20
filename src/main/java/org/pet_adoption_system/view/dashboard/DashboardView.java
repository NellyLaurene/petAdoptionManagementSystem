package org.pet_adoption_system.view.dashboard;

import org.pet_adoption_system.controller.PetController;
import org.pet_adoption_system.dao.AdopterDao;
import org.pet_adoption_system.dao.AdoptionDao;
import org.pet_adoption_system.dao.PetDao;
import org.pet_adoption_system.view.adopter.AdopterView;
import org.pet_adoption_system.view.adoption.AdoptionView;
import org.pet_adoption_system.view.components.CardsComponents;
import org.pet_adoption_system.view.components.HeaderComponent;
import org.pet_adoption_system.view.components.Sidebar;
import org.pet_adoption_system.view.login.LoginView;
import org.pet_adoption_system.view.pets.PetView;

import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {
    private final PetDao petDao;
    private final AdopterDao adopterDao;
    private final AdoptionDao adoptionDao;
    private CardsComponents cardsPanel;

    public DashboardView(String staffName) {
        this.petDao = new PetDao();
        this.adopterDao = new AdopterDao();
        this.adoptionDao = new AdoptionDao();

        setTitle("Pet Adoption System - Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainContainer = new JPanel(new BorderLayout());

        Sidebar sidebar = new Sidebar();
        sidebar.setActiveButton("dashboard");
        sidebar.addNavigationListener(button -> {
            switch (button) {
                case "dashboard" -> {
                    dispose();
                    new DashboardView(staffName).setVisible(true);
                }
                case "pets" -> {
                    dispose();
                    PetView petView = new PetView(staffName);
                    new PetController(petView);
                    petView.setVisible(true);
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
                case "staff" -> {
                    dispose();
                    org.pet_adoption_system.view.staff.StaffView staffView = new org.pet_adoption_system.view.staff.StaffView(staffName);
                    new org.pet_adoption_system.controller.StaffController(staffView);
                    staffView.setVisible(true);
                }
                case "logout" -> {
                    dispose();
                    new LoginView().setVisible(true);
                }
            }
        });
        mainContainer.add(sidebar, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout());

        HeaderComponent header = new HeaderComponent();
        header.setTitle("Dashboard");
        header.setWelcomeMessage(staffName);
        contentPanel.add(header, BorderLayout.NORTH);

        cardsPanel = new CardsComponents();
        contentPanel.add(cardsPanel, BorderLayout.CENTER);

        int totalPets = petDao.totalPetsAvailable();
        int availablePets = petDao.allAvailablePets();
        int pendingAdoptions = adoptionDao.pendingAdoption();
        int totalAdopters = adopterDao.totalAdopter();
        updateCardData(totalPets, availablePets, pendingAdoptions, totalAdopters);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainContainer.add(contentPanel, BorderLayout.CENTER);
        add(mainContainer);
    }

    public void updateCardData(int totalPets, int availablePets, int pendingAdoptions, int totalAdopters) {
        cardsPanel.removeAll();

        cardsPanel.addCard(String.valueOf(totalPets), "Total Pets");
        cardsPanel.addCard(String.valueOf(availablePets), "Available Pets");
        cardsPanel.addCard(String.valueOf(pendingAdoptions), "Pending Adoptions");
        cardsPanel.addCard(String.valueOf(totalAdopters), "Registered Adopters");

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

}
