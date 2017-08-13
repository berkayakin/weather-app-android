package weatherapp.testchambr.com.weatherapp.presenters;

import weatherapp.testchambr.com.weatherapp.api.ApiClient;
import weatherapp.testchambr.com.weatherapp.api.ApiInterface;

public class BasePresenter {
    private ApiInterface apiInterface;

    public void createApiInterface() {
        this.apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    public ApiInterface getApiInterface() {
        if(apiInterface == null)
            createApiInterface();

        return apiInterface;
    }
}