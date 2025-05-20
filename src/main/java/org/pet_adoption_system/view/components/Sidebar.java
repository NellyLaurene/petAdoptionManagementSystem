package org.pet_adoption_system.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.pet_adoption_system.view.ConstantView.*;

public class Sidebar extends JPanel {
    private final Map<String, JButton> buttons = new HashMap<>();
    private Consumer<String> navigationListener;

    public Sidebar() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(PRIMARY);
        setPreferredSize(new Dimension(200, -1));

        JLabel logo = new JLabel("PetAdopt", SwingConstants.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(logo);

        addSidebarButton("Home", "dashboard");
        addSidebarButton("Pets", "pets");
        addSidebarButton("Adopters", "adopters");
        addSidebarButton("Adoptions", "adoption");
        addSidebarButton("Staff", "staff");

        add(Box.createVerticalGlue());
        addSidebarButton("Logout", "logout");
    }

    private void addSidebarButton(String label, String key) {
        JButton button = createSidebarButton(label);
        button.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.accept(key);
            }
        });
        buttons.put(key, button);
        add(button);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        return button;
    }

    public void setActiveButton(String key) {
        buttons.forEach((k, btn) -> btn.setBackground(PRIMARY));
        if (buttons.containsKey(key)) {
            buttons.get(key).setBackground(SECONDARY);
        }
    }

    public void addNavigationListener(Consumer<String> listener) {
        this.navigationListener = listener;
    }
}
