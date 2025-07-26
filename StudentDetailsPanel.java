package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentDetailsPanel extends JPanel {
    private JButton nextButton; // Button to move to next tab

    public StudentDetailsPanel() {
        // Set layout and padding for the main panel
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 250, 255)); // Light blue background
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Outer margin

        // Title label at the top
        JLabel title = new JLabel(" Student Information Entry", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 24));
        add(title, BorderLayout.NORTH); // Add title to top

        // Create form panel with 7 rows and 2 columns
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 12, 12));
        formPanel.setOpaque(false); // Make background transparent to match parent

        // Create text fields for student details
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField branchField = new JTextField();
        JTextField mathField = new JTextField();
        JTextField physicsField = new JTextField();
        JTextField chemistryField = new JTextField();

        // Add label and corresponding field to form
        formPanel.add(new JLabel("🆔 Student ID:")); formPanel.add(idField);
        formPanel.add(new JLabel("📛 Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("🎂 Age:")); formPanel.add(ageField);
        formPanel.add(new JLabel("🏫 Branch:")); formPanel.add(branchField);
        formPanel.add(new JLabel("📐 Math Marks:")); formPanel.add(mathField);
        formPanel.add(new JLabel("🔭 Physics Marks:")); formPanel.add(physicsField);
        formPanel.add(new JLabel("⚗️ Chemistry Marks:")); formPanel.add(chemistryField);

        // Add formPanel to center of main layout
        add(formPanel, BorderLayout.CENTER);

        // Create a panel for buttons (Save & Next)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false); // Match background

        JButton saveButton = new JButton(" Save Student");
        styleButton(saveButton); // Apply custom style

        nextButton = new JButton(" Next to Prediction Panel");
        styleButton(nextButton);

        // Add buttons to buttonPanel
        buttonPanel.add(saveButton);
        buttonPanel.add(nextButton);

        // Add button panel at the bottom
        add(buttonPanel, BorderLayout.SOUTH);

        // Save student info to database on button click
        saveButton.addActionListener(e -> {
            try {
                // Get input values from fields
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String branch = branchField.getText().trim();
                int math = Integer.parseInt(mathField.getText().trim());
                int physics = Integer.parseInt(physicsField.getText().trim());
                int chemistry = Integer.parseInt(chemistryField.getText().trim());

                // Calculate average marks
                double average = (math + physics + chemistry) / 3.0;

                // Connect to the database
                try (Connection conn = DBConnection.getConnection()) {

                    // Insert or update student info
                    String studentQuery = "INSERT INTO students (id, name, age, branch) VALUES (?, ?, ?, ?) " +
                            "ON DUPLICATE KEY UPDATE name = VALUES(name), age = VALUES(age), branch = VALUES(branch)";
                    PreparedStatement studentPs = conn.prepareStatement(studentQuery);
                    studentPs.setInt(1, id);
                    studentPs.setString(2, name);
                    studentPs.setInt(3, age);
                    studentPs.setString(4, branch);
                    studentPs.executeUpdate();

                    // Insert or update marks for 3 subjects
                    String[] subjects = {"Mathematics", "Physics", "Chemistry"};
                    int[] marks = {math, physics, chemistry};

                    for (int i = 0; i < 3; i++) {
                        String query = "REPLACE INTO marks (student_id, subject, marks) VALUES (?, ?, ?)";
                        PreparedStatement marksPs = conn.prepareStatement(query);
                        marksPs.setInt(1, id);
                        marksPs.setString(2, subjects[i]);
                        marksPs.setInt(3, marks[i]);
                        marksPs.executeUpdate();
                    }

                    // Show success dialog with average
                    JOptionPane.showMessageDialog(this,
                            "✅ Student info saved successfully.\n📊 Average Marks: " + String.format("%.2f", average));
                }
            } catch (Exception ex) {
                // Show error dialog if input or DB error
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        });
    }

    // Styling helper for buttons
    private void styleButton(JButton button) {
        button.setBackground(new Color(52, 152, 219)); // Blue background
        button.setForeground(Color.WHITE); // White text
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    // Public getter for navigation to next panel
    public JButton getNextButton() {
        return nextButton;
    }
}
