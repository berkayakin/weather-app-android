package weatherapp.testchambr.com.weatherapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import retrofit2.Call;
import weatherapp.testchambr.com.weatherapp.api.ApiClient;
import weatherapp.testchambr.com.weatherapp.models.WeatherResponse;
import weatherapp.testchambr.com.weatherapp.presenters.WeatherActivityPresenter;


public class WeatherTest {

    @Mock
    private WeatherActivityPresenter weatherActivityPresenter;

    @Mock
    private Call<WeatherResponse> weatherResponseCall;

    @Mock
    private WeatherResponse weatherResponse;

    @Mock
    ApiClient apiClient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testWeather () throws Exception {
        //Soon
    }
}

