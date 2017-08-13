package weatherapp.testchambr.com.weatherapp.dagger;

import javax.inject.Singleton;

import dagger.Component;
import weatherapp.testchambr.com.weatherapp.activities.WeatherActivity;

@Singleton
@Component(modules = {AppModule.class, PresenterModule.class})
public interface AppComponent {

    void inject(WeatherActivity target);

}