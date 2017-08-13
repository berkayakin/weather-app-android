package weatherapp.testchambr.com.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class WeatherClouds {

    @SerializedName("all")
    private Double All;

    public Double getAll() {
        return All;
    }
}
