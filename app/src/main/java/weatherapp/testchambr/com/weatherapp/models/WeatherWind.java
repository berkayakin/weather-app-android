package weatherapp.testchambr.com.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class WeatherWind {

    @SerializedName("speed")
    private Double Speed;

    @SerializedName("deg")
    private Double Degree;

    public Double getSpeed() {
        return Speed;
    }

    public Double getDegree() {
        return Degree;
    }
}
