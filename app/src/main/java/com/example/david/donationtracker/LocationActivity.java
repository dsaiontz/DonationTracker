package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    private ArrayList<Location> locations;
    private RecyclerView locationRecyclerView;
    private RecyclerView.Adapter adapter;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainPage();
            }
        });

        // reads in location data
        locations = readLocationData();

        // grabs username
        Intent grabbedIntent = getIntent();
        username = grabbedIntent.getExtras().getString("username");

        // configures the recycler view
        adapter = new LocationAdapter(locations, null, username);
        locationRecyclerView = findViewById(R.id.locationRecyclerView);
        locationRecyclerView.setHasFixedSize(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationRecyclerView.setAdapter(adapter);
    }

    public void backToMainPage() {
        Intent backToMain = new Intent(LocationActivity.this, MainPage.class);
        backToMain.putExtra("username", username);
        startActivity(backToMain);
        finish();
    }

    private ArrayList<Location> readLocationData() {
        ArrayList<Location> locations = new ArrayList<>();
        try {
            Context context = locationRecyclerView.getContext();
            InputStream stream = context.getAssets().open("LocationData.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String space = " ";
            String comma = ", ";
            String line;
            while ((line = br.readLine())!= null) {
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
            br.close();
        } catch (Exception e) {
            Log.w("Location Data", "Reading Location Data crashed" + "\n" + e.getMessage());
        }
        // corrects for reading in the column headers
        locations.remove(0);
        return locations;
    }
}
