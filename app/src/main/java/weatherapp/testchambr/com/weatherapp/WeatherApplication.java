package weatherapp.testchambr.com.weatherapp;

import android.app.Application;
import android.content.Context;

import weatherapp.testchambr.com.weatherapp.dagger.AppComponent;
import weatherapp.testchambr.com.weatherapp.dagger.AppModule;
import weatherapp.testchambr.com.weatherapp.dagger.DaggerAppComponent;

public class WeatherApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = initDagger(this);
    }

    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent initDagger(WeatherApplication application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }

}
