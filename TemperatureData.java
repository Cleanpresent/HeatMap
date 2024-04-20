package Exercise3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TemperatureData {
    private List<DataPoint> londonDataPoints;
    private double minTemperature;
    private double maxTemperature;
    private boolean useColour;

    public TemperatureData(String filename) {
        londonDataPoints = new ArrayList<>();
        minTemperature = Double.MAX_VALUE;
        maxTemperature = Double.MIN_VALUE;
        useColour = true; // Default to color visualization
        readTemperatures(filename);
    }

    private void readTemperatures(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read each line containing latitude, longitude, and temperature
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    double latitude = Double.parseDouble(values[0]);
                    double longitude = Double.parseDouble(values[1]);
                    double temperature = Double.parseDouble(values[2]);
                    londonDataPoints.add(new DataPoint(latitude, longitude, temperature));
                    // Update min and max temperatures
                    minTemperature = Math.min(minTemperature, temperature);
                    maxTemperature = Math.max(maxTemperature, temperature);
                } else {
                    System.err.println("Invalid data format: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading temperature data: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public List<DataPoint> getLondonDataPoints() {
        return londonDataPoints;
    }

    public void setUseColour(boolean useColour) {
        this.useColour = useColour;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    // Main method to print a subset of data points
    public static void main(String[] args) {
        TemperatureData temperatureData = new TemperatureData("heatmap.csv");
        List<DataPoint> dataPoints = temperatureData.getLondonDataPoints();
        int count = 0;
        for (DataPoint dataPoint : dataPoints) {
            System.out.println("Latitude: " + dataPoint.getLatitude() + ", Longitude: " + dataPoint.getLongitude() + ", Temperature: " + dataPoint.getTemperature());
            count++;
            if (count >= 100) {
                break; // Print only 100 records
            }
        }
    }
}
