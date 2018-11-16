package com.example.david.donationtracker;

import android.content.Context;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Map;

/**
 * detail activity class
 */
@SuppressWarnings("ConstantConditions")
public class DetailActivity extends AppCompatActivity {

    // --Commented out by Inspection (11/16/18 10:44 AM):private String locationName;
    private RecyclerView.Adapter adapter;
    private RecyclerView locationRecyclerView;

    // --Commented out by Inspection (11/16/18 10:29 AM):private Donations donos = new Donations();

    private FirebaseUser user;


    private FirebaseFirestore db;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    private String userType;

    private FirebaseAuth mAuth;

    private DocumentSnapshot userTypeInfo;


    private ArrayList<Donation> listForAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        listForAdapter = new ArrayList<>();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

//        AndroidThreeTen.init(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final Location location = Locations.getCurrentLocation();

        user = mAuth.getCurrentUser();

        final Button donationButton = findViewById(R.id.donationButton);

        CollectionReference usersCollection = db.collection("users");

        DocumentReference docRef = usersCollection.document(user.getEmail());

        Task<DocumentSnapshot> snapshot = docRef.get();
        snapshot.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    userTypeInfo = task.getResult();
                    donationButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String userType = (String) userTypeInfo.get("userType");
                            if (("EMPLOYEE".equals(userType)) ||
                                    ("ADMIN".equals(userType)) ||
                                    ("MANAGER".equals(userType))) {
                                Intent intent =
                                        new Intent(DetailActivity.this,
                                                DonationActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String text = "You don't have permission to access this.";
                                Toast toast = Toast.makeText(getApplicationContext(), text,
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                    if (document.exists()) {
                        Log.d("getUserType", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("getUserType", "No such document");
                    }
                } else {
                    Log.d("getUserType", "get failed with ", task.getException());
                }
            }
        });

        final Context context = this;

        //NEED ARRAYLIST OF DONATIONS

        CollectionReference locationsCollection = db.collection("locations");
        DocumentReference docuRefer = locationsCollection.document(location.getName());
        CollectionReference donationsAtLocation = docuRefer.collection("donations");
                Task<QuerySnapshot> task = donationsAtLocation.get();
                task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("retrievedDonation", document.getId() + " => " +
                                        document.getData());
                                Map<String, Object> data = document.getData();
                                double dub = (double) data.get("donationValue");
                                Donation donation = new Donation(location, (String)
                                        (data.get("shortDescription")),
                                        (String) (data.get("longDescription")), dub,
                                        (DonationCategory.valueOf((String)
                                                data.get("donationCategory"))));
                                listForAdapter.add(donation);
                            }
                            adapter = new DonationAdapter(listForAdapter);
                            locationRecyclerView = findViewById(R.id.donationsRecyclerView);
                            locationRecyclerView.setHasFixedSize(true);
                            locationRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                            locationRecyclerView.setAdapter(adapter);

                            //Sets text for detailed information of location
                            TextView textView = findViewById(R.id.detailText);
                            if (location != null) {
                                String detailText = "Name: " + location.getName();
                                detailText = detailText + "\nType: " + location.getType()
                                        + "\nLongitude: " + location.getLongitude() + "\nLatitude: "
                                        + location.getLatitude() + "\nAddress: "
                                        + location.getAddress()
                                        + "\nPhone Number: " + location.getPhoneNumber() + "\n";
                                textView.setText(detailText);
                            }
                            textView.setTextColor(Color.parseColor("#FFFFFF"));
                        } else {
                            Log.d("retrievedDonation", "Error getting documents: ",
                                    task.getException());
                        }
                    }
                });

        if (user == null) {
            Log.e("userError", "The passed in instance of user is null");
        } else if (user.getEmail() == null) {
            Log.e("userError", "instance of user didn't have an email");
        }



        //Sets text for detailed information of location

        docRef = usersCollection.document(user.getEmail());
        Task<DocumentSnapshot> userInfoTask = docRef.get();
                userInfoTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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




   //public void readFromFirebase() {
   //    //if (locationName == "")
   //    //set int i accordingly
   //    myRef = database.getReference();

   //    myRef.addValueEventListener(new ValueEventListener() {
   //        @Override
   //        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
   //            showData(dataSnapshot);
   //        }

   //        @Override
   //        public void onCancelled(@NonNull DatabaseError databaseError) {

   //        }
   //    });
   //}

    //public void showData(DataSnapshot dataSnapshot) {
    //    for (DataSnapshot ds : dataSnapshot.getChildren()) {
    //        adapter = new DonationAdapter(Donations.getDonations(location), null, user.getEmail());
    //        locationRecyclerView = findViewById(R.id.donationsRecyclerView);
    //        locationRecyclerView.setHasFixedSize(true);
    //        locationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    //        locationRecyclerView.setAdapter(adapter);
    //    }
    //}




