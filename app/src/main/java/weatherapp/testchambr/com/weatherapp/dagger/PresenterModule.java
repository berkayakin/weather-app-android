package weatherapp.testchambr.com.weatherapp.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import weatherapp.testchambr.com.weatherapp.presenters.WeatherActivityPresenter;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    public WeatherActivityPresenter provideWeatherActivityPresenter () {
        return new WeatherActivityPresenter();
    }

}