package weatherapp.testchambr.com.weatherapp.views;

import android.content.Context;

import weatherapp.testchambr.com.weatherapp.models.LatLng;

public interface BaseMapView {
    void setMapEventsListener(MapEventsListener mapEventsListener);
    LatLng getCenter ();
    void setCenter (LatLng latLng);
    void setZoom (int zoomLevel);
    int getZoom ();
    void setMinZoom (int minZoomLevel);
    void setMaxZoom (int maxZoomLevel);
    void clearMarkers ();
    void addMarker (Context context, String title, LatLng latLng, boolean willAnimate);
    void prepareMap();
    void animateTo (LatLng latLng);
    boolean hasMarker();
}
