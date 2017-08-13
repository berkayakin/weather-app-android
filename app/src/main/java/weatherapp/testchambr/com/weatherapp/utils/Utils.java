package weatherapp.testchambr.com.weatherapp.utils;

import android.location.Location;

import weatherapp.testchambr.com.weatherapp.models.LatLng;

public class Utils {

    public static int kelvinToCelsius(Double tempKelvin)
    {
        return (int) Math.round(tempKelvin - 273.15);
    }

    public static String formatLocation(LatLng latLng) {
        StringBuilder builder = new StringBuilder();

        if (latLng.getLatitude() < 0) {
            builder.append("S ");
        } else {
            builder.append("N ");
        }

        String latitudeDegrees = Location.convert(Math.abs(latLng.getLatitude()), Location.FORMAT_SECONDS);
        String[] latitudeSplit = latitudeDegrees.split(":");
        builder.append(latitudeSplit[0]);
        builder.append("°");
        builder.append(latitudeSplit[1]);
        builder.append("'");
        builder.append(latitudeSplit[2]);
        builder.append("\"");

        builder.append(" ");

        if (latLng.getLongitude() < 0) {
            builder.append("W ");
        } else {
            builder.append("E ");
        }

        String longitudeDegrees = Location.convert(Math.abs(latLng.getLongitude()), Location.FORMAT_SECONDS);
        String[] longitudeSplit = longitudeDegrees.split(":");
        builder.append(longitudeSplit[0]);
        builder.append("°");
        builder.append(longitudeSplit[1]);
        builder.append("'");
        builder.append(longitudeSplit[2]);
        builder.append("\"");

        return builder.toString();
    }

}
