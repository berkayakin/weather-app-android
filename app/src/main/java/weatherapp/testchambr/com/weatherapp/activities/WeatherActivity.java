package weatherapp.testchambr.com.weatherapp.activities;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import weatherapp.testchambr.com.weatherapp.R;
import weatherapp.testchambr.com.weatherapp.WeatherApplication;
import weatherapp.testchambr.com.weatherapp.models.LatLng;
import weatherapp.testchambr.com.weatherapp.models.WeatherResponse;
import weatherapp.testchambr.com.weatherapp.presenters.WeatherActivityPresenter;
import weatherapp.testchambr.com.weatherapp.utils.Utils;
import weatherapp.testchambr.com.weatherapp.views.BaseMapView;
import weatherapp.testchambr.com.weatherapp.views.MapEventsListener;

public class WeatherActivity extends AppCompatActivity implements WeatherActivityPresenter.WeatherActivityPresenterListener, MapEventsListener {

    @Inject
    WeatherActivityPresenter weatherActivityPresenter;

    @Inject
    Context appContext;

    @BindView(R.id.mapView) BaseMapView mapView;
    @BindView(R.id.locationNameTV) TextView locationNameTV;
    @BindView(R.id.locationCoordinatesTV) TextView locationCoordinatesTV;
    @BindView(R.id.weatherTemperatureTV) TextView weatherTemperatureTV;
    @BindView(R.id.noLocationSelectedView) RelativeLayout noLocationSelectedView;
    @BindView(R.id.weatherResultView) ScrollView weatherResultView;
    @BindView(R.id.loadingView) RelativeLayout loadingView;
    @BindView(R.id.loadingProgressBar) ContentLoadingProgressBar loadingProgressBar;
    @BindView(R.id.failedView) RelativeLayout failedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((WeatherApplication) getApplication()).getAppComponent().inject(this);

        //I haven't imported the Configuration class because I have already imported another class with name 'Configuration'
        org.osmdroid.config.Configuration.getInstance().load(appContext, PreferenceManager.getDefaultSharedPreferences(appContext));

        setContentView(R.layout.activity_weather);

        ButterKnife.bind(this);

        weatherActivityPresenter.setListener(this);

        //Custom method for default map settings
        mapView.prepareMap();

        //Custom listener which implements my MapEventsListener interface
        mapView.setMapEventsListener(this);

        if(savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
    }

    private void getWeather(LatLng latLng) {

        if(noLocationSelectedView.getVisibility() != View.GONE)
            noLocationSelectedView.setVisibility(View.GONE);

        failedView.setVisibility(View.GONE);

        animateLoading();

        if(weatherActivityPresenter != null)
            weatherActivityPresenter.getWeatherByCoordinates(latLng);

        String markerTitle = appContext.getResources().getString(R.string.location);
        mapView.addMarker(appContext, markerTitle, latLng, true);
    }

