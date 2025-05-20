package org.pet_adoption_system.view.staff;

import org.pet_adoption_system.model.Staff;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class StaffFormDialog extends JDialog {
    private final JTextField firstNameField = new JTextField(15);
    private final JTextField lastNameField = new JTextField(15);
    private final JTextField emailField = new JTextField(15);
    private final JTextField phoneNumberField = new JTextField(15);
    private final JTextField positionField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JButton saveButton = new JButton("Save");

    public StaffFormDialog(Component parent, String title, Staff existingStaff, Consumer<Staff> onSave) {
        super(parent instanceof Window ? (Window) parent : SwingUtilities.getWindowAncestor(parent), title, ModalityType.APPLICATION_MODAL);
        setSize(400, existingStaff == null ? 400 : 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneNumberField);
        formPanel.add(new JLabel("Position:"));
        formPanel.add(positionField);

        // Only show password input if adding a new staff
        boolean isAdding = (existingStaff == null);
        if (isAdding) {
            formPanel.add(new JLabel("Password:"));
            formPanel.add(passwordField);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        if (existingStaff != null) {
            firstNameField.setText(existingStaff.getFirst_name());
            lastNameField.setText(existingStaff.getLast_name());
            emailField.setText(existingStaff.getEmail());
            phoneNumberField.setText(existingStaff.getPhone_number());
            positionField.setText(existingStaff.getPosition());
        }

        saveButton.addActionListener(e -> {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();
            String position = positionField.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
                    || phoneNumber.isEmpty() || position.isEmpty()
                    || (isAdding && new String(passwordField.getPassword()).trim().isEmpty())) {
                JOptionPane.showMessageDialog(this, "All fields must be filled out" +
                        (isAdding ? ", including password." : "."));
                return;
            }

            Staff staff = (existingStaff == null) ? new Staff() : existingStaff;
            staff.setFirst_name(firstName);
            staff.setLast_name(lastName);
            staff.setEmail(email);
            staff.setPhone_number(phoneNumber);
            staff.setPosition(position);

            if (isAdding) {
                staff.setPassword(new String(passwordField.getPassword()).trim());
            }

            onSave.accept(staff);
            dispose();
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
