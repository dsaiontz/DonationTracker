package com.example.david.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Location location = getIntent().getParcelableExtra("location");

        TextView text = (TextView) findViewById(R.id.text);
        text.setText("Name: " + location.getName() + "\nType: " + location.getType()
                + "\nLongitude: " + location.getLongitude() + "\nLatitude: "
                + location.getLatitude() + "\nAddress: " + location.getAddress()
                + "\nPhone Number: " + location.getPhoneNumber());
        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailActivity.this, LocationActivity.class));
                finish();
            }
        });
    }

}
