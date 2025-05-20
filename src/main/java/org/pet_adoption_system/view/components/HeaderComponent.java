package org.pet_adoption_system.view.components;// Add these imports at the top
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static org.pet_adoption_system.view.ConstantView.SECONDARY;
import static org.pet_adoption_system.view.ConstantView.TEXT;

public class HeaderComponent extends JPanel {
    private final JLabel titleLabel;
    private final JLabel descriptionLabel;

    public HeaderComponent() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(SECONDARY);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TEXT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(titleLabel);

        descriptionLabel = new JLabel();
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(TEXT);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        add(descriptionLabel);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setDescription(String description) {
        descriptionLabel.setText(description);
    }

    public void setWelcomeMessage(String staffName) {
        setDescription("Welcome, " + staffName + "! Here's what's happening at the shelter today.");
    }
}