    private void animateLoading () {
        //Api call takes less than 1 second generally, so a fade animation makes no sense in this case
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void weatherReady(WeatherResponse weatherResponse) {

        String locationNameString = weatherResponse.getName();
        locationNameTV.setText(locationNameString);

        String coordinatesString = appContext.getResources().getString(R.string.location) + ": ";
        coordinatesString += Utils.formatLocation(LatLng.make(weatherResponse.getLatLng().getLatitude(), weatherResponse.getLatLng().getLongitude()));
        locationCoordinatesTV.setText(coordinatesString);

        String temperatureInCelsiusString = appContext.getResources().getString(R.string.temperature) + ": ";
        temperatureInCelsiusString += Utils.kelvinToCelsius(weatherResponse.getTemperature()) + "°C";
        weatherTemperatureTV.setText(temperatureInCelsiusString);

        loadingView.setVisibility(View.GONE);
        weatherResultView.setVisibility(View.VISIBLE);
    }

    @Override
    public void weatherFailed(@Nullable String failureMessage) {
        if(failureMessage == null)
            failureMessage = appContext.getResources().getString(R.string.msg_call_failed);

        Log.e("Failed", failureMessage);

        weatherResultView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        failedView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tryAgainButton)
    void tryAgain () {

        if(weatherActivityPresenter != null)
            getWeather(LatLng.make(weatherActivityPresenter.getLastLocationLatitude(), weatherActivityPresenter.getLastLocationLongitude()));
    }

    //When we override this method and add android:configChanges="orientation" to the activity on the manifest file,
    //the layout will not be re-created with a bundle when orientation has been changed, but I choose persisting current data from the bundle
    /*@Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);

        //We also could re-load the last data in this way
        if(weatherActivityPresenter.getLastWeatherResponse() != null) {
            weatherReady(weatherActivityPresenter.getLastWeatherResponse());
        }
    }*/

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence("locationName", locationNameTV.getText());
        outState.putCharSequence("locationCoordinates", locationCoordinatesTV.getText());
        outState.putCharSequence("weatherTemperature", weatherTemperatureTV.getText());
        outState.putInt("noLocationSelectedViewVisibility", noLocationSelectedView.getVisibility());
        outState.putInt("weatherResultViewVisibility", weatherResultView.getVisibility());
        outState.putInt("loadingViewVisibility", loadingView.getVisibility());
        outState.putInt("failedViewVisibility", failedView.getVisibility());
        outState.putBoolean("mapHasMarker", mapView.hasMarker());
        outState.putDouble("lastLocationLatitude", weatherActivityPresenter.getLastLocationLatitude());
        outState.putDouble("lastLocationLongitude", weatherActivityPresenter.getLastLocationLongitude());
        outState.putInt("mapZoomLevel", mapView.getZoom());
        outState.putDouble("mapCenterLatitude", mapView.getCenter().getLatitude());
        outState.putDouble("mapCenterLongitude", mapView.getCenter().getLongitude());
    }

    private void restoreState (Bundle savedInstanceState) {
        try {
            Double mapCenterLatitude = savedInstanceState.getDouble("mapCenterLatitude");
            Double mapCenterLongitude = savedInstanceState.getDouble("mapCenterLongitude");
            mapView.setCenter(LatLng.make(mapCenterLatitude, mapCenterLongitude));
            mapView.setZoom(savedInstanceState.getInt("mapZoomLevel"));

            String lastLocationName = savedInstanceState.getString("locationName");

            if(savedInstanceState.getBoolean("mapHasMarker")) {
                Double lastLatitude = savedInstanceState.getDouble("lastLocationLatitude");
                Double lastLongitude = savedInstanceState.getDouble("lastLocationLongitude");
                mapView.addMarker(appContext, lastLocationName, LatLng.make(lastLatitude, lastLongitude), false);
            }
            if(savedInstanceState.getInt("weatherResultViewVisibility") == View.VISIBLE) {
                locationNameTV.setText(lastLocationName);
                locationCoordinatesTV.setText(savedInstanceState.getString("locationCoordinates"));
                weatherTemperatureTV.setText(savedInstanceState.getString("weatherTemperature"));
                weatherResultView.setVisibility(View.VISIBLE);
                noLocationSelectedView.setVisibility(View.GONE);
                failedView.setVisibility(View.GONE);
            } else {
                weatherResultView.setVisibility(View.GONE);
                noLocationSelectedView.setVisibility(savedInstanceState.getInt("noLocationSelectedViewVisibility"));
                failedView.setVisibility(savedInstanceState.getInt("failedViewVisibility"));
            }
            loadingView.setVisibility(savedInstanceState.getInt("loadingViewVisibility"));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public boolean onMapLongPress(LatLng latLng) {
        getWeather(LatLng.make(latLng.getLatitude(), latLng.getLongitude()));
        return false;
    }

    @Override
    public void onDestroy () {
        super.onDestroy();

        if(weatherActivityPresenter != null)
            weatherActivityPresenter.cancelAllRequests();
    }
}