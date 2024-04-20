package Exercise3;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemperatureDisplay extends JComponent {
    private final int DEFAULT_WIDTH = 600;
    private final int DEFAULT_HEIGHT = 600;
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

        double minTemperature = temperatureData.getMinTemperature();
        double maxTemperature = temperatureData.getMaxTemperature();

        for (DataPoint dataPoint : dataPoints) {
            double temperature = dataPoint.getTemperature();
            Color color;
            if (useColour) {
                color = getColorForTemperature(temperature, minTemperature, maxTemperature);
            } else {
                color = getGreyscaleColorForTemperature(temperature, minTemperature, maxTemperature);
            }
            drawDataPoint(g, dataPoint, color);
        }
    }

    private Color getColorForTemperature(double temperature, double minTemperature, double maxTemperature) {
        double temperatureRange = maxTemperature - minTemperature;
        double normalizedTemperature = (temperature - minTemperature) / temperatureRange;

        int red = (int) (255 * normalizedTemperature);
        int green = (int) (255 * (1 - normalizedTemperature));

        return new Color(red, green, 0);
    }

    private Color getGreyscaleColorForTemperature(double temperature, double minTemperature, double maxTemperature) {
        double temperatureRange = maxTemperature - minTemperature;
        double normalizedTemperature = (temperature - minTemperature) / temperatureRange;

        int greyValue = (int) (255 * normalizedTemperature);
        return new Color(greyValue, greyValue, greyValue);
    }

    private void drawDataPoint(Graphics g, DataPoint dataPoint, Color color) {
        double latitude = dataPoint.getLatitude();
        double longitude = dataPoint.getLongitude();
        int x = (int) (getWidth() * (longitude + 180) / 360);
        int y = (int) (getHeight() * (90 - latitude) / 180);
        g.setColor(color);
        g.fillRect(x, y, 2, 2); // Adjust size according to your preference
    }
}

