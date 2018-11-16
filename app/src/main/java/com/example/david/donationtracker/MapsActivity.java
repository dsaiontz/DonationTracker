package com.example.david.donationtracker;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Objects;

/**
 * maps activity class that shows the google map
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private float averageLat;
    private float averageLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        FragmentManager manager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) manager
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        double latitude;
        double longitude;

        List<Location> locations = Locations.getAllLocations();
        for (Location location : locations) {
            latitude = Double.parseDouble(location.getLatitude());
            longitude = Double.parseDouble(location.getLongitude());
            LatLng marker = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(marker).title(location.getName()
                    + ", " + location.getPhoneNumber()));
            averageLat += latitude;
            averageLong += longitude;
        }
        averageLat /= locations.size();
        averageLong /= locations.size();
        LatLng forCamera = new LatLng(averageLat, averageLong);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(forCamera, 10f));

    }
}
