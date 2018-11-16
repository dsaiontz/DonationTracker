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

        TextView textView = (TextView) findViewById(R.id.donation);
        textView.setText(Donations.getCurrentDonation().toString());
    }
}
