package Exercise3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class TemperatureApp {
    public static void main(String[] args) {
        // Load temperature data from a file
        TemperatureData temperatureData = new TemperatureData("heatmap.csv");

        // Print the number of data points
        System.out.println("Number of data points: " + temperatureData.getLondonDataPoints().size());

        // Create a JFrame to display the temperature data
        JFrame frame = new JFrame("Temperature Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the TemperatureDisplay component
        TemperatureDisplay temperatureDisplay = new TemperatureDisplay(temperatureData, true);

        // Add the display to the frame
        frame.getContentPane().add(temperatureDisplay, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);

        // Print frame size
        System.out.println("Frame size: " + frame.getSize());
    }
}


