/*package ui;

import predictor.Predictor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PredictionPanel extends JPanel {
    private JButton nextButton;

    public PredictionPanel() {

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(255, 250, 240));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel(" Student Performance Predictor", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 26));
        title.setForeground(new Color(44, 62, 80));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 250, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel workingDaysLabel = new JLabel("Working Days (Excluding Weekends) in 6 months:");
        JTextField workingDaysField = new JTextField("132", 15);
        styleField(workingDaysField);

        JLabel holidaysLabel = new JLabel("Total Holidays (Including Weekends) in 6 months:");
        JTextField holidaysField = new JTextField(15);
        styleField(holidaysField);

        JLabel presentLabel = new JLabel("Days Present:");
        JTextField presentField = new JTextField(15);
        styleField(presentField);

        JLabel absentLabel = new JLabel("Days Absent:");
        JTextField absentField = new JTextField(15);
        styleField(absentField);

        JLabel marksLabel = new JLabel("Average Marks:");
        JTextField marksField = new JTextField(15);
        styleField(marksField);

        JLabel attendanceLabel = new JLabel("Attendance: --", SwingConstants.CENTER);
        attendanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        attendanceLabel.setForeground(new Color(52, 73, 94));

        JButton predictButton = new JButton(" Predict Result");
        styleButton(predictButton);

        JLabel resultLabel = new JLabel("Prediction: --", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultLabel.setForeground(new Color(39, 174, 96));

        nextButton = new JButton(" Next to Chart Panel");
        styleButton(nextButton);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(workingDaysLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(workingDaysField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(holidaysLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(holidaysField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(absentLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(absentField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(presentLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(presentField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(marksLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(marksField, gbc);

        gbc.gridwidth = 2; gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(predictButton, gbc);

        gbc.gridy = 6;
        formPanel.add(attendanceLabel, gbc);

        gbc.gridy = 7;
        formPanel.add(resultLabel, gbc);

        gbc.gridy = 8;
        formPanel.add(nextButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        absentField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    int workingDays = Integer.parseInt(workingDaysField.getText().trim());
                    int holidays = Integer.parseInt(holidaysField.getText().trim());
                    int absent = Integer.parseInt(absentField.getText().trim());

                    int actualDays = workingDays - holidays;
                    int present = Math.max(0, actualDays - absent);
                    presentField.setText(String.valueOf(present));
                } catch (NumberFormatException ignored) {}
            }
        });

        predictButton.addActionListener(e -> {
            try {
                int workingDays = Integer.parseInt(workingDaysField.getText().trim());
                int holidays = Integer.parseInt(holidaysField.getText().trim());
                int present = Integer.parseInt(presentField.getText().trim());
                int absent = Integer.parseInt(absentField.getText().trim());
                double avgMarks = Double.parseDouble(marksField.getText().trim());

                int actualWorkingDays = workingDays - holidays;
                int totalRecorded = present + absent;
                if (totalRecorded > actualWorkingDays) actualWorkingDays = totalRecorded;

                double attendancePercentage = ((double) present / actualWorkingDays) * 100;

                attendanceLabel.setText(String.format("Attendance: %.2f%%", attendancePercentage));

                boolean willPass = Predictor.willPass(attendancePercentage, avgMarks);
                resultLabel.setText("Prediction: " + (willPass ? "🎉 Pass" : "⚠️ Fail"));
                resultLabel.setForeground(willPass ? new Color(46, 204, 113) : new Color(231, 76, 60));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❗ Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBackground(new Color(255, 255, 255));
        field.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        field.setMargin(new Insets(8, 12, 8, 12));
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public static JPanel createDeveloperTab() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(245, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel heading = new JLabel(" Developer Info", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(heading, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(245, 245, 255));

        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.setBackground(new Color(245, 245, 255));

        JLabel photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(140, 160));
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Load placeholder image
        try {
            ImageIcon defaultIcon = new ImageIcon(ClassLoader.getSystemResource("icon/photo.png")); // Place the placeholder image in an 'assets' folder
            Image img = defaultIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            photoLabel.setText("Your Photo");
        }


        photoPanel.add(photoLabel, BorderLayout.NORTH);


        JTextArea info = new JTextArea();
        info.setFont(new Font("Monospaced", Font.PLAIN, 16));
        info.setText("Project: Gradelytics - Student Performance Tracker\n" +
                "Developer: Sourav Das\n" +
                "Tools: Java, Swing, MySQL, JFreeChart\n" +
                "IDE: IntelliJ IDEA\n\n" +
                "Academic Insight Visualization System\n" +
                "Version: 1.0.0\n");
        info.setEditable(false);
        info.setBackground(Color.WHITE);
        info.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 255), 1));
        info.setMargin(new Insets(10, 20, 10, 20));

        centerPanel.add(photoPanel, BorderLayout.WEST);
        centerPanel.add(info, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }
}*/
package ui;

