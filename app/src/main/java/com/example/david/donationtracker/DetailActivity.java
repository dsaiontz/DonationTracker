package com.example.david.donationtracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private String username;
    private String locationName;
    private RecyclerView.Adapter adapter;
    private RecyclerView locationRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //user and location are static variables that represent the current user and current location being used
        final User user = Credentials.getCurrentUser();
        final Location location = Locations.getCurrentLocation();


        //configures the recycler view that holds the location detail activity as well as donations at that location
        adapter = new DonationAdapter(Donations.getDonations(location), null, username);
        locationRecyclerView = findViewById(R.id.donationsRecyclerView);
        locationRecyclerView.setHasFixedSize(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationRecyclerView.setAdapter(adapter);


        //Sets text for detailed information of location
        TextView textView = (TextView) findViewById(R.id.detailText);
        if (location != null) {
            String detailText = "Name: " + location.getName();
            detailText = detailText + "\nType: " + location.getType()
                    + "\nLongitude: " + location.getLongitude() + "\nLatitude: "
                    + location.getLatitude() + "\nAddress: " + location.getAddress()
                    + "\nPhone Number: " + location.getPhoneNumber() + "\n";
            textView.setText(detailText);
        }
        textView.setTextColor(Color.parseColor("#FFFFFF"));


        //Button for adding donation, displays toast if just a USER
        Button donationButton = findViewById(R.id.donationButton);
        donationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((user.getUserType() == UserType.EMPLOYEE) ||
                        (user.getUserType() == UserType.ADMIN) ||
                        (user.getUserType() == UserType.MANAGER)) {
                    Intent intent = new Intent(DetailActivity.this, DonationActivity.class);
                    final LocalDateTime time = LocalDateTime.now();
                    intent.putExtra("time", time);
                    startActivity(intent);
                    finish();
                } else {
                    String text = "You don't have permission to access this.";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


        //Back button returns to locationactivity
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLocationActivity = new Intent(DetailActivity.this, LocationActivity.class);
                startActivity(toLocationActivity);
                finish();
            }
        });
    }

}
