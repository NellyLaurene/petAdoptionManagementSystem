package org.pet_adoption_system.view.login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static org.pet_adoption_system.view.ConstantView.WHITE;
import static org.pet_adoption_system.view.ConstantView.PRIMARY;


public class LoginView extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private final String IMAGE_PATH = "src/main/resources/images/petAdopt.jpg";

    public interface LoginEventHandler {
        void onLoginAttempt(String email, String password);
    }

    private LoginEventHandler loginEventHandler;

    public LoginView() {
        initializeUI();
    }

    public void setLoginEventHandler(LoginEventHandler handler) {
        this.loginEventHandler = handler;
    }

    private void initializeUI() {
        setTitle("Login");
        setSize(1024, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(WHITE);

        JPanel imagePanel = createImagePanel();
        mainPanel.add(imagePanel);

        JPanel loginPanel = createLoginPanel();
        mainPanel.add(loginPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createImagePanel() {
        JPanel imagePanel = new JPanel(new BorderLayout()) {
            private final Image img = new ImageIcon(IMAGE_PATH).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };

        imagePanel.setBackground(Color.BLACK);
        return imagePanel;
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(WHITE);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(150, 30, 40, 150));

        JLabel welcomeLabel = new JLabel("Hello!");
        welcomeLabel.setForeground(PRIMARY);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel loginInstructionLabel = new JLabel("Login your account");
        loginInstructionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        loginInstructionLabel.setForeground(PRIMARY);
        loginInstructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginInstructionLabel.setBorder(new EmptyBorder(30, 0, 5, 0));

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        emailLabel.setForeground(PRIMARY);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        emailField = new JTextField(20);
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordLabel.setForeground(PRIMARY);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setBorder(new EmptyBorder(15, 0, 0, 0));

        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 2, 1, 1, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);

        loginButton = new RoundedButton("Login");
        loginButton.setBackground(PRIMARY);
        loginButton.setForeground(WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginPanel.add(welcomeLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(loginInstructionLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(emailLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        loginPanel.add(passwordField);

        JPanel forgotPasswordPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        forgotPasswordPanel.setBackground(WHITE);
        forgotPasswordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        forgotPasswordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginPanel.add(forgotPasswordPanel);

        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        loginButton.addActionListener(e -> {
            if (loginEventHandler != null) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                loginEventHandler.onLoginAttempt(email, password);
            }
        });

        return loginPanel;
    }

    class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width - 1, height - 1, 20, 20);
            g2.setColor(getBackground());
            g2.fill(roundedRectangle);

            g2.setColor(getForeground());
            g2.setFont(getFont());
            FontMetrics fontMetrics = g2.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(getText());
            int textHeight = fontMetrics.getHeight();
            int x = (width - textWidth) / 2;
            int y = (height - textHeight) / 2 + fontMetrics.getAscent();
            g2.drawString(getText(), x, y);

            g2.dispose();
        }
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showInfoMessage(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void clearForm() {
        emailField.setText("");
        passwordField.setText("");
    }
}
