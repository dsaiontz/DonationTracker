package com.example.david.donationtracker;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    private ArrayList<Location> locations;
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
    }

    private void logout() {
        startActivity(new Intent(MainPage.this, LoginActivity.class));
        finish();
    }

    private void readLocationData() {
        try {
            File csvFile = new File(Environment.getExternalStorageDirectory() + "/LocationData.csv");
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            String space = " ";
            String comma = ", ";
            while ((line = br.readLine()) != null) {
                String[] words = line.split(",");
                String name = words[1];
                Float lattitude = Float.parseFloat(words[2]);
                Float longitude = Float.parseFloat(words[3]);
                String streetAddress = words[4];
                String city = words[5];
                String state = words[6];
                String address = streetAddress + space + city + comma + state;
                String type = words[8];
                String phoneNumber = words[9];
                Location locale = new Location(name, type, longitude, lattitude, address, phoneNumber);
                locations.add(locale);
            }

        } catch (Exception e) {
            Log.w("Location Data", e.getMessage());
        }
    }
}
