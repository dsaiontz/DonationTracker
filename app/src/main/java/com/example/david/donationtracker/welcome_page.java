package com.example.david.donationtracker;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * welcome page activity
 */
public class welcome_page extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        db = FirebaseFirestore.getInstance();

        //login button goes to login activity
        Button LoginButton = findViewById(R.id.WelcomePageLoginButton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        //register button goes to register activity
        Button RegisterButton = findViewById(R.id.WelcomePageRegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


       // Button dbButton = (Button) findViewById(R.id.dbButton);
        //dbButton.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View v) {
         //       FirebaseDatabase fdb= FirebaseDatabase.getInstance();
         //       DatabaseReference myRef = fdb.getReference();
         //       User testUser = new User("Update@update.com","update", UserType.ADMIN);
         //       myRef.setValue(testUser);
         //   }
        //});


        //reads in the location data from the csv file, sets it to Locations.CsvLocations
        readLocationData();
        }

    private void readLocationData() {
        BufferedReader reader;
        try {
            AssetManager manager = getAssets();
            reader = new BufferedReader(
                    new InputStreamReader(manager.open("LocationData.csv")));
            String space = " ";
            String comma = ", ";
            String line;
            reader.readLine();
            while ((reader.readLine())!= null) {
                line = reader.readLine();
                final String[] words = line.split(",");

                Map<String, Object> data = new HashMap<>();
                data.put("name", words[1]);
                data.put("latitude", words[2]);
                data.put("longitude", words[3]);
                data.put("streetAddress", words[4]);
                data.put("city", words[5]);
                data.put("state", words[6]);
                data.put("address", data.get("streetAddress") + space + data.get("city") + comma +
                        data.get("state"));
                data.put("type", words[8]);
                data.put("phoneNumber", words[9]);

                CollectionReference locColl = db.collection("locations");
                DocumentReference docuRef = locColl.document(words[1]);

                Task<Void> setLocTask = docuRef.set(data, SetOptions.merge());
                setLocTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("locationAdded",
                                        "DocumentSnapshot successfully written: " + words[1]);
                            }
                        });
                        setLocTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("locationAdded", "Error writing document", e);
                            }
                        });
                Locations.add(createLocation(words));
            }
            reader.close();
        } catch (Exception e) {
            Log.w("Location Data", "Reading Location Data crashed" + "\n" + e.getMessage());
        }
    }

    private Location createLocation(String[] words) {
        String space = " ";
        String comma = ", ";
        String name = words[1];
        String latitude = words[2];
        String longitude = words[3];
        String streetAddress = words[4];
        String city = words[5];
        String state = words[6];
        String address = streetAddress + space + city + comma + state;
        String type = words[8];
        String phoneNumber = words[9];
        return new Location(name, type, longitude, latitude,
                address, phoneNumber);
    }



    private void logout() {
        startActivity(new Intent(welcome_page.this, LoginActivity.class));
        finish();
    }

    private void register() {
        startActivity(new Intent(welcome_page.this, RegisterActivity.class));
        finish();
    }
}
