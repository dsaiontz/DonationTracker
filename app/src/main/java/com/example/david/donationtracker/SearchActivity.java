package com.example.david.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText nameSearchText;
    private EditText valueSearchText;
    private Spinner searchLocationSpinner;
    private Spinner donationCategorySpinner;
    private Object[] searchTypeOptions;
    private Object[] donationCategoryOptions;
    private RecyclerView donationRecyclerView;
    private RecyclerView.Adapter adapter;

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
        //nameSearchText.setOnEditorActionListener();

        valueSearchText = (EditText) findViewById(R.id.valueSearchText);
        //valueSearchText.setOnEditorActionListener();

        searchLocationSpinner = (Spinner) findViewById(R.id.locationSpinner);

        //setting values for spinner for choosing donation category
        donationCategoryOptions = new Object[DonationCategory.values().length+1];
        donationCategoryOptions[0] = (Object) "Please Select Category";
        int k = 1;
        for (DonationCategory i: DonationCategory.values()) {
            donationCategoryOptions[k++] = i;
        }
        donationCategorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        //donationCategorySpinner.

        Button locationSearchButton = findViewById(R.id.locationSearchButton);
        locationSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickLocationSearchButton();
            }
        });

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

        // set up spinner for search options
        searchTypeOptions = new Object[4];
        searchTypeOptions[0] =  "Please Select Search Type";
        searchTypeOptions[1] =  "By Keywords";
        searchTypeOptions[2] =  "By Donation Value";
        searchTypeOptions[3] =  "By Category";


    }

    private void setDonations(ArrayList<Donation> donations) {
        adapter = new SearchAdapter(donations, null);
    }

    public void backToMainPage() {
        Intent backToMain = new Intent(SearchActivity.this, MainPage.class);
        startActivity(backToMain);
        finish();
    }

    public void handleClickLocationSearchButton() {



        finish();
    }

    public void handleClickNameSearchButton() {



        finish();
    }

    public void handleClickCategorySearchButton() {



        finish();
    }

    public void handleClickValueSearchButton() {



        finish();
    }
}
