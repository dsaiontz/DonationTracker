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



        TextView showDonations = (TextView) findViewById(R.id.showDonations);
        showDonations.append(Donations.getDonations().keySet().toString());
    }

    private void logout() {
        startActivity(new Intent(MainPage.this, LoginActivity.class));
        finish();
    }

    private void toLocation() {
        Intent grabbedIntent = getIntent();
        String username = grabbedIntent.getExtras().getString("username");
        if (Credentials.containsKey(username)) {
            user = Credentials.get(username);
        }
        Intent intent = new Intent(MainPage.this, LocationActivity.class);
        intent.putExtra("username", username);

        Log.e("","after intent" + username);
        Log.e("","after intent" + getIntent().getStringExtra("username"));

        startActivity(intent);
        finish();
    }
}
