package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.time.LocalDateTime;

import javax.security.auth.login.LoginException;

public class DetailActivity extends AppCompatActivity {

    private String username;
    private String locationName;
    private RecyclerView.Adapter adapter;
    private RecyclerView locationRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent grabbedIntent = getIntent();
        Bundle bundle = grabbedIntent.getExtras();
        username = bundle.getString("username");
        locationName = bundle.getString("locationName");

        Locations locations = new Locations();

        final Location location = Locations.get(locationName);
        final User user = Credentials.get(username);

        String donationsText = "";
        ArrayList<Donation> donationsForLocation = Donations.getDonations(location);

        // configures the recycler view
        adapter = new DonationAdapter(Donations.getDonations(locations.get(locationName)), null, username);
        locationRecyclerView = findViewById(R.id.locationRecyclerView);
        locationRecyclerView.setHasFixedSize(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationRecyclerView.setAdapter(adapter);

        TextView textView = (TextView) findViewById(R.id.detailText);
        if (location != null) {
            String detailText = "Name: " + location.getName();
            Log.i("", "NOT WORKING");
            detailText = detailText + "\nType: " + location.getType()
                    + "\nLongitude: " + location.getLongitude() + "\nLatitude: "
                    + location.getLatitude() + "\nAddress: " + location.getAddress()
                    + "\nPhone Number: " + location.getPhoneNumber() + "\n" + donationsText;
            textView.setText(detailText);
        }

        Button donationButton = (Button) findViewById(R.id.donationButton);
        donationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("","before if" + username);

                if ((user.getUserType() == UserType.EMPLOYEE) ||
                        (user.getUserType() == UserType.ADMIN) ||
                        (user.getUserType() == UserType.MANAGER)) {
                    Log.e("","if runs");
                    Intent intent = new Intent(DetailActivity.this, DonationActivity.class);
                    intent.putExtra("location", location);
                    intent.putExtra("username", username);
                    final LocalDateTime time = LocalDateTime.now();
                    intent.putExtra("time", time);
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("","User type is not employee");
                    //Toaster if no access
                    String text = "You don't have permission to access this.";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent grabbedIntent = getIntent();
                Intent toLocationActivity = new Intent(DetailActivity.this, LocationActivity.class);
                toLocationActivity.putExtra("username", grabbedIntent.getExtras().getString("username"));
                startActivity(toLocationActivity);
                finish();
            }
        });
    }

}
