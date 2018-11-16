package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Donation activity class
 */
public class DonationActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private EditText shortDescription;
    private EditText longDescription;
    private EditText donationValue;
    private Spinner donationCategorySpinner;

    // username must be passed with every intent
    // location name should be passed with most intents
    private String username;
    // --Commented out by Inspection (11/16/18 1:15 PM):private String locationName;

    private FirebaseUser user;

    //Firebase add ons
    //private DocumentReference mDocRef = FirebaseFirestore.getInstance();
    private FirebaseFirestore db;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_donation);
//        Donations donations = new Donations();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

//        Intent currentIntent = getIntent();

        user = mAuth.getCurrentUser();
        username = user.getEmail();

        location = Locations.getCurrentLocation();

        //setting values for spinner for choosing location
//        Object[] registerLocationOptions = new Object[Locations.getAllLocations().size() + 1];
//        registerLocationOptions[0] = (Object) "Please Select Location";
//        int m = 1;
//        for (Location i: Locations.getAllLocations()) {
//            registerLocationOptions[m++] = i.getName();
//        }

        //setting values for spinner for choosing donation category
        Object[] registerSpinnerOptions = new Object[DonationCategory.values().length + 1];
        registerSpinnerOptions[0] = "Please Select Category";
        int k = 1;
        for (DonationCategory i: DonationCategory.values()) {
            registerSpinnerOptions[k] = i;
            k++;
        }

        //EditTexts for descriptions and value
        shortDescription = findViewById(R.id.addDonationShortDescription);
        longDescription = findViewById(R.id.addDonationLongDescription);
        donationValue = findViewById(R.id.addDonationValue);


        //donation category spinner
        donationCategorySpinner = findViewById(R.id.addDonationSpinner);
        donationCategorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter<DonationCategory> adapterNext = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, registerSpinnerOptions);
        adapterNext.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationCategorySpinner.setAdapter(adapterNext);
        donationCategorySpinner.setOnItemSelectedListener(this);


        //add donation button should to to location activity after adding donation to Donations
        final Button addDonationButton = findViewById(R.id.addDonationButton);
        addDonationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    Editable shortDesc = shortDescription.getText();
                    Editable longDesc = longDescription.getText();
                    Editable doubleEdit = donationValue.getText();
                    Object categoryItem = donationCategorySpinner.getSelectedItem();

                    //ADDING DONATIONS TO FIREBASE
                    Map<String, Object> data = new HashMap<>();
                    data.put("shortDescription", shortDesc.toString());
                    data.put("longDescription", longDesc.toString());
                    data.put("donationValue", Double.parseDouble(doubleEdit
                            .toString()));
                    data.put("donationCategory", categoryItem
                            .toString());

                    CollectionReference locCollection = db.collection("locations");
                    DocumentReference locationDocument = locCollection.document(location.getName());
                    CollectionReference donationsCollection =
                            locationDocument.collection("donations");
                    donationsCollection.add(data);

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
                final Intent intentToDetail = new Intent(DonationActivity.this,
                        DetailActivity.class);
                startActivity(intentToDetail);
                finish();
            }
        });
    }
    
    //methods for spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView parent) {
        // Do nothing.
    }

    private void toLocationActivity() {
        Intent intent = new Intent(DonationActivity.this, LocationActivity.class);
        intent.putExtra("currentUser", user);
        startActivity(intent);
        finish();
    }

    private void backToDetailActivity() {
        Intent backToDetailActivity = new Intent(DonationActivity.this,
                DetailActivity.class);
        backToDetailActivity.putExtra("username", username);
        backToDetailActivity.putExtra("location", location.getName());
        startActivity(backToDetailActivity);
        finish();
    }


    //private void addDonationToFirebase(Donation donation) {
    //    fdb = FirebaseDatabase.getInstance();
    //    DatabaseReference myRef = fdb.getReference();
    //    myRef.setValue(donation);
    //}

    //METHOD CONTRACT:
    //Method signature: addDonationToFirebase(Donation : donation) : void
    //Precondition: The parameter donation does not equal null
    //Postcondition: A donation object is added to firebase
    //Invariant: the donation object will always be valid when this method is called in our code
    //Frame Condition: Firebase realtime database gets written onto (changed), and
    //the donation object passed in doesn't change


}
