package weatherapp.testchambr.com.weatherapp.models;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("id")
    private String Id;

    @SerializedName("main")
    private String Main;

    @SerializedName("description")
    private String Description;

    @SerializedName("icon")
    private String Icon;

    public String getId() {
        return Id;
    }

    public String getMain() {
        return Main;
    }

    public String getDescription() {
        return Description;
    }

    public String getIcon() {
        return Icon;
    }
}
