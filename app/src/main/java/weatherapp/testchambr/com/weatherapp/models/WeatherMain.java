package weatherapp.testchambr.com.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class WeatherMain {

    @SerializedName("temp")
    private Double Temperature;

    @SerializedName("humidity")
    private Double Humidity;

    @SerializedName("pressure")
    private Double Pressure;

    @SerializedName("temp_min")
    private Double MinimumTemperature;

    @SerializedName("temp_max")
    private Double MaximumTemperature;

    public Double getTemperature() {
        return Temperature;
    }

    public Double getHumidity() {
        return Humidity;
    }

    public Double getPressure() {
        return Pressure;
    }

    public Double getMinimumTemperature() {
        return MinimumTemperature;
    }

    public Double getMaximumTemperature() {
        return MaximumTemperature;
    }

    public void setTemperature(Double temperature) {
        Temperature = temperature;
    }
}