import predictor.Predictor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PredictionPanel extends JPanel {
    private JButton nextButton; // Button to navigate to the next tab

    public PredictionPanel() {
        // Set layout and padding for the main panel
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(255, 250, 240)); // Light cream background
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Title label at the top
        JLabel title = new JLabel(" Student Performance Predictor", SwingConstants.CENTER);
        title.setFont(new Font("Verdana", Font.BOLD, 26));
        title.setForeground(new Color(44, 62, 80)); // Dark gray text
        add(title, BorderLayout.NORTH); // Add to the top

        // Create a form panel using GridBagLayout for flexible layout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 250, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Create all labels and fields
        JLabel workingDaysLabel = new JLabel("Working Days:");
        JTextField workingDaysField = new JTextField("132", 15);
        styleField(workingDaysField);

        JLabel holidaysLabel = new JLabel("Total Holidays:");
        JTextField holidaysField = new JTextField(15);
        styleField(holidaysField);

        JLabel presentLabel = new JLabel("Days Present:");
        JTextField presentField = new JTextField(15);
        styleField(presentField);

        JLabel absentLabel = new JLabel("Days Absent:");
        JTextField absentField = new JTextField(15);
        styleField(absentField);

        JLabel marksLabel = new JLabel("Average Marks:");
        JTextField marksField = new JTextField(15);
        styleField(marksField);

        JLabel attendanceLabel = new JLabel("Attendance: --", SwingConstants.CENTER);
        attendanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        attendanceLabel.setForeground(new Color(52, 73, 94));

        JButton predictButton = new JButton(" Predict Result");
        styleButton(predictButton);

        JLabel resultLabel = new JLabel("Prediction: --", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        resultLabel.setForeground(new Color(39, 174, 96));

        nextButton = new JButton(" Next to Chart Panel");
        styleButton(nextButton);

        // Add all components to the formPanel with proper positioning
        // 👉 First row - Label: Working Days
        gbc.gridx = 0;                 // Column 0 (left)
        gbc.gridy = 0;                 // Row 0 (top)
        formPanel.add(workingDaysLabel, gbc);   // Add the label to the panel

        gbc.gridx = 1;                 // Column 1 (right side of the label)
        formPanel.add(workingDaysField, gbc);   // Add the text field next to the label

// 👉 Second row - Label: Holidays
        gbc.gridx = 0; gbc.gridy = 1;           // Move to Row 1, Column 0
        formPanel.add(holidaysLabel, gbc);      // Add the holidays label

        gbc.gridx = 1;                          // Move to Column 1
        formPanel.add(holidaysField, gbc);     // Add the holidays field

// 👉 Third row - Label: Days Absent
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(absentLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(absentField, gbc);

// 👉 Fourth row - Label: Days Present
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(presentLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(presentField, gbc);

// 👉 Fifth row - Label: Average Marks
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(marksLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(marksField, gbc);

// 👉 Sixth row - Predict Button (centered across both columns)
        gbc.gridwidth = 2;             // Span both columns
        gbc.gridx = 0; gbc.gridy = 5;  // Row 5
        formPanel.add(predictButton, gbc);

// 👉 Seventh row - Attendance result (centered)
        gbc.gridy = 6;                 // Row 6
        formPanel.add(attendanceLabel, gbc);  // Attendance label with percentage

// 👉 Eighth row - Final prediction result (centered)
        gbc.gridy = 7;
        formPanel.add(resultLabel, gbc);  // Pass/Fail result

// 👉 Ninth row - Next button (centered)
        gbc.gridy = 8;
        formPanel.add(nextButton, gbc);  // Button to move to the chart tab

// ➕ Add the whole form panel to the center area of this PredictionPanel (BorderLayout)
        add(formPanel, BorderLayout.CENTER);


        // Auto-calculate present days when absent is entered
        absentField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    int workingDays = Integer.parseInt(workingDaysField.getText().trim());
                    int holidays = Integer.parseInt(holidaysField.getText().trim());
                    int absent = Integer.parseInt(absentField.getText().trim());
                    int actualDays = workingDays - holidays;
                    int present = Math.max(0, actualDays - absent);
                    presentField.setText(String.valueOf(present));
                } catch (NumberFormatException ignored) {
                }
            }
        });

        // Predict button action
        predictButton.addActionListener(e -> {
            try {
                int workingDays = Integer.parseInt(workingDaysField.getText().trim());
                int holidays = Integer.parseInt(holidaysField.getText().trim());
                int present = Integer.parseInt(presentField.getText().trim());
                int absent = Integer.parseInt(absentField.getText().trim());
                double avgMarks = Double.parseDouble(marksField.getText().trim());

                int actualWorkingDays = workingDays - holidays;
                int totalRecorded = present + absent;
                if (totalRecorded > actualWorkingDays)
                    actualWorkingDays = totalRecorded;

                double attendancePercentage = ((double) present / actualWorkingDays) * 100;
                attendanceLabel.setText(String.format("Attendance: %.2f%%", attendancePercentage));

                boolean willPass = Predictor.willPass(attendancePercentage, avgMarks);
                resultLabel.setText("Prediction: " + (willPass ? "\uD83C\uDF89 Pass" : "\u26A0\uFE0F Fail"));
                resultLabel.setForeground(willPass ? new Color(46, 204, 113) : new Color(231, 76, 60));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❗ Please enter valid numeric values.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Utility method to style input fields
    private void styleField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        field.setMargin(new Insets(8, 12, 8, 12));
    }

    // Utility method to style buttons
    private void styleButton(JButton button) {
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Getter for navigation to be used by MainWindow
    public JButton getNextButton() {
        return nextButton;
    }

    public static JPanel createDeveloperTab() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(245, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel heading = new JLabel(" Developer Info", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(heading, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(245, 245, 255));

        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.setBackground(new Color(245, 245, 255));

        JLabel photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(140, 160));
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Load placeholder image
        try {
            ImageIcon defaultIcon = new ImageIcon(ClassLoader.getSystemResource("icon/photo.png")); // Place the placeholder image in an 'assets' folder
            Image img = defaultIcon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            photoLabel.setText("Your Photo");
        }


        photoPanel.add(photoLabel, BorderLayout.NORTH);


        JTextArea info = new JTextArea();
        info.setFont(new Font("Monospaced", Font.PLAIN, 16));
        info.setText("Project: Gradelytics - Student Performance Tracker\n" +
                "Developer: Sourav Das\n" +
                "Tools: Java, Swing, MySQL, JFreeChart\n" +
                "IDE: IntelliJ IDEA\n\n" +
                "Academic Insight Visualization System\n" +
                "Version: 1.0.0\n");
        info.setEditable(false);
        info.setBackground(Color.WHITE);
        info.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 255), 1));
        info.setMargin(new Insets(10, 20, 10, 20));

        centerPanel.add(photoPanel, BorderLayout.WEST);
        centerPanel.add(info, BorderLayout.CENTER);

        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }
}