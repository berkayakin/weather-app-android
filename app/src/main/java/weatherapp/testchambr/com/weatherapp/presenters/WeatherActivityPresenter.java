package weatherapp.testchambr.com.weatherapp.presenters;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import weatherapp.testchambr.com.weatherapp.models.LatLng;
import weatherapp.testchambr.com.weatherapp.models.WeatherResponse;
import weatherapp.testchambr.com.weatherapp.utils.Constants;

public class WeatherActivityPresenter extends BasePresenter {

    private WeatherActivityPresenterListener weatherActivityPresenterListener;
    private Call<WeatherResponse> weatherResponseCall;
    private WeatherResponse lastWeatherResponse;

    private LatLng lastLocation = LatLng.make(0.0,0.0);

    public interface WeatherActivityPresenterListener {
        void weatherReady (WeatherResponse weatherResponse);
        void weatherFailed (String failureMessage);
    }

    public WeatherActivityPresenter () {
        createApiInterface();
    }

    public void setListener (WeatherActivityPresenterListener weatherActivityPresenterListener) {
        this.weatherActivityPresenterListener = weatherActivityPresenterListener;
    }

    public void getWeatherByCoordinates (LatLng latLng) {
        if(weatherResponseCall != null)
            weatherResponseCall.cancel();

        lastLocation = latLng;

        weatherResponseCall = getApiInterface().getWeatherByCoordinates(latLng.getLatitude(), latLng.getLongitude(), Constants.OPENWEATHER_API_KEY);
        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                lastWeatherResponse = response.body();

                if(lastWeatherResponse != null) {

                    if(lastWeatherResponse.getResultCode() == 200) {
                        weatherActivityPresenterListener.weatherReady(lastWeatherResponse);
                    } else {
                        weatherActivityPresenterListener.weatherFailed(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherActivityPresenterListener.weatherFailed(t.getLocalizedMessage());
            }
        });

    }

    public WeatherResponse getLastWeatherResponse() {
        return lastWeatherResponse;
    }

    public LatLng getLastLocation() {
        return lastLocation;
    }

    public void cancelAllRequests () {
        weatherResponseCall.cancel();
    }

    public WeatherResponse getWeatherResponseForTest (WeatherResponse weatherResponse) {
        return weatherResponse;
    }

}