package com.example.david.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DonationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail_layout);

        Intent intent = getIntent();

        Donation donation = (Donation) intent.getSerializableExtra("donation");
//        Donation donation = new Donation(intent.getStringExtra("donation"),
//                Locations.get(intent.getStringExtra("location")), intent.getStringExtra("short description"),
//                intent.getStringExtra("full description"), Double.parseDouble(intent.getStringExtra("value")),
//                DonationCategory.valueOf(intent.getStringExtra("category")));

        TextView textView = (TextView) findViewById(R.id.donation);
        textView.setText(donation.toString());
    }
}
