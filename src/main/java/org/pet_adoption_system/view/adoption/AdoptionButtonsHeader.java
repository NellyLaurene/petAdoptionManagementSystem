package org.pet_adoption_system.view.adoption;

import org.pet_adoption_system.view.ConstantView;

import javax.swing.*;
import java.awt.*;


public class AdoptionButtonsHeader extends JPanel {
    private final JButton addButton;
    private final JButton editButton;
    private final JButton deleteButton;

    private final JTextField idField;
    private final JButton searchButton;

    public AdoptionButtonsHeader() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(ConstantView.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        addButton = createButton("Add", ConstantView.PRIMARY);
        editButton = createButton("Edit", ConstantView.SECONDARY);
        deleteButton = createButton("Delete", ConstantView.ACCENT);

        add(addButton);
        add(editButton);
        add(deleteButton);
        add(Box.createHorizontalStrut(20));

        idField = createInput("ID");
        add(label("ID:"));
        add(idField);

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

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(ConstantView.TEXT);
        return label;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTextField getIdField() {
        return idField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

}
