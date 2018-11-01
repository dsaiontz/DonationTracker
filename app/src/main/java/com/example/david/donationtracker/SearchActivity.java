package com.example.david.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchActivity extends AppCompatActivity {

    private EditText searchText;
    private Spinner searchTypeSpinner;
    private Spinner donationCategorSpinner;
    private Object[] searchTypeOptions;
    private Object[] donationCategoryOptions;

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

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickSearchButton();
            }
        });

        // set up spinner for search options
        searchTypeOptions = new Object[4];
        searchTypeOptions[0] =  "Please Select Search Type";
        searchTypeOptions[1] =  "By Keywords";
        searchTypeOptions[2] =  "By Donation Value";
        searchTypeOptions[3] =  "By Category";

        //setting values for spinner for choosing donation category
        donationCategoryOptions = new Object[DonationCategory.values().length+1];
        donationCategoryOptions[0] = (Object) "Please Select Category";
        int k = 1;
        for (DonationCategory i: DonationCategory.values()) {
            donationCategoryOptions[k++] = i;
        }

    }

    public void backToMainPage() {
        Intent backToMain = new Intent(SearchActivity.this, MainPage.class);
        startActivity(backToMain);
        finish();
    }

    public void handleClickSearchButton() {



        finish();
    }
}
