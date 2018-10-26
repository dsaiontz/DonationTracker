package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        final Location location = getIntent().getParcelableExtra("location");

        TextView text = (TextView) findViewById(R.id.text);
        text.setText("Name: " + location.getName() + "\nType: " + location.getType()
                + "\nLongitude: " + location.getLongitude() + "\nLatitude: "
                + location.getLatitude() + "\nAddress: " + location.getAddress()
                + "\nPhone Number: " + location.getPhoneNumber());

        final String username = getIntent().getStringExtra("username");
        Intent intent = new Intent(DetailActivity.this, DonationActivity.class);
        intent.putExtra("username", username);
        final User user = Credentials.get(username);

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
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("","else runs");
                    //Toaster if no access
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    String text = "Your user type does not have access.";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

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
