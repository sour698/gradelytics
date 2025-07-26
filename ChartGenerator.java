// Package declaration: this class is in the 'charts' package
package charts;

// Import DB connection helper class from your project
import db.DBConnection;

// JFreeChart imports for creating charts
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

// For saving chart as an image
import javax.imageio.ImageIO;

// Swing components for GUI
import javax.swing.*;

// AWT classes for styling GUI (color, font, size)
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

// File handling
import java.io.File;

// SQL classes to run queries
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Main class to generate the student performance chart
public class ChartGenerator {

    // This method fetches data from the database and prepares it for the chart
    private static DefaultCategoryDataset loadDataset() {
        // Create a dataset object to store data for the bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = DBConnection.getConnection()) {
            // SQL query to get student name, subject, and marks (only for 3 subjects)
            String sql = "SELECT s.name, m.subject, m.marks " +
                    "FROM marks m JOIN students s ON m.student_id = s.id " +
                    "WHERE m.subject IN ('Mathematics', 'Physics', 'Chemistry')";

            // Prepare and execute the query
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Loop through results and add them to the dataset
            while (rs.next()) {
                String studentName = rs.getString("name");
                String subject = rs.getString("subject");
                int marks = rs.getInt("marks");

                // Add value to the dataset (marks, row key = subject, column key = student)
                dataset.addValue(marks, subject, studentName);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print error if something goes wrong
        }

        // Return the dataset to be used in the chart
        return dataset;
    }

    // This method creates and returns a JPanel that contains the full chart UI
    public static JPanel createChartPanel() {
        // Load dataset from database
        DefaultCategoryDataset dataset = loadDataset();

        // Create a chart using the dataset
        JFreeChart chart = createStyledChart(dataset);

        // Create a panel that displays the chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(900, 500)); // Set size
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Add padding

        // Main wrapper panel using BorderLayout
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE); // Set background color
        wrapper.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 255), 2)); // Border

        // Create a label as chart heading/title
        JLabel heading = new JLabel("📈 Performance Chart", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20)); // Font style
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Create a panel to hold control buttons (Refresh & Export)
        JPanel controlPanel = new JPanel();

        // Button to refresh the chart with updated data
        JButton refreshButton = new JButton("🔄 Refresh Chart");

        // Button to export the chart as a PNG image
        JButton exportButton = new JButton("📤 Export to PNG");

        // Action listener for Refresh button
        refreshButton.addActionListener(e -> {
            // Reload data and update chart
            DefaultCategoryDataset newDataset = loadDataset();
            JFreeChart refreshedChart = createStyledChart(newDataset);
            chartPanel.setChart(refreshedChart); // Update chart in panel
        });

        // Action listener for Export button
        exportButton.addActionListener(e -> {
            try {
                // Convert chart to image
                BufferedImage image = chartPanel.getChart().createBufferedImage(1000, 600);

                // File chooser to let user select where to save
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File("student_chart.png")); // Default file name

                // Show save dialog and get result
                int userSelection = fileChooser.showSaveDialog(wrapper);

                // If user chooses a file and clicks Save
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();

                    // Write the image to file as PNG
                    ImageIO.write(image, "png", fileToSave);

                    // Show success message
                    JOptionPane.showMessageDialog(wrapper, "Chart exported as " + fileToSave.getName());
                }
            } catch (Exception ex) {
                ex.printStackTrace(); // Print error if something goes wrong
                JOptionPane.showMessageDialog(wrapper, "❌ Failed to export chart:\n" + ex.getMessage());
            }
        });

        // Add buttons to control panel
        controlPanel.add(refreshButton);
        controlPanel.add(exportButton);

        // Add all components to the wrapper panel
        wrapper.add(heading, BorderLayout.NORTH);      // Title at the top
        wrapper.add(chartPanel, BorderLayout.CENTER);  // Chart in center
        wrapper.add(controlPanel, BorderLayout.SOUTH); // Buttons at the bottom

        // Return the final panel
        return wrapper;
    }

    // This method creates and styles the bar chart
    private static JFreeChart createStyledChart(DefaultCategoryDataset dataset) {
        // Create the chart using factory method
        JFreeChart chart = ChartFactory.createBarChart(
                "Student Performance Overview", // Chart title
                "Student",                      // X-axis label
                "Marks",                        // Y-axis label
                dataset                         // Dataset
        );

        // Set a subtitle using custom font
        chart.setTitle(new TextTitle("Student Marks (Mathematics, Physics, Chemistry)",
                new Font("Arial", Font.BOLD, 18)));

        // Set background color of the whole chart
        chart.setBackgroundPaint(new Color(245, 245, 255));

        // Get the plot (the area inside the axes)
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(230, 230, 250));  // Plot background color
        plot.setRangeGridlinePaint(Color.GRAY);             // Grid line color

        // Customize bar colors for each subject
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, new Color(102, 204, 255)); // Mathematics
        renderer.setSeriesPaint(1, new Color(255, 153, 102)); // Physics
        renderer.setSeriesPaint(2, new Color(153, 255, 153)); // Chemistry

        renderer.setDrawBarOutline(false); // Remove bar borders

        // Return the styled chart
        return chart;
    }
}
