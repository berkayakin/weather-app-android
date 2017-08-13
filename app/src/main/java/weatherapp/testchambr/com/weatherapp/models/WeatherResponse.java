package weatherapp.testchambr.com.weatherapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse extends BaseResponse implements BaseWeatherResponse {

    @SerializedName("coord")
    private WeatherCoordinates Coord;

    @SerializedName("weather")
    private List<Weather> Weathers;

    @SerializedName("main")
    private WeatherMain Main;

    @SerializedName("wind")
    private WeatherWind Wind;

    @SerializedName("rain")
    private WeatherRain Rain;

    @SerializedName("clouds")
    private WeatherClouds Clouds;

    @SerializedName("id")
    private String Id;

    @SerializedName("name")
    private String Name;

    public WeatherCoordinates getCoord() {
        return Coord;
    }

    public List<Weather> getWeathers() {
        return Weathers;
    }

    public WeatherMain getMain() {
        return Main;
    }

    public WeatherWind getWind() {
        return Wind;
    }

    public WeatherRain getRain() {
        return Rain;
    }

    public WeatherClouds getClouds() {
        return Clouds;
    }

    public String getId() {
        return Id;
    }

    public String getResponseName() {
        return Name;
    }

    @Override
    public String getName() {
        return getResponseName();
    }

    @Override
    public Double getTemperature() {
        return getMain().getTemperature();
    }

    @Override
    public LatLng getLatLng() {
        return LatLng.make(getCoord().getLatitude(), getCoord().getLongitude());
    }
}