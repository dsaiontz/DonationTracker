package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    private ArrayList<Location> locations;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setContentView(R.layout.activity_location);
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainPage();
            }
        });

        locations = new ArrayList<>();

        String username = getIntent().getStringExtra("username");
        Intent intent = new Intent(LocationActivity.this, DetailActivity.class);
        intent.putExtra("username", username);

        Log.e("", "in location activity " + username);

        recyclerView = (RecyclerView) findViewById(R.id.listLocationData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        readLocationData();
        locations.remove(locations.get(0));
        adapter = new LocationAdapter(locations);
        recyclerView.setAdapter(adapter);       //CAUSING CRASHES
    }

    public void backToMainPage() {
        startActivity(new Intent(LocationActivity.this, MainPage.class));
        finish();
    }

    private void readLocationData() {
        try {
            Context context = recyclerView.getContext();
            InputStream stream = context.getAssets().open("LocationData.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String space = " ";
            String comma = ", ";
            String line;
            int lineNumber = 0;
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
                lineNumber++;
                Log.w("lineNumber: ", lineNumber + "");
            }
            br.close();
        } catch (Exception e) {
            Log.w("Location Data", e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }
}
