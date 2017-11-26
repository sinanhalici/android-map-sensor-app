package com.example.jiaheng.mapsensor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Jiaheng on 4/2/17.
 */

public class LocationProvider {
    private Location latestLocation;
    private LocationManager locationManager;
    private String provider;
    private boolean isLocating = false;
    private Context context;

    public LocationProvider(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        provider = getProvider();
    }

    public boolean isLocating() {
        return isLocating;
    }

    public void start() {
        if (provider.isEmpty()) {
            Toast.makeText(context, "Location failed", Toast.LENGTH_SHORT).show();
            return;
        }
        if (hasPermission()) {
            isLocating = true;
            locationManager.requestLocationUpdates(provider, 5000, 0, locationListener);
        }

        Location location = getLatestLocation();
        for (Place place : Global.placeList) {
            place.setAngle(Calc.calculateAngle(location.getLatitude(), location.getLongitude(), place.getLatitude(), place.getLongitude()));
        }
    }

    public void stop() {
        isLocating = false;
        locationManager.removeUpdates(locationListener);
    }

    public Location getLastKnowLocation() {
        return locationManager.getLastKnownLocation(provider);
    }

    public Location getLatestLocation() {
        if (latestLocation == null) {
            latestLocation = getLastKnowLocation();
        }
        return latestLocation;
    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            latestLocation = location;
            for (Place place : Global.placeList) {
                place.setAngle(Calc.calculateAngle(location.getLatitude(), location.getLongitude(), place.getLatitude(), place.getLongitude()));
            }
        }
    };

    public String getProvider() {
        List<String> providers = locationManager.getProviders(true);
        String locationProvider = "";
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        }
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        }
        return locationProvider;
    }

    public boolean hasPermission() {
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private static LocationProvider locationProvider;

    public static LocationProvider getInstance(Context context) {
        if (locationProvider == null) {
            locationProvider = new LocationProvider(context);
        }
        return locationProvider;
    }

}
