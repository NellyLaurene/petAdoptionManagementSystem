package org.pet_adoption_system.view.adopter;

import org.pet_adoption_system.model.Adopter;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class AdopterFormDialog extends JDialog {

    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField emailField;
    private final JTextField phoneField;
    private final JTextField locationField;

    public AdopterFormDialog(Frame parent, String title, Adopter adopter, Consumer<Adopter> onSaveCallback) {
        super(parent, title, true);
        setSize(400, 320);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        locationField = new JTextField();

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Location:"));
        formPanel.add(locationField);

        add(formPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Prefill data if editing
        if (adopter != null) {
            firstNameField.setText(adopter.getFirst_name());
            lastNameField.setText(adopter.getLast_name());
            emailField.setText(adopter.getEmail());
            phoneField.setText(adopter.getPhone_number());
            locationField.setText(adopter.getlocation());
        }

        confirmButton.addActionListener(e -> {
            Adopter newAdopter = new Adopter();
            if (adopter != null) {
                newAdopter.setAdopter_id(adopter.getAdopter_id()); // Keep the existing ID
            }
            newAdopter.setFirst_name(firstNameField.getText().trim());
            newAdopter.setLast_name(lastNameField.getText().trim());
            newAdopter.setEmail(emailField.getText().trim());
            newAdopter.setPhone_number(phoneField.getText().trim());
            newAdopter.setlocation(locationField.getText().trim());

            onSaveCallback.accept(newAdopter);
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());
    }
}
