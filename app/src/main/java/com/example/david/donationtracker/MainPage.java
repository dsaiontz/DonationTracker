package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        Button locationButton = (Button) findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLocation();
            }
        });
        readLocationData();


        TextView showDonations = (TextView) findViewById(R.id.showDonations);
    }

    private void logout() {
        Credentials.setCurrentUser(null);
        startActivity(new Intent(MainPage.this, LoginActivity.class));
        finish();
    }

    private void toLocation() {
        finish();
    }

    private void readLocationData() {
        ArrayList<Location> locations = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("LocationData.csv")));
            String space = " ";
            String comma = ", ";
            String line;
            while ((line = reader.readLine())!= null) {
                String[] words = line.split(",");
                String name = words[1];
                String latitude = words[2];
                String longitude = words[3];
                String streetAddress = words[4];
                String city = words[5];
                String state = words[6];
                String address = streetAddress + space + city + comma + state;
                String type = words[8];
                String phoneNumber = words[9];
                Location location = new Location(name, type, longitude, latitude, address, phoneNumber);
                locations.add(location);
            }
            reader.close();
        } catch (Exception e) {
            Log.w("Location Data", "Reading Location Data crashed" + "\n" + e.getMessage());
        }
        // corrects for reading in the column headers
        locations.remove(0);
        Locations.setCsvLocations(locations);
    }
}
