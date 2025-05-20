package org.pet_adoption_system.view.pets;

import org.pet_adoption_system.view.ConstantView;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PetButtonsHeader extends JPanel {

    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;

    private final JTextField idField;

    private final JComboBox<String> genderCombo;
    private final JComboBox<String> sizeCombo;
    private final JComboBox<String> statusCombo;

    private final JButton searchButton;

    public PetButtonsHeader() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(ConstantView.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Buttons
        addButton = createButton("Add", ConstantView.PRIMARY);
        editButton = createButton("Edit", ConstantView.SECONDARY);
        deleteButton = createButton("Delete", ConstantView.ACCENT);

        add(addButton);
        add(editButton);
        add(deleteButton);
        add(Box.createHorizontalStrut(20));

        // Input fields and dropdowns
        idField = createInput("ID");

        genderCombo = createCombo(new String[]{"All Genders", "Male", "Female"});
        sizeCombo = createCombo(new String[]{"All Sizes", "Small", "Medium", "Large"});
        statusCombo = createCombo(new String[]{"All Statuses", "Available", "Pending", "Adopted"});

        add(label("ID:"));
        add(idField);

        add(label("Gender:"));
        add(genderCombo);

        add(label("Size:"));
        add(sizeCombo);

        add(label("Status:"));
        add(statusCombo);

        // Search button
        searchButton = createButton("Search", ConstantView.PRIMARY);
        add(Box.createHorizontalStrut(10));
        add(searchButton);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(ConstantView.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        return button;
    }

    private JTextField createInput(String placeholder) {
        JTextField field = new JTextField(10);
        field.setToolTipText("Search by " + placeholder);
        field.setBackground(ConstantView.WHITE);
        field.setForeground(ConstantView.TEXT);
        return field;
    }

    private JComboBox<String> createCombo(String[] options) {
        JComboBox<String> combo = new JComboBox<>(options);
        combo.setBackground(ConstantView.WHITE);
        combo.setForeground(ConstantView.TEXT);
        return combo;
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(ConstantView.TEXT);
        return label;
    }

    // Public getters
    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTextField getIdField() {
        return idField;
    }

    public JComboBox<String> getGenderCombo() {
        return genderCombo;
    }

    public JComboBox<String> getSizeCombo() {
        return sizeCombo;
    }

    public JComboBox<String> getStatusCombo() {
        return statusCombo;
    }

    // Returns active filters
    public Map<String, String> getFilters() {
        Map<String, String> filters = new HashMap<>();
        filters.put("id", idField.getText().trim());
        filters.put("gender", genderCombo.getSelectedItem().toString());
        filters.put("size", sizeCombo.getSelectedItem().toString());
        filters.put("status", statusCombo.getSelectedItem().toString());
        return filters;
    }

    // Register actions for controller to handle
    public void setAddAction(Runnable action) {
        addButton.addActionListener(e -> action.run());
    }

    public void setEditAction(Runnable action) {
        editButton.addActionListener(e -> action.run());
    }
}
