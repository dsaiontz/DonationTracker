package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int minPasswordLength = 6;

    // UI References
    private EditText email;
    private EditText pass1;
    private EditText pass2;

    private Spinner userSpinner;
    private Object[] registerSpinnerOptions;

    private Spinner locationSpinner;
    private Object[] registerLocationOptions;

    private Credentials creds;

    // Regex Patterns
    private Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        creds = new Credentials();

        db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerSpinnerOptions = new Object[UserType.values().length+1];
        registerSpinnerOptions[0] = (Object) "PLEASE SELECT USER TYPE";
        int k = 1;
        for (UserType i : UserType.values()) {
            registerSpinnerOptions[k++] = i;
        }

        registerLocationOptions = new Object[Locations.getAllLocations().size()+1];
        registerLocationOptions[0] = (Object) "PLEASE SELECT LOCATION";
        int m = 1;
        for (Location i : Locations.getAllLocations()) {
            registerLocationOptions[m++] = i.getName();
        }

        email = (EditText) findViewById(R.id.RegisterPageEmail);
        pass1 = (EditText) findViewById(R.id.RegisterPassword1);
        pass2 = (EditText) findViewById(R.id.RegisterPassword2);

        userSpinner = (Spinner) findViewById(R.id.userSpinner);
        userSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<DonationActivity> adapterNext = new ArrayAdapter(this, android.R.layout.simple_spinner_item, registerSpinnerOptions);
        adapterNext.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapterNext);
        userSpinner.setOnItemSelectedListener(this);

        locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<DonationActivity> adapterNew = new ArrayAdapter(this, android.R.layout.simple_spinner_item, registerLocationOptions);
        adapterNext.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapterNew);
        locationSpinner.setOnItemSelectedListener(this);


        Button registerButton = (Button) findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        Button cancelButton = (Button) findViewById(R.id.RegisterPageCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

    }


    //goes to login activity, registers user
    private void register() {
        String emailText = email.getText().toString();
        String passText1 = pass1.getText().toString();
        String passText2 = pass2.getText().toString();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailText);
        if (userSpinner.getSelectedItem().equals(registerSpinnerOptions[0])) {
            email.setError("Please choose a user type!");
            email.requestFocus();
        } else if (!matcher.find()) {
            email.setError("Invalid email address!");
            email.requestFocus();
        } else if (!passText1.equals(passText2)) {
            pass1.setError("Passwords need to match!");
            pass2.setError("Passwords need to match!");
            pass1.requestFocus();
        } else if (pass1.length() < minPasswordLength) {
            pass1.setError("Password must be at least " + minPasswordLength + " characters long");
            pass1.requestFocus();
        } else if (creds.containsKey(emailText)){
            email.setError("This email is already registered!");
            email.requestFocus();
        } else {
            final Context context = getApplicationContext();
            creds.add(new User(emailText, passText1, (UserType) userSpinner.getSelectedItem()));

            //ADDING USER TO FIREBASE
            mAuth.createUserWithEmailAndPassword(emailText, passText1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign up success, update UI with the signed-in user's information
                                Log.d("creationSuccess", "createUserWithEmail:success");
                            } else {
                                // If sign up fails, display a message to the user.
                                Log.w("creationSuccess", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(context, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                                finish();
                            }
                        }
                    });

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userType", userSpinner.getSelectedItem());
            userInfo.put("location", locationSpinner.getSelectedItem());

            DocumentReference docref = db.collection("users").document(emailText);
                    docref.set(userInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("userTypeAdded", "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("userTypeAdded", "Error writing document", e);
                        }
                    });

            CharSequence text = "You have been registered!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }
    }

    //cancels registration
    private void cancel() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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
