package weatherapp.testchambr.com.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("cod")
    private int ResultCode;

    public int getResultCode() {
        return ResultCode;
    }
}