package Exercise3;

public class DataPoint {
    private final double latitude;
    private final double longitude;
    private final double temperature;

    public DataPoint(double latitude, double longitude, double temperature) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature = temperature;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getTemperature() {
        return temperature;
    }
}
