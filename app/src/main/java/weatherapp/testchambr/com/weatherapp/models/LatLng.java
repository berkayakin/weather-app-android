package weatherapp.testchambr.com.weatherapp.models;

import android.util.Log;

public class LatLng {

    private Double latitude;
    private Double longitude;

    public LatLng (Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static LatLng make (Double latitude, Double longitude) {
        return new LatLng(latitude, longitude);
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void printLocation () {
        Log.d("Location", "Latitude: " + latitude.toString() + ", Longitude: " + longitude.toString());
    }
}