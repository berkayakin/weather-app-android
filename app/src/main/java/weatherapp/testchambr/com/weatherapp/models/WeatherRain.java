package weatherapp.testchambr.com.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class WeatherRain {

    @SerializedName("3h")
    private Double Rain3h;

    public Double getRain3h() {
        return Rain3h;
    }
}
