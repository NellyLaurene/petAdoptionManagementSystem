package org.pet_adoption_system.controller;

import org.pet_adoption_system.dao.AdopterDao;
import org.pet_adoption_system.dao.AdoptionDao;
import org.pet_adoption_system.dao.PetDao;
import org.pet_adoption_system.view.dashboard.DashboardView;

public class DashboardController {
    private final DashboardView view;
    private final PetDao petDao;
    private final AdopterDao adopterDao;
    private final AdoptionDao adoptionDao;

    public DashboardController(String staffName) {
        this.petDao = new PetDao();
        this.adopterDao = new AdopterDao();
        this.adoptionDao = new AdoptionDao();
        this.view = new DashboardView(staffName);

        initController();
    }

    private void initController() {
        updateDashboardData();
    }

    public void updateDashboardData() {
        int totalPets = petDao.totalPetsAvailable();
        int availablePets = petDao.allAvailablePets();
        int pendingAdoptions = adoptionDao.pendingAdoption();
        int totalAdopters = adopterDao.totalAdopter();

        view.updateCardData(totalPets, availablePets, pendingAdoptions, totalAdopters);
    }

    public void showDashboard() {
        view.setVisible(true);
    }

    public DashboardView getView() {
        return view;
    }

    public static void main(String[] args) {
        DashboardController controller = new DashboardController("Nelly");
        controller.showDashboard();
    }
}