// Package declaration - this means this file is part of the "tracker" package (like a folder for organizing code)
package tracker;

// This imports the SwingUtilities class from Java's Swing library,
// which helps with creating GUI applications
import javax.swing.*;

// This is the main class of the program, named "Main"
public class Main {

    // This is the main method - the starting point of any Java program
    public static void main(String[] args) {

        // This ensures that GUI creation happens on the Event Dispatch Thread (safe for GUI updates)
        SwingUtilities.invokeLater(() ->
                // This creates an object of MainWindow class from the "ui" package
                new ui.MainWindow()
        );
    }
}
