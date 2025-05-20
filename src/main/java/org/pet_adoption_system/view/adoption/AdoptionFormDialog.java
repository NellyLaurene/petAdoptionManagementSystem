package org.pet_adoption_system.view.adoption;

import org.pet_adoption_system.model.Adoption;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class AdoptionFormDialog extends JDialog {

    private final JTextField petIdField;
    private final JTextField adopterIdField;
    private final JTextField adoptionDateField;
    private final JTextField adoptionFeeField;
    private final JComboBox<String> statusComboBox;
    private final JTextField staffIdField;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final BigDecimal MIN_ADOPTION_FEE = new BigDecimal("100.00");

    public AdoptionFormDialog(Frame parent, String title, Adoption adoption, Consumer<Adoption> onSaveCallback) {
        super(parent, title, true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        petIdField = new JTextField();
        adopterIdField = new JTextField();
        adoptionDateField = new JTextField();
        adoptionFeeField = new JTextField();
        statusComboBox = new JComboBox<>(new String[]{"Pending", "Completed", "Cancelled"});
        staffIdField = new JTextField();

        formPanel.add(new JLabel("Pet ID:"));
        formPanel.add(petIdField);
        formPanel.add(new JLabel("Adopter ID:"));
        formPanel.add(adopterIdField);
        formPanel.add(new JLabel("Adoption Date (YYYY-MM-DD):"));
        formPanel.add(adoptionDateField);
        formPanel.add(new JLabel("Adoption Fee:"));
        formPanel.add(adoptionFeeField);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusComboBox);
        formPanel.add(new JLabel("Staff ID:"));
        formPanel.add(staffIdField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        adoptionDateField.setToolTipText("Format: YYYY-MM-DD (e.g., 2025-04-27)");
        adoptionFeeField.setToolTipText("Must be at least $100.00");

        if (adoption != null) {
            petIdField.setText(String.valueOf(adoption.getPet_id()));
            adopterIdField.setText(String.valueOf(adoption.getAdopter_id()));
            adoptionDateField.setText(adoption.getAdoption_date() != null ? adoption.getAdoption_date().toString() : "");
            adoptionFeeField.setText(adoption.getAdoption_fee() != null ? adoption.getAdoption_fee().toPlainString() : "");
            statusComboBox.setSelectedItem(adoption.getStatus());
            staffIdField.setText(String.valueOf(adoption.getStaff_id()));
        }

        confirmButton.addActionListener(e -> {
            try {
                StringBuilder errorMessage = new StringBuilder();
                boolean isValid = true;

                int petId, adopterId, staffId;
                try {
                    petId = Integer.parseInt(petIdField.getText().trim());
                    if (petId <= 0) {
                        errorMessage.append("- Pet ID must be a positive number.\n");
                        isValid = false;
                    }
                } catch (NumberFormatException ex) {
                    errorMessage.append("- Pet ID must be a valid number.\n");
                    isValid = false;
                    petId = 0;
                }

                try {
                    adopterId = Integer.parseInt(adopterIdField.getText().trim());
                    if (adopterId <= 0) {
                        errorMessage.append("- Adopter ID must be a positive number.\n");
                        isValid = false;
                    }
                } catch (NumberFormatException ex) {
                    errorMessage.append("- Adopter ID must be a valid number.\n");
                    isValid = false;
                    adopterId = 0;
                }

                try {
                    staffId = Integer.parseInt(staffIdField.getText().trim());
                    if (staffId <= 0) {
                        errorMessage.append("- Staff ID must be a positive number.\n");
                        isValid = false;
                    }
                } catch (NumberFormatException ex) {
                    errorMessage.append("- Staff ID must be a valid number.\n");
                    isValid = false;
                    staffId = 0;
                }

                String dateStr = adoptionDateField.getText().trim();
                LocalDate adoptionDate = null;
                if (dateStr.isEmpty()) {
                    errorMessage.append("- Adoption date is required.\n");
                    isValid = false;
                } else if (!DATE_PATTERN.matcher(dateStr).matches()) {
                    errorMessage.append("- Adoption date must be in YYYY-MM-DD format.\n");
                    isValid = false;
                } else {
                    try {
                        adoptionDate = LocalDate.parse(dateStr, dateFormatter);
                    } catch (DateTimeParseException ex) {
                        errorMessage.append("- Invalid date. Please ensure your date is valid and in YYYY-MM-DD format.\n");
                        isValid = false;
                    }
                }

                String feeStr = adoptionFeeField.getText().trim();
                BigDecimal adoptionFee = null;
                if (feeStr.isEmpty()) {
                    errorMessage.append("- Adoption fee is required.\n");
                    isValid = false;
                } else {
                    try {
                        adoptionFee = new BigDecimal(feeStr);
                        if (adoptionFee.compareTo(BigDecimal.ZERO) < 0) {
                            errorMessage.append("- Adoption fee cannot be negative.\n");
                            isValid = false;
                        } else if (adoptionFee.compareTo(MIN_ADOPTION_FEE) < 0) {
                            errorMessage.append("- Adoption fee must be at least $100.00.\n");
                            isValid = false;
                        }
                    } catch (NumberFormatException ex) {
                        errorMessage.append("- Adoption fee must be a valid number.\n");
                        isValid = false;
                    }
                }

                String status = (String) statusComboBox.getSelectedItem();
                if (status == null || status.trim().isEmpty()) {
                    errorMessage.append("- Status is required.\n");
                    isValid = false;
                }

                if (!isValid) {
                    JOptionPane.showMessageDialog(this,
                            errorMessage.toString(),
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Adoption newAdoption = new Adoption();
                if (adoption != null) {
                    newAdoption.setAdoption_id(adoption.getAdoption_id());
                }
                newAdoption.setPet_id(petId);
                newAdoption.setAdopter_id(adopterId);
                newAdoption.setAdoption_date(adoptionDate);
                newAdoption.setAdoption_fee(adoptionFee);
                newAdoption.setStatus(status);
                newAdoption.setStaff_id(staffId);

                onSaveCallback.accept(newAdoption);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "An unexpected error occurred: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }
}