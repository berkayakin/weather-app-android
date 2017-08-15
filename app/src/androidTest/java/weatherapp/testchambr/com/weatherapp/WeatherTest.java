package weatherapp.testchambr.com.weatherapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import weatherapp.testchambr.com.weatherapp.activities.WeatherActivity;
import weatherapp.testchambr.com.weatherapp.api.ApiClient;
import weatherapp.testchambr.com.weatherapp.api.ApiInterface;
import weatherapp.testchambr.com.weatherapp.models.LatLng;
import weatherapp.testchambr.com.weatherapp.models.WeatherResponse;
import weatherapp.testchambr.com.weatherapp.presenters.WeatherActivityPresenter;
import weatherapp.testchambr.com.weatherapp.utils.Constants;


public class WeatherTest {

    @Mock
    private WeatherActivityPresenter weatherActivityPresenter;

    @Mock
    private WeatherActivityPresenter.WeatherActivityPresenterListener weatherActivityPresenterListener;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testWeather () throws Exception {

        weatherActivityPresenter.getWeatherByCoordinates(new LatLng(0.0, 0.0));

        Mockito.verify(weatherActivityPresenter).getWeatherByCoordinates((LatLng) Mockito.any());

    }

    @Test
    public void testWeatherWithoutMockito () throws Exception {

        final Double lat = 0.0;
        Double lon = 5.0;

        Call<WeatherResponse> call = ApiClient.getApiClient().create(ApiInterface.class).getWeatherByCoordinates(lat,lon, Constants.OPENWEATHER_API_KEY);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Assert.assertEquals(lat, response.body().getLatLng().getLatitude());
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {}
        });
    }
}