package weatherapp.testchambr.com.weatherapp.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

import weatherapp.testchambr.com.weatherapp.R;
import weatherapp.testchambr.com.weatherapp.models.LatLng;

public class MyOsmMapView extends MapView implements BaseMapView{

    MapEventsListener mapEventsListener;

    public MyOsmMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyOsmMapView(Context context) {
        super(context);
    }

    @Override
    public void prepareMap() {
        setTilesScaledToDpi(true);
        setMultiTouchControls(true);
        setTileSource(TileSourceFactory.MAPNIK);

        setZoom(9);
        setMinZoom(3);
        setCenter(LatLng.make(50.06, 19.94));
    }

    @Override
    public void addMarker (Context context, String title, LatLng latLng, boolean willAnimate) {
        try {
            ArrayList<OverlayItem> overlayItems = new ArrayList<>();
            OverlayItem overlayItem = new OverlayItem(title, "", new GeoPoint(latLng.getLatitude(), latLng.getLongitude()));
            overlayItems.add(overlayItem);

            Drawable markerDrawable = context.getResources().getDrawable(R.drawable.ic_marker);
            ItemizedIconOverlay itemizedIconOverlay = new ItemizedIconOverlay<>(overlayItems, markerDrawable, null, context);

            clearMarkers();

            getOverlays().add(itemizedIconOverlay);

            if(willAnimate)
                animateTo(LatLng.make(latLng.getLatitude(), latLng.getLongitude()));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public void clearMarkers () {
        if(getOverlays() == null)
            return;

        //I don't use clear function because it also removes the MapEventsReceiver overlay
        if(getOverlays().size() > 1) {
            for(int i = 0; i < getOverlays().size(); i++) {
                //First overlay of the map is MapEventsReceiver which is on position 0 in the list
                //I could simple start for loop from 1 but we may want to change the order of MapEventsReceiver overlay in the future
                if(i == 0)
                    continue;

                getOverlays().remove(i);
            }
        }
    }

    @Override
    public void setMapEventsListener(final MapEventsListener mapEventsListener) {
        this.mapEventsListener = mapEventsListener;
        MapEventsOverlay overlayEvents = new MapEventsOverlay(new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                if(mapEventsListener != null)
                    mapEventsListener.onMapLongPress(LatLng.make(p.getLatitude(), p.getLongitude()));
                return false;
            }
        });
        getOverlays().add(overlayEvents);
    }

    @Override
    public LatLng getCenter() {
        return LatLng.make(getMapCenter().getLatitude(), getMapCenter().getLongitude());
    }

    @Override
    public void setCenter(LatLng latLng) {
        getController().setCenter(new GeoPoint(latLng.getLatitude(), latLng.getLongitude()));
    }

    @Override
    public void setZoom(int zoomLevel) {
        getController().setZoom(zoomLevel);
    }

    @Override
    public int getZoom() {
        return getZoomLevel();
    }

    @Override
    public void setMinZoom(int minZoomLevel) {
        setMinZoomLevel(minZoomLevel);
    }

    @Override
    public void setMaxZoom(int maxZoomLevel) {
        setMaxZoomLevel(maxZoomLevel);
    }

    @Override
    public void animateTo(LatLng latLng) {
        getController().animateTo(new GeoPoint(latLng.getLatitude(), latLng.getLongitude()));
    }

    @Override
    public boolean hasMarker() {
        return getOverlays().size() > 1;
    }

}