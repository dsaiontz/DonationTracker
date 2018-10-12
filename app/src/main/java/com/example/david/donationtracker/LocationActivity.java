package com.example.david.donationtracker;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    private ArrayList<Location> locations;
    private RecyclerView recyclerView;

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
        locations = new ArrayList<>();
        System.out.println("About to read location data");
        readLocationData();
        System.out.println("Finished reading location data");

        recyclerView = findViewById(R.id.listLocationData);
        Location[] locationArr = new Location[locations.size()];
        for (int i = 0; i < locations.size(); i++) {
            locationArr[i] = locations.get(i);
        }
        LocationAdapter adapter = new LocationAdapter(locationArr);
        recyclerView.setAdapter(adapter);
    }

    public void backToMainPage() {
        startActivity(new Intent(LocationActivity.this, MainPage.class));
        finish();
    }

    private void readLocationData() {
        try {
//            File csvFile = new File(Environment.getExternalStorageDirectory() + "/LocationData.csv");
            File csvFile = new File("C:\\Users\\codemettle.LHDLL\\Documents\\GitHub\\DonationTracker\\app\\LocationData.csv");
            System.out.println("got a new file: " + csvFile.toString());
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            String line;
            String space = " ";
            String comma = ", ";
            System.out.println("starting while loop");
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
                System.out.println("finished an iteration of while loop");
            }
            System.out.println("Finished reading file successfully");

        } catch (Exception e) {
            Log.w("Location Data", e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
    }
}
