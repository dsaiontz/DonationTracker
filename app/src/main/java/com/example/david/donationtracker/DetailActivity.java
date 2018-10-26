package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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

import javax.security.auth.login.LoginException;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent grabbedIntent = getIntent();
        final String username = grabbedIntent.getStringExtra("username");
        final Location location = grabbedIntent.getParcelableExtra("location");
        final User user = Credentials.get(username);

        String donationsText = "";
        Log.e("Donations", "onCreate: " + Donations.getDonations().toString());
        donationsText = Donations.getDonations().toString();

        Log.e("", "after this is location");
        if (location != null) {
            Log.e("","location is not null " + location.getName());
        }

        TextView text = (TextView) findViewById(R.id.text);
        String detailText = "Name: " + location.getName();
        Log.i("","NOT WORKING");
        detailText = detailText + "\nType: " + location.getType()
                + "\nLongitude: " + location.getLongitude() + "\nLatitude: "
                + location.getLatitude() + "\nAddress: " + location.getAddress()
                + "\nPhone Number: " + location.getPhoneNumber() + "\n" + donationsText;
        text.setText(detailText);

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
                    startActivity(intent);
                    finish();
                } else {
                    Log.i("","User type is not employee");
                    //Toaster if no access
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    String text = "You don't have permission to access this.";
                    Toast toast = Toast.makeText(context, text, duration);
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
