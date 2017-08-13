package weatherapp.testchambr.com.weatherapp.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;
import weatherapp.testchambr.com.weatherapp.models.BaseResponse;
import weatherapp.testchambr.com.weatherapp.models.WeatherResponse;

public interface ApiInterface {

    @GET("weather")
    Call<WeatherResponse> getWeatherByCoordinates(@Query("lat") Double latitude,
                                             @Query("lon") Double longitude,
                                             @Query("appid") String appId);

}