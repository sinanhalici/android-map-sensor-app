package com.example.jiaheng.mapsensor;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private MapView mapView;
    private View mView;
    private Marker destinationMarker;
    private TextView distanceText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_google_map, null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) mView.findViewById(R.id.map);
        distanceText = (TextView) mView.findViewById(R.id.distance);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.getMapAsync(this);
            mapView.onResume();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }


    private static GoogleMapFragment googleMapFragment;

    public static GoogleMapFragment getInstance() {
        if (googleMapFragment == null) {
            googleMapFragment = new GoogleMapFragment();
        }
        return googleMapFragment;
    }

    @Override
    public void onMapReady(GoogleMap map) {

        MapsInitializer.initialize(getContext());
        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        Bundle bundle = getArguments();
        int whichPlace = bundle.getInt("whichPlace", -999);

        Location location = LocationProvider.getInstance(getContext()).getLatestLocation();

        for (Place place : Global.placeList) {
            if (place.getId() == whichPlace) {
                Location temp = new Location("temp");
                temp.setLatitude(place.getLatitude());
                temp.setLongitude(place.getLongitude());

                double distance = location.distanceTo(temp) * 3.280839895;
                distanceText.setText("Distance: " + String.format("%.1f", distance) + "feet");
                if (destinationMarker != null)
                    destinationMarker.remove();

                destinationMarker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getLatitude(), place.getLongitude())).title(place.getName()));
                CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(place.getLatitude(), place.getLongitude())).zoom(16).bearing(0).tilt(45).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        }

    }


}
