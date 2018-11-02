package com.example.david.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText nameSearchText;
    private EditText valueSearchText;
    private Spinner searchLocationSpinner;
    private Spinner donationCategorySpinner;
    private Object[] searchTypeOptions;
    private Object[] donationCategoryOptions;
    private RecyclerView donationRecyclerView;
    private RecyclerView.Adapter adapter;

    Donations donos = new Donations(); // In memory of Jackson's object naming
    FirebaseUser user;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainPage();
            }
        });

        nameSearchText = (EditText) findViewById(R.id.nameSearchText);
        nameSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    handleClickNameSearchButton();
                    return true;
                }
                return false;
            }
        });

        valueSearchText = (EditText) findViewById(R.id.valueSearchText);
        valueSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    handleClickValueSearchButton();
                    return true;
                }
                return false;
            }
        });

        searchLocationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        ArrayAdapter<DonationActivity> adapterLoc = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Locations.getAllLocations());
        adapterLoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchLocationSpinner.setAdapter(adapterLoc);
        searchLocationSpinner.setOnItemSelectedListener(this);

        //setting values for spinner for choosing donation category
        donationCategoryOptions = new Object[DonationCategory.values().length+1];
        donationCategoryOptions[0] = (Object) "Please Select Category";
        int k = 1;
        for (DonationCategory i: DonationCategory.values()) {
            donationCategoryOptions[k++] = i;
        }
        donationCategorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<DonationActivity> adapterCat = new ArrayAdapter(this, android.R.layout.simple_spinner_item, donationCategoryOptions);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationCategorySpinner.setAdapter(adapterCat);
        donationCategorySpinner.setOnItemSelectedListener(this);

        Button locationSearchButton = findViewById(R.id.locationSearchButton);
        locationSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickLocationSearchButton();
            }
        });

        //Getting current user
        Intent currentIntent = getIntent();
        user = currentIntent.getParcelableExtra("currentUser");
        username = user.getEmail(); //equivalent to username from previous versions

        Button nameSearchButton = findViewById(R.id.nameSearchButton);
        nameSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickNameSearchButton();
            }
        });

        Button categorySearchButton = findViewById(R.id.categorySearchButton);
        categorySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickCategorySearchButton();
            }
        });

        Button valueSearchButton = findViewById(R.id.valueSearchButton);
        valueSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickValueSearchButton();
            }
        });

        adapter = new SearchAdapter(donos.getDonations(Locations.get("" + searchLocationSpinner.getSelectedItemId())), null);
        donationRecyclerView.findViewById(R.id.searchRecyclerView);
        donationRecyclerView.setHasFixedSize(true);
        donationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        donationRecyclerView.setAdapter(adapter);


        // set up spinner for search options
        /*searchTypeOptions = new Object[4];
        searchTypeOptions[0] =  "Please Select Search Type";
        searchTypeOptions[1] =  "By Keywords";
        searchTypeOptions[2] =  "By Donation Value";
        searchTypeOptions[3] =  "By Category";*/


    }

    private void setDonations(ArrayList<Donation> donations) {
        adapter = new SearchAdapter(donations, null);
    }

    public void backToMainPage() {
        Intent backToMain = new Intent(SearchActivity.this, MainPage.class);
        backToMain.putExtra("currentUser", user);
        startActivity(backToMain);
        finish();
    }

    public void handleClickLocationSearchButton() {
        if (!searchLocationSpinner.getSelectedItem().equals("All")) {
            donos.getDonations(Locations.get((String) searchLocationSpinner.getSelectedItem()));
        } else {
            donos.getAllDonations();
        }
        finish();
    }

    public void handleClickNameSearchButton() {
        donos.filterByName("");
        finish();
    }

    public void handleClickCategorySearchButton() {
        donos.filterByCategory(null);
        finish();
    }

    public void handleClickValueSearchButton() {
        donos.filterByValue(0, 0);
        finish();
    }

    //methods for spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
    }

    public void onNothingSelected(AdapterView parent) {
        // Do nothing.
    }
}
