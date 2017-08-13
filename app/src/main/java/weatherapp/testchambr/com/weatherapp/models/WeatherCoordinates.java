package weatherapp.testchambr.com.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class WeatherCoordinates {

    @SerializedName("lat")
    private Double Latitude;

    @SerializedName("lon")
    private Double Longitude;

    public Double getLatitude() {
        return Latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }
}
