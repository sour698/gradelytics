// 📦 This class is part of the 'ui' package
package ui;

// 📊 Import the chart panel from the 'charts' package
import charts.ChartGenerator;

// 🧱 Swing GUI components
import javax.swing.*;

// 🎨 AWT for layout, fonts, colors, and dimensions
import java.awt.*;

public class MainWindow extends JFrame {

    // 🏗️ Constructor to build the main application window
    public MainWindow() {

        // 🏷️ Set the title of the application window
        setTitle("Gradelytics - Student Performance Tracker");

        // ❌ Close the program when the user exits the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 📐 Set the size of the main window (width x height)
        setSize(800, 600);

        // 🖼️ Create a stylish header label at the top
        JLabel header = new JLabel(" Gradelytics - Student Performance Tracker", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26)); // Set font
        header.setOpaque(true);                              // Enable background color
        header.setBackground(new Color(0, 123, 167));        // Blue background
        header.setForeground(Color.WHITE);                   // White text color

        // Add a bottom gold border and padding
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 4, 0, new Color(255, 215, 0)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // Set fixed height for the header
        header.setPreferredSize(new Dimension(getWidth(), 60));

        // Change mouse cursor to hand when hovered
        header.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // 🧭 Create the tabbed interface to switch between views
        JTabbedPane tabbedPane = new JTabbedPane();

        // 👤 Create Student Details Panel
        StudentDetailsPanel studentPanel = new StudentDetailsPanel();

        // 🔮 Create Prediction Panel
        PredictionPanel predictionPanel = new PredictionPanel();

        // 📊 Chart Panel + Exit Button (combined in a container)
        JPanel chartPanelContainer = new JPanel(new BorderLayout());
        JPanel chartPanel = ChartGenerator.createChartPanel(); // Get panel from ChartGenerator

        // ❌ Create Exit Button
        JButton exitButton = new JButton(" Exit Application");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBackground(new Color(231, 76, 60)); // Red
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false); // No border highlight when clicked

        // When exit button is clicked, close the application
        exitButton.addActionListener(e -> System.exit(0));

        // 🧩 Panel to hold the exit button at the bottom
        JPanel exitButtonPanel = new JPanel();
        exitButtonPanel.setBackground(Color.WHITE);
        exitButtonPanel.add(exitButton);

        // 🧱 Add the chart and the exit button panel to the container
        chartPanelContainer.add(chartPanel, BorderLayout.CENTER);
        chartPanelContainer.add(exitButtonPanel, BorderLayout.SOUTH);

        // ➕ Add all tabs to the tabbed pane
        tabbedPane.addTab("👤 Student Details", studentPanel);
        tabbedPane.addTab("🔮 Prediction", predictionPanel);
        tabbedPane.addTab("📊 Charts", chartPanelContainer);

        // ⏩ Add navigation logic between tabs using "Next" buttons
        studentPanel.getNextButton().addActionListener(e -> tabbedPane.setSelectedIndex(1)); // To Prediction tab
        predictionPanel.getNextButton().addActionListener(e -> tabbedPane.setSelectedIndex(2)); // To Chart tab

        // 👨‍💻 Add a tab to show Developer Info
        tabbedPane.addTab("👨‍💻 Developer Info", PredictionPanel.createDeveloperTab());

        // 🧱 Set layout of the window and add components
        getContentPane().setLayout(new BorderLayout()); // Use BorderLayout
        add(header, BorderLayout.NORTH);                // Add header at top
        add(tabbedPane, BorderLayout.CENTER);           // Add tabbedPane in the center

        // 🪟 Final UI window settings
        setLocationRelativeTo(null); // Center the window on screen
        setUndecorated(true);        // Remove the default window border/title
        setVisible(true);            // Show the window
    }
}
