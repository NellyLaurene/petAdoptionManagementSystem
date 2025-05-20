package org.pet_adoption_system.view.pets;

import org.pet_adoption_system.model.Pet;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class PetFormDialog extends JDialog {
    private final JTextField speciesField = new JTextField(15);
    private final JTextField ageField = new JTextField(5);
    private final JComboBox<String> genderCombo = new JComboBox<>(new String[]{"Male", "Female"});
    private final JComboBox<String> sizeCombo = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
    private final JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Available", "Adopted", "Pending"});
    private final JButton saveButton = new JButton("Save");

    public PetFormDialog(Component parent, String title, Pet existingPet, Consumer<Pet> onSave) {
        super(parent instanceof Window ? (Window) parent : SwingUtilities.getWindowAncestor(parent), title, ModalityType.APPLICATION_MODAL);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.add(new JLabel("Species:"));
        formPanel.add(speciesField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderCombo);
        formPanel.add(new JLabel("Size:"));
        formPanel.add(sizeCombo);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusCombo);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        if (existingPet != null) {
            speciesField.setText(existingPet.getSpecies());
            ageField.setText(String.valueOf(existingPet.getAge()));
            genderCombo.setSelectedItem(existingPet.getGender());
            sizeCombo.setSelectedItem(existingPet.getSize());
            statusCombo.setSelectedItem(existingPet.getStatus());
        }

        saveButton.addActionListener(e -> {
            String species = speciesField.getText().trim();
            String ageText = ageField.getText().trim();
            String gender = (String) genderCombo.getSelectedItem();
            String size = (String) sizeCombo.getSelectedItem();
            String status = (String) statusCombo.getSelectedItem();

            try {
                int age = Integer.parseInt(ageText);

                Pet pet = (existingPet == null) ? new Pet() : existingPet;
                pet.setSpecies(species);
                pet.setAge(age);
                pet.setGender(gender);
                pet.setSize(size);
                pet.setStatus(status);

                onSave.accept(pet);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid age.");
            }
        });
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
