package com.example.david.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class welcome_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        //login button goes to login activity
        Button LoginButton = (Button) findViewById(R.id.WelcomePageLoginButton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        //register button goes to register activity
        Button RegisterButton = (Button) findViewById(R.id.WelcomePageRegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        Button dbButton = (Button) findViewById(R.id.dbButton);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase fdb= FirebaseDatabase.getInstance();
                DatabaseReference myRef = fdb.getReference();

                User testUser = new User("t@t.com","abc123", UserType.ADMIN);
                myRef.setValue(testUser);
            }
        });

        //reads in the location data from the csv file, sets it to Locations.CsvLocations
        readLocationData();

        addDefaultLogin();
    }

    private void readLocationData() {
        Locations locations = new Locations();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("LocationData.csv")));
            String space = " ";
            String comma = ", ";
            String line;
            reader.readLine();
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
    }

    private void addDefaultLogin() {
        Credentials credentials = new Credentials();

        credentials.add(new User("a@a.com", "abc123", UserType.ADMIN));
    }



    private void logout() {
        startActivity(new Intent(welcome_page.this, LoginActivity.class));
        finish();
    }

    private void register() {
        startActivity(new Intent(welcome_page.this, RegisterActivity.class));
        finish();
    }
}
