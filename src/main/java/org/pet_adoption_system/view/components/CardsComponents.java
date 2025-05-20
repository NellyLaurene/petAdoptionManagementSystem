package org.pet_adoption_system.view.components;

import javax.swing.*;
import java.awt.*;
import static org.pet_adoption_system.view.ConstantView.*;

public class CardsComponents extends JPanel {
    public CardsComponents() {
        setLayout(new GridLayout(2, 2, 20, 20));
        setBackground(BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public void addCard(String value, String title) {
        JPanel card = createCard(value, title);
        add(card);
    }

    private JPanel createCard(String value, String title) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setMaximumSize(new Dimension(250, 150));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
        valueLabel.setForeground(PRIMARY);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        titleLabel.setForeground(TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(valueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        card.add(titleLabel);

        return card;
    }
}