package com.example.david.donationtracker;

import android.content.Intent;
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
    private Location location;
    private RecyclerView detailRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView locationRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //final Location location = Locations.get(locationName);
        final User user = Credentials.getCurrentUser();
        final Location locationName = Locations.getCurrentLocation();

        String donationsText = "";
        ArrayList<Donation> donationsForLocation = Donations.getDonations(locationName);

        // configures the recycler view
        adapter = new DonationAdapter(Donations.getDonations(locationName), null, username);
        detailRecyclerView = findViewById(R.id.donationsRecyclerView);
        detailRecyclerView.setHasFixedSize(true);
        detailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailRecyclerView.setAdapter(adapter);

        TextView textView = (TextView) findViewById(R.id.detailText);
        if (locationName != null) {
            String detailText = "Name: " + locationName.getName();
            Log.i("", "NOT WORKING");
            detailText = detailText + "\nType: " + locationName.getType()
                    + "\nLongitude: " + locationName.getLongitude() + "\nLatitude: "
                    + locationName.getLatitude() + "\nAddress: " + locationName.getAddress()
                    + "\nPhone Number: " + locationName.getPhoneNumber() + "\n";
            for (int i = 0; i < donationsForLocation.size(); i++) {
                detailText = detailText + donationsForLocation.get(i).getFullDescription() + "\n";
            }
            textView.setText(detailText);
        }
        TextView locationDetail = (TextView) findViewById(R.id.detailText);

        String detailText = "Name: " + locationName.getName();
        detailText = detailText + "\nType: " + locationName.getType()
                + "\nLongitude: " + locationName.getLongitude() + "\nLatitude: "
                + locationName.getLatitude() + "\nAddress: " + locationName.getAddress()
                + "\nPhone Number: " + locationName.getPhoneNumber() + "\n";
//        for (int i = 0; i < donationsForLocation.size(); i++) {
//            detailText = detailText + donationsForLocation.get(i).getFullDescription() + "\n";
//        }
        locationDetail.setText(detailText);

//        Button donationButton = (Button) findViewById(R.id.donationButton);
//        donationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("","before if" + username);
//
//                if ((user.getUserType() == UserType.EMPLOYEE) ||
//                        (user.getUserType() == UserType.ADMIN) ||
//                        (user.getUserType() == UserType.MANAGER)) {
//                    Log.e("","if runs");
//                    Intent intent = new Intent(DetailActivity.this, DonationActivity.class);
//                    intent.putExtra("location", location);
//                    intent.putExtra("username", username);
//                    final LocalDateTime time = LocalDateTime.now();
//                    intent.putExtra("time", time);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Log.i("","User type is not employee");
//                    //Toaster if no access
//                    String text = "You don't have permission to access this.";
//                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//            }
//        });

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent grabbedIntent = getIntent();
                Intent toLocationActivity = new Intent(DetailActivity.this, LocationActivity.class);
                Locations.setCurrentLocation(null);
                startActivity(toLocationActivity);
                finish();
            }
        });
    }

}
