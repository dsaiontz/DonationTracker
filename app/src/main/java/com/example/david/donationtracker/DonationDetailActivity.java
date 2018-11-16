package com.example.david.donationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DonationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_detail_layout);

        Log.e("","inside donationdetailactivity");

        TextView textView = findViewById(R.id.donation);

        Donation don = Donations.getCurrentDonation();

        String string = don.toString();

        textView.setText(string);
    }
}
