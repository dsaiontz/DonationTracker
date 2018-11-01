package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDateTime;

public class DonationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner donationLocation;
    private EditText shortDescription;
    private EditText longDescription;
    private EditText donationValue;
    private Spinner donationCategorySpinner;
    private Object[] registerSpinnerOptions;
    private Object[] registerLocationOptions;
    private Donations donations;

    // username must be passed with every intent
    // location name should be passed with most intents
    private String username;
    private String locationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_donation);
        donations = new Donations();

        final User username = Credentials.getCurrentUser();
        final Location location = Locations.getCurrentLocation();

        //setting values for spinner for choosing location
        registerLocationOptions = new Object[Locations.getAllLocations().size()+1];
        registerLocationOptions[0] = (Object) "Please Select Location";
        int m = 1;
        for (Location i: Locations.getAllLocations()) {
            registerLocationOptions[m++] = i.getName();
        }

        //setting values for spinner for choosing donation category
        registerSpinnerOptions = new Object[DonationCategory.values().length+1];
        registerSpinnerOptions[0] = (Object) "Please Select Category";
        int k = 1;
        for (DonationCategory i: DonationCategory.values()) {
            registerSpinnerOptions[k++] = i;
        }

        //location spinner
        donationLocation = (Spinner) findViewById(R.id.addDonationLocation);
        donationLocation.setOnItemSelectedListener(this);
        ArrayAdapter<DonationCategory> adapterOne = new ArrayAdapter(this, android.R.layout.simple_spinner_item, registerLocationOptions);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationLocation.setAdapter(adapterOne);
        donationLocation.setOnItemSelectedListener(this);


        //EditTexts for descriptions and value
        shortDescription = (EditText) findViewById(R.id.addDonationShortDescription);
        longDescription = (EditText) findViewById(R.id.addDonationLongDescription);
        donationValue = (EditText) findViewById(R.id.addDonationValue);


        //donation category spinner
        donationCategorySpinner = (Spinner) findViewById(R.id.addDonationSpinner);
        donationCategorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter<DonationCategory> adapterNext = new ArrayAdapter(this, android.R.layout.simple_spinner_item, registerSpinnerOptions);
        adapterNext.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationCategorySpinner.setAdapter(adapterNext);
        donationCategorySpinner.setOnItemSelectedListener(this);


        //add donation button should to to location activity after adding donation to Donations
        Button addDonationButton = (Button) findViewById(R.id.addDonationButton);
        addDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    //need to add more test cases to send to catch block
                    double value = Double.parseDouble(donationValue.getText().toString());
                    donations.addDonation(new Donation(location, shortDescription.getText().toString(),
                            longDescription.getText().toString(),
                            Double.parseDouble(donationValue.getText().toString()),
                            (DonationCategory) donationCategorySpinner.getSelectedItem()));
                    toLocationActivity();
                    finish();
                }
                catch(NumberFormatException e)
                {
                    //toast the user that value needs to be a number
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    String text = "Donation value must be a number.";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        //back button goes to detail activity
        Button backButton = findViewById(R.id.donationBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToDetailActivity();
                Log.i("","before going back to location page when you click button");
                final Intent intentToDetail = new Intent(DonationActivity.this, DetailActivity.class);
                startActivity(intentToDetail);
                finish();
            }
        });
    }

    //methods for spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    public void onNothingSelected(AdapterView parent) {
        // Do nothing.
    }

    public void toLocationActivity() {
        startActivity(new Intent(DonationActivity.this, LocationActivity.class));
        finish();
    }

    public void backToDetailActivity() {
        Intent backToDetailActivity = new Intent(DonationActivity.this, DetailActivity.class);
        backToDetailActivity.putExtra("username", username);
        backToDetailActivity.putExtra("location", locationName);
        startActivity(backToDetailActivity);
        finish();
    }

}
