package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * search activity class
 */
public class SearchActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private static final String SEARCH_RESULTS_FOR = "search results for %s %s";
    private static final String IS_EMPTY = " is empty";
    private static final String DONATION_VALUE = "donationValue";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String LONG_DESCRIPTION = "longDescription";
    private static final String DONATION_CATEGORY = "donationCategory";
    private static final String SEARCH_RESULTS_FOR1 = "search results for %s %s";
    private static final String IS_EMPTY1 = " is empty";
    private static final String SEARCH_RESULTS_FOR_THE_NAME = "search results for the name %s %s";
    private static final String IS_EMPTY2 = " is empty";
    private static final String SEARCH_RESULTS_FOR2 = "search results for %s %s";
    private static final String IS_EMPTY3 = "is empty";
    private static final String SEARCH_RESULTS_FOR_VALUE_RANGE = "search results for value range %s %s %s %s";
    private static final String STRING = "-";
    private static final String EMPTY = " empty";
    private EditText nameSearchText;
    private EditText valueSearchTextMin;
    private EditText valueSearchTextMax;
    private Spinner searchLocationSpinner;
    private Spinner donationCategorySpinner;
    private RecyclerView donationRecyclerView;
    private RecyclerView.Adapter adapter;

    private Object[] donationCategoryOptions;
    private ArrayList<String> locs;

    private final Donations donations = new Donations();
    private FirebaseUser user;

    private FirebaseFirestore db;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeButtons();

        db = FirebaseFirestore.getInstance();

        initializeSearchTexts();

        searchLocationSpinner = findViewById(R.id.locationSpinner);

        locs = new ArrayList<>();
        locs.add("All");
        ArrayList theL = Locations.getAllLocations();
        Location m;
        String s;
        int v = theL.size();
        for (int x = 0; x < v; x++) {
            m = (Location) theL.get(x);
            s = m.getName();
            locs.add(s);
            //locs.add(Locations.getAllLocations().get(x).getName());
        }

        //setting values for spinner for choosing donation category
        donationCategoryOptions = new Object[DonationCategory.values().length + 1];
        donationCategoryOptions[0] = "Please Select Category";
        int k = 1;
        for (DonationCategory i: DonationCategory.values()) {
            donationCategoryOptions[k] = i;
            k++;
        }

        initializeSpinners();

        initializeButtons();

        //Getting current user
        Intent currentIntent = getIntent();
        user = currentIntent.getParcelableExtra("currentUser");

        // set up spinner for search options
//        Object[] searchTypeOptions = new Object[4];
//        searchTypeOptions[0] =  "Please Select Search Type";
//        searchTypeOptions[1] =  "By Keywords";
//        searchTypeOptions[2] =  "By Donation Value";
//        searchTypeOptions[3] =  "By Category";

        context = this;

        donations.getAllDonationsFromDatabase();
    }

    private void initializeButtons() {
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

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToLocationActivity();
            }
        });
    }

    private void initializeSearchTexts() {
        nameSearchText = findViewById(R.id.nameSearchText);
        nameSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if ((id == EditorInfo.IME_ACTION_DONE) || (id == EditorInfo.IME_NULL)) {
                    handleClickNameSearchButton();
                    return true;
                }
                return false;
            }
        });

        valueSearchTextMin = findViewById(R.id.valueSearchTextMin);
        valueSearchTextMin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if ((id == EditorInfo.IME_ACTION_DONE) || (id == EditorInfo.IME_NULL)) {
                    handleClickValueSearchButton();
                    return true;
                }
                return false;
            }
        });

        valueSearchTextMax = findViewById(R.id.valueSearchTextMax);
        valueSearchTextMax.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if ((id == EditorInfo.IME_ACTION_DONE) || (id == EditorInfo.IME_NULL)) {
                    handleClickValueSearchButton();
                    return true;
                }
                return false;
            }
        });
    }

    private void initializeSpinners() {
        ArrayAdapter<DonationActivity> adapterLoc = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, locs);
        adapterLoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchLocationSpinner.setAdapter(adapterLoc);
        searchLocationSpinner.setOnItemSelectedListener(this);

        donationCategorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<DonationActivity> adapterCat = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, donationCategoryOptions);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        donationCategorySpinner.setAdapter(adapterCat);
        donationCategorySpinner.setOnItemSelectedListener(this);
    }

