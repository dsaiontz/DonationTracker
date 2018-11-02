package com.example.david.donationtracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.time.Clock;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;

public class DetailActivity extends AppCompatActivity {

    private String username;
    private String locationName;
    private RecyclerView.Adapter adapter;
    private RecyclerView locationRecyclerView;

    private String userNameFromAdapter;

    Donations donos = new Donations();

    private FirebaseUser user;

    private FirebaseFirestore db;

    private String userType;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mAuth = FirebaseAuth.getInstance();

        AndroidThreeTen.init(this);

        Intent grabbedIntent = getIntent();


        //user and location are static variables that represent the current user and current location being used

        db = FirebaseFirestore.getInstance();
        final Location location = Locations.getCurrentLocation();

        Intent currentIntent = getIntent();
        user = mAuth.getCurrentUser();
        username = user.getEmail();


        DocumentReference docRef = db.collection("users").document(user.getEmail());
        final DocumentSnapshot userTypeInfo = docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    userType = (String) (document.get("userType"));
                    if (document.exists()) {
                        Log.d("getUserType", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("getUserType", "No such document");
                    }
                } else {
                    Log.d("getUserType", "get failed with ", task.getException());
                }
            }
        }).getResult();

        //configures the recycler view that holds the location detail activity as well as donations at that location
        adapter = new DonationAdapter(donos.getDonations(location), null, username);
        locationRecyclerView = findViewById(R.id.donationsRecyclerView);
        locationRecyclerView.setHasFixedSize(true);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        locationRecyclerView.setAdapter(adapter);
        if (user == null) {
            Log.e("userError", "The passed in instance of user is null");
        } else if (user.getEmail() == null) {
            Log.e("userError", "instance of user didn't have an email");
        }

        //UNCOMMENT AND FIX WHEN DATABASE IS WORKING
//        if (Donations.getDonations(location) != null) {
//            adapter = new DonationAdapter(Donations.getDonations(location), null, user.getEmail());
//            locationRecyclerView = findViewById(R.id.donationsRecyclerView);
//            locationRecyclerView.setHasFixedSize(true);
//            locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//            locationRecyclerView.setAdapter(adapter);
//        }


        //Sets text for detailed information of location
        TextView textView = (TextView) findViewById(R.id.detailText);
        if (location != null) {
            String detailText = "Name: " + location.getName();
            detailText = detailText + "\nType: " + location.getType()
                    + "\nLongitude: " + location.getLongitude() + "\nLatitude: "
                    + location.getLatitude() + "\nAddress: " + location.getAddress()
                    + "\nPhone Number: " + location.getPhoneNumber() + "\n";
            textView.setText(detailText);
        }
        textView.setTextColor(Color.parseColor("#FFFFFF"));

        //Button for adding donation, displays toast if just a USER
        Button donationButton = findViewById(R.id.donationButton);

        donationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((userTypeInfo.get("userType").equals("EMPLOYEE")) ||
                        (userTypeInfo.get("userType").equals("ADMIN")) ||
                        (userTypeInfo.get("userType").equals("MANAGER"))) {
                    Intent intent = new Intent(DetailActivity.this, DonationActivity.class);
                    //final LocalDateTime time = LocalDateTime.now();
                    final org.threeten.bp.LocalDateTime time = org.threeten.bp.LocalDateTime.now();
                    intent.putExtra("time", time);
                    startActivity(intent);
                    finish();
                } else {
                    String text = "You don't have permission to access this.";
                    Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        docRef = db.collection("users").document(user.getEmail());
        DocumentSnapshot document;
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("pulledUserType", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("pulledUserType", "No such document");
                    }
                } else {
                    Log.d("pulledUserType", "get failed with ", task.getException());
                }
            }
        });


        //Back button returns to locationactivity
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLocationActivity = new Intent(DetailActivity.this, LocationActivity.class);
                toLocationActivity.putExtra("currentUser", user);
                startActivity(toLocationActivity);
                finish();
            }
        });
    }

}
