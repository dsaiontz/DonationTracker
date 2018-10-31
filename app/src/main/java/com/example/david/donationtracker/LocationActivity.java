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
        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchPage();
            }
        });


        // reads in location data
        locations = Locations.getCsvLocations();


        // configures the recycler view
        adapter = new LocationAdapter(locations, null, username);
        locationRecyclerView = findViewById(R.id.locationRecyclerView);
        locationRecyclerView.setHasFixedSize(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationRecyclerView.setAdapter(adapter);
    }

    public void toSearchPage() {
        Intent toSearch = new Intent(LocationActivity.this, SearchActivity.class);
        startActivity(toSearch);
        finish();
    }

    //back button returns to the main page
    public void backToMainPage() {
        Intent backToMain = new Intent(LocationActivity.this, MainPage.class);
        startActivity(backToMain);
        finish();
    }
}