// --Commented out by Inspection START (11/16/18 10:30 AM):
//    private void setDonations(ArrayList<Donation> donations) {
//        adapter = new SearchAdapter(donations);
//    }
// --Commented out by Inspection STOP (11/16/18 10:30 AM)

    private void backToLocationActivity() {
        Intent backToLocationActivity = new Intent(
                SearchActivity.this, LocationActivity.class);
        backToLocationActivity.putExtra("currentUser", user);
        startActivity(backToLocationActivity);
        finish();
    }

    private void handleClickLocationSearchButton() {
        ArrayList<Donation> searchResults;
        Object item = searchLocationSpinner.getSelectedItem();
        String itemString = item.toString();
        if (!"All".equals(itemString)) {
            final ArrayList<Donation> results = new ArrayList<>();
            final Location location = Locations.get((String)
                    (searchLocationSpinner.getSelectedItem()));
            CollectionReference locColl = db.collection("locations");
            String obj = (String) searchLocationSpinner.getSelectedItem();
            DocumentReference docuRefer = locColl.document(obj);
            CollectionReference donColl = docuRefer.collection("donations");
            Task<QuerySnapshot> task1 = donColl.get();
            task1.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    Log.d("retrievedDonation", document.getId()
                                            + " => " + document.getData());
                                    Map<String, Object> data = document.getData();
                                    double dub = (double) data.get(DONATION_VALUE);
                                    Donation donation = new Donation(location,
                                            (String) (data.get(SHORT_DESCRIPTION)),
                                            (String) (data.get(LONG_DESCRIPTION)), dub,
                                            (DonationCategory.valueOf((String)
                                                    data.get(DONATION_CATEGORY))));
                                    results.add(donation);
                                }
                                adapter = new DonationAdapter(results);
                                if (results.isEmpty()) {
                                    Log.e("","searchresults is empty, does nothing rn");
                                    TextView emptyMessage = findViewById(R.id.emptyMessageView);
                                    String s = String.format(SEARCH_RESULTS_FOR,searchLocationSpinner.getSelectedItem().toString(),IS_EMPTY);
                                    emptyMessage.setText(s);
                                } else {
                                    adapter = new SearchAdapter(results);
                                    donationRecyclerView = findViewById(R.id.donationsRecyclerView);
                                    donationRecyclerView.setHasFixedSize(false);
                                    donationRecyclerView.setLayoutManager(new
                                            LinearLayoutManager(context));
                                    donationRecyclerView.setAdapter(adapter);
                                }
                            } else {
                                Log.d("retrievedDonation", "Error getting documents: ",
                                        task.getException());
                            }
                        }
                    });
        } else {
            searchResults = donations.getAllDonations();
            
            if (searchResults.isEmpty()) {
                Log.e("","searchresults is empty, does nothing rn");
                TextView emptyMessage = findViewById(R.id.emptyMessageView);
                String q = String.format(SEARCH_RESULTS_FOR1, searchLocationSpinner.getSelectedItem().toString(),IS_EMPTY1);
                emptyMessage.setText(q);
            } else {
                adapter = new SearchAdapter(searchResults);
                donationRecyclerView = findViewById(R.id.donationsRecyclerView);
                donationRecyclerView.setHasFixedSize(false);
                donationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                donationRecyclerView.setAdapter(adapter);
            }
        }
    }

    private void handleClickNameSearchButton() {
        Editable c = nameSearchText.getText();
        String searchText = c.toString();
        ArrayList<Donation> searchResults = donations.filterByName(searchText);

        if (searchResults.isEmpty()) {
            Log.e("","search results is empty, does nothing rn");
            TextView emptyMessage = findViewById(R.id.emptyMessageView);
            String inter = String.format(SEARCH_RESULTS_FOR_THE_NAME, searchText,IS_EMPTY2);
            emptyMessage.setText(inter);
        } else {
            adapter = new SearchAdapter(searchResults);
            donationRecyclerView = findViewById(R.id.donationsRecyclerView);
            donationRecyclerView.setHasFixedSize(false);
            donationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            donationRecyclerView.setAdapter(adapter);
        }
    }

    private void handleClickCategorySearchButton() {
        Object item = searchLocationSpinner.getSelectedItem();
        String itemString = item.toString();
        boolean isAllLocs = "All".equals(itemString);
        DonationCategory category = (DonationCategory) donationCategorySpinner.getSelectedItem();
        ArrayList<Donation> searchResults = donations.filterByCategory(category, isAllLocs);
        if (searchResults.isEmpty()) {
            Log.e("","search results is empty, does nothing rn");
            TextView emptyMessage = findViewById(R.id.emptyMessageView);
            String in = String.format(SEARCH_RESULTS_FOR2, category, IS_EMPTY3);
            emptyMessage.setText(in);
        } else {
            adapter = new SearchAdapter(searchResults);
            donationRecyclerView = findViewById(R.id.donationsRecyclerView);
            donationRecyclerView.setHasFixedSize(true);
            donationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            donationRecyclerView.setAdapter(adapter);
        }
    }

    private void handleClickValueSearchButton() {
        Object item = searchLocationSpinner.getSelectedItem();
        String itemString = item.toString();
        boolean isAllLocs = "All".equals(itemString);
        Log.e("","Just called handleclickvaluesearchbutton");
        ArrayList<Donation> searchResults = donations.filterByValue(
                Double.parseDouble(valueSearchTextMin.getText().toString()),
                Double.parseDouble(valueSearchTextMax.getText().toString()),
                isAllLocs, searchLocationSpinner.getSelectedItem().toString());
        if (searchResults.isEmpty()) {
            Log.e("","searchresults is empty, does nothing rn");
            TextView emptyMessage = findViewById(R.id.emptyMessageView);
            String v = String.format(SEARCH_RESULTS_FOR_VALUE_RANGE,valueSearchTextMin.getText().toString(),STRING,valueSearchTextMax.getText().toString(),EMPTY);
            emptyMessage.setText(v);
        } else {
            Log.e("", "first donation in search results: " + searchResults.get(0));
            adapter = new SearchAdapter(searchResults);
            donationRecyclerView = findViewById(R.id.donationsRecyclerView);
            donationRecyclerView.setHasFixedSize(false);
            donationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            donationRecyclerView.setAdapter(adapter);
        }
        Log.e("","After setting recycler view adapter to search results");
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
}
