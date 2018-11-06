package com.example.david.donationtracker;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        double latitude;
        double longitude;

        ArrayList<Location> locations = Locations.getAllLocations();
        for (Location location : locations) {
            latitude = Double.parseDouble(location.getLatitude());
            longitude = Double.parseDouble(location.getLongitude());
            LatLng marker = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(marker).title(location.toString()));
        }
        Location firstLocation = locations.get(0);
        latitude = Double.parseDouble(firstLocation.getLatitude());
        longitude = Double.parseDouble(firstLocation.getLongitude());
        LatLng forCamera = new LatLng(latitude, longitude);
        // don't add this marker to the map, only grabbed like this to move camera which didn't work in the loop...
        mMap.moveCamera(CameraUpdateFactory.newLatLng(forCamera));

    }
}
