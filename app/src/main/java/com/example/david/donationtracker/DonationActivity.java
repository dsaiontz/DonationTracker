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

public class DonationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner donationLocation;
    private EditText shortDescription;
    private EditText longDescription;
    private EditText donationValue;
    private Spinner donationCategorySpinner;

    private Object[] registerSpinnerOptions;
    private Object[] registerLocationOptions;

    private Donations donos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_donation);
        donos = new Donations();

        final String username = getIntent().getExtras().getString("username");
        final Location location = getIntent().getParcelableExtra("location");

        registerLocationOptions = new Object[Donations.getValidLocations().length+1];
        registerLocationOptions[0] = (Object) "PLEASE SELECT LOCATION";
        int m = 1;
        for (String i: Donations.getValidLocations()) {
            registerLocationOptions[m++] = i;
        }

        registerSpinnerOptions = new Object[DonationCategory.values().length+1];
        registerSpinnerOptions[0] = (Object) "PLEASE SELECT CATEGORY";
        int k = 1;
        for (DonationCategory i: DonationCategory.values()) {
            registerSpinnerOptions[k++] = i;
        }

        donationLocation = (Spinner) findViewById(R.id.addDonationLocation);
        donationLocation.setOnItemSelectedListener(this);
        ArrayAdapter<DonationCategory> adapterOne = new ArrayAdapter(this, android.R.layout.simple_spinner_item, registerLocationOptions);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationLocation.setAdapter(adapterOne);
        donationLocation.setOnItemSelectedListener(this);

        shortDescription = (EditText) findViewById(R.id.addDonationShortDescription);
        longDescription = (EditText) findViewById(R.id.addDonationLongDescription);
        donationValue = (EditText) findViewById(R.id.addDonationValue);

        donationCategorySpinner = (Spinner) findViewById(R.id.addDonationSpinner);
        donationCategorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter<DonationCategory> adapterNext = new ArrayAdapter(this, android.R.layout.simple_spinner_item, registerSpinnerOptions);
        adapterNext.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationCategorySpinner.setAdapter(adapterNext);
        donationCategorySpinner.setOnItemSelectedListener(this);

        Button addDonationButton = (Button) findViewById(R.id.addDonationButton);
        addDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    //////TRY OTHER TESTS TO MAKE SURE THERE AREN'T ANY OTHER ERRORS
                    Double.parseDouble(donationValue.getText().toString());
                    Location locale = new Location(donationLocation.getSelectedItem().toString();
                    ///////LOCATION NEEDS FULL CONSTRUCTOR EVENTUALLY!!!
                    donos.addDonation(new Donation(locale,
                            shortDescription.getText().toString(), longDescription.getText().toString(),
                            Double.parseDouble(donationValue.getText().toString()),
                            (DonationCategory) donationCategorySpinner.getSelectedItem()));
//                    Intent intent = new Intent(DonationActivity.this, MainPage.class);
//                    intent.putExtra("username", username);
                    final Intent intentToDetail = new Intent(DonationActivity.this, DetailActivity.class);
                    intentToDetail.putExtra("username", username);
                    intentToDetail.putExtra("location", locale);
                    startActivity(intentToDetail);
                    finish();
                }
                catch(NumberFormatException e)
                {
                    //toast
                    int duration = Toast.LENGTH_SHORT;
                    Context context = getApplicationContext();
                    String text = "Value must be a double.";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        Button backButton = (Button) findViewById(R.id.donationBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("","before going back to location page when you click button");
                final Intent intentToDetail = new Intent(DonationActivity.this, DetailActivity.class);
                intentToDetail.putExtra("username", username);
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

}
