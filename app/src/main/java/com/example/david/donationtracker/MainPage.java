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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Intent currentIntent = getIntent();

        user = currentIntent.getParcelableExtra("currentUser");

        //logout button should set current user to null, return to login activity
        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        //location button should go to location activity=
        Button locationButton = (Button) findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLocation();
            }
        });
    }

    private void logout() {
        Credentials.setCurrentUser(null);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainPage.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toLocation() {
        Intent intent = new Intent(MainPage.this, LocationActivity.class);
        intent.putExtra("currentUser", user);
        startActivity(intent);
        finish();
    }


}
