package Exercise3;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemperatureDisplay extends JComponent {
    private final int DEFAULT_WIDTH = 800;
    private final int DEFAULT_HEIGHT = 800;
    private TemperatureData temperatureData;
    private boolean useColour;

    public TemperatureDisplay(TemperatureData temperatureData, boolean useColour) {
        this.temperatureData = temperatureData;
        this.useColour = useColour;
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        List<DataPoint> dataPoints = temperatureData.getLondonDataPoints();
        if (dataPoints.isEmpty()) {
            return;
        }

        // Find minimum and maximum latitude, longitude, and temperature values
        double minLatitude = Double.MAX_VALUE;
        double maxLatitude = Double.MIN_VALUE;
        double minLongitude = Double.MAX_VALUE;
        double maxLongitude = Double.MIN_VALUE;
        double minTemperature = Double.MAX_VALUE;
        double maxTemperature = Double.MIN_VALUE;

        for (DataPoint dataPoint : dataPoints) {
            double latitude = dataPoint.getLatitude();
            double longitude = dataPoint.getLongitude();
            double temperature = dataPoint.getTemperature();

            // Update min and max latitude, longitude, and temperature values
            minLatitude = Math.min(minLatitude, latitude);
            maxLatitude = Math.max(maxLatitude, latitude);
            minLongitude = Math.min(minLongitude, longitude);
            maxLongitude = Math.max(maxLongitude, longitude);
            minTemperature = Math.min(minTemperature, temperature);
            maxTemperature = Math.max(maxTemperature, temperature);
        }

        // Calculate scaling factors
        double latScale = (double) getHeight() / (maxLatitude - minLatitude);
        double lonScale = (double) getWidth() / (maxLongitude - minLongitude);

        for (DataPoint dataPoint : dataPoints) {
            double latitude = dataPoint.getLatitude();
            double longitude = dataPoint.getLongitude();
            double temperature = dataPoint.getTemperature();

            // Map latitude and longitude to pixel coordinates
            int x = (int) ((longitude - minLongitude) * lonScale);
            int y = (int) ((maxLatitude - latitude) * latScale);

            // Draw data point at the mapped pixel coordinates
            Color color;
            if (useColour) {
                color = getColorForTemperature(temperature, minTemperature, maxTemperature);
            } else {
                color = getGreyscaleColorForTemperature(temperature, minTemperature, maxTemperature);
            }
            drawDataPoint(g, x, y, color);
        }
    }

    private Color getColorForTemperature(double temperature, double minTemperature, double maxTemperature) {
        double temperatureRange = maxTemperature - minTemperature;
        double normalizedTemperature = (temperature - minTemperature) / temperatureRange;

        int red, green, blue;

        if (normalizedTemperature <= 0.5) {
            red = (int) (255 * (1 - normalizedTemperature * 2));
            green = (int) (255 * normalizedTemperature * 2);
            blue = 0;
        } else {
            red = 0;
            green = (int) (255 * (1 - (normalizedTemperature - 0.5) * 2));
            blue = (int) (255 * (normalizedTemperature - 0.5) * 2);
        }

        return new Color(red, green, blue);
    }


    private Color getGreyscaleColorForTemperature(double temperature, double minTemperature, double maxTemperature) {
        double temperatureRange = maxTemperature - minTemperature;
        double normalizedTemperature = (temperature - minTemperature) / temperatureRange;

        int greyValue = (int) (255 * normalizedTemperature);
        return new Color(greyValue, greyValue, greyValue);
    }

    private void drawDataPoint(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x, y, 2, 2); // Adjust size according to your preference
    }

}
