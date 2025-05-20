package org.pet_adoption_system.controller;

import org.pet_adoption_system.dao.StaffDao;
import org.pet_adoption_system.model.Staff;
import org.pet_adoption_system.view.dashboard.DashboardView;
import org.pet_adoption_system.view.login.LoginView;

import javax.swing.*;

public class LoginController {
    private final LoginView loginView;
    private final StaffDao staffDao;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.staffDao = new StaffDao();

        this.loginView.setLoginEventHandler(new LoginView.LoginEventHandler() {
            @Override
            public void onLoginAttempt(String email, String password) {
                handleLogin(email, password);
            }
        });
    }

    private void handleLogin(String email, String password) {
        if (email.isEmpty()) {
            loginView.showErrorMessage("Email is required.");
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            loginView.showErrorMessage("Please enter a valid email.");
            return;
        }

        if (password.isEmpty()) {
            loginView.showErrorMessage("Password is required.");
            return;
        }

        if(!(password.length() > 8)) {
            loginView.showErrorMessage("Password must be more than 8 characters");
            return;
        }

        // Find user by email
        Staff user = staffDao.findByEmail(email);

        // Check if user exists in database
        if (user == null) {
            loginView.showErrorMessage("User not found.");
            return;
        }

        // User exists, verify password
        if (!user.getPassword().equals(password)) {
            loginView.showErrorMessage("Invalid password.");
            return;
        }

        // User exists and password is correct, check if admin
        if (!"Admin".equalsIgnoreCase(user.getPosition())) {
            loginView.showErrorMessage("Access denied. Only admins are allowed to login.");
            return;
        }

        // User is admin with correct credentials
        loginView.showSuccessMessage("Login successful! Welcome, " + user.getFirst_name());
        loginView.dispose();
        openDashboard(user);
    }

    private void openDashboard(Staff admin) {
        SwingUtilities.invokeLater(() -> {
            DashboardView dashboard = new DashboardView(admin.getFirst_name());
            dashboard.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView view = new LoginView();
            new LoginController(view);
            view.setVisible(true);
        });
    }
}