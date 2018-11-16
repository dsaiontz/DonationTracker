package com.example.david.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {

    private FirebaseUser user;

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

        Button searchButton = (Button) findViewById(R.id.toSearchActivityButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearchPage();
            }
        });

        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMapPage();
            }
        });

        ArrayList<Location> locations = Locations.getAllLocations();

        //Get current user
        Intent currentIntent = getIntent();
        user = currentIntent.getParcelableExtra("currentUser");

        // configures the recycler view
        RecyclerView.Adapter adapter = new LocationAdapter(locations);
        RecyclerView locationRecyclerView = findViewById(R.id.locationRecyclerView);
        locationRecyclerView.setHasFixedSize(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationRecyclerView.setAdapter(adapter);
    }

    private void toSearchPage() {
        Intent toSearch = new Intent(LocationActivity.this, SearchActivity.class);
        toSearch.putExtra("currentUser", user);
        startActivity(toSearch);
        finish();
    }

    //back button returns to the main page
    private void backToMainPage() {
        Intent backToMain = new Intent(LocationActivity.this, MainPage.class);
        backToMain.putExtra("currentUser", user);
        startActivity(backToMain);
        finish();
    }

    private void toMapPage() {
        Intent toMap = new Intent(LocationActivity.this, MapsActivity.class);
        toMap.putExtra("currentUser", user);
        startActivity(toMap);
        finish();
    }
}
