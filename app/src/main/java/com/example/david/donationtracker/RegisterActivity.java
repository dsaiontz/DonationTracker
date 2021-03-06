package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Activity used for registering users
 */
public class RegisterActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    // UI References
    private EditText email;
    private EditText pass1;
    private EditText pass2;

    private Spinner userSpinner;
    private Object[] registerSpinnerOptions;

    private Spinner locationSpinner;

    // --Commented out by Inspection (11/16/18 10:30 AM):private Credentials creds;

    // Regex Patterns
    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    private Object[] registerLocationOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerSpinnerOptions = new Object[UserType.values().length+1];
        registerSpinnerOptions[0] = "PLEASE SELECT USER TYPE";
        int k = 1;
        for (UserType i : UserType.values()) {
            registerSpinnerOptions[k] = i;
            k++;
        }

        List<Location> locList = Locations.getAllLocations();

        registerLocationOptions = new Object[locList.size() + 1];
        registerLocationOptions[0] = "PLEASE SELECT LOCATION";
        int m = 1;
        for (Location i : Locations.getAllLocations()) {
            registerLocationOptions[m] = i.getName();
            m++;
        }

        email = findViewById(R.id.RegisterPageEmail);
        pass1 = findViewById(R.id.RegisterPassword1);
        pass2 = findViewById(R.id.RegisterPassword2);

        initializeSpinners();

        Button registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        Button cancelButton = findViewById(R.id.RegisterPageCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

    }

    private void initializeSpinners() {
        userSpinner = findViewById(R.id.userSpinner);
        userSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<DonationActivity> adapterNext = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, registerSpinnerOptions);
        adapterNext.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapterNext);
        userSpinner.setOnItemSelectedListener(this);


        locationSpinner = findViewById(R.id.locationSpinner);
        locationSpinner.setOnItemSelectedListener(this);
        SpinnerAdapter adapterNew = new ArrayAdapter
                (this, android.R.layout.simple_spinner_item, registerLocationOptions);
        adapterNext.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapterNew);
        locationSpinner.setOnItemSelectedListener(this);
    }


    //goes to login activity, registers user
    private void register() {
        Editable emailEdit = email.getText();
        Editable pass1Edit = pass1.getText();
        Editable pass2Edit = pass2.getText();
        final String emailText = emailEdit.toString();
        String passText1 = pass1Edit.toString();
        String passText2 = pass2Edit.toString();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailText);
        int minPasswordLength = 6;
        Object selected = userSpinner.getSelectedItem();
        if (selected.equals(registerSpinnerOptions[0])) {
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
        } else {
            final Context context = getApplicationContext();

            //ADDING USER TO FIREBASE

            Task<AuthResult> addUserTask = mAuth
                    .createUserWithEmailAndPassword(emailText, passText1);
            addUserTask.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign up success, update UI with the signed-in user's information
                                Log.d("creationSuccess", "createUserWithEmail:success");

                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("userType", ((UserType) userSpinner.getSelectedItem())
                                        .getUserType());
                                userInfo.put("location", locationSpinner.getSelectedItem());

                                CollectionReference userColl = db.collection("users");
                                DocumentReference docuRef = userColl.document(emailText);
                                Task<Void> userInfoTask = docuRef.set(userInfo);
                                userInfoTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("userTypeAdded",
                                                        "DocumentSnapshot " +
                                                                "successfully written!");
                                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                                mAuth.signOut();
                                                CharSequence text = "You have been registered!";
                                                int duration = Toast.LENGTH_SHORT;
                                                Toast toast = Toast.makeText(context,
                                                        text, duration);
                                                toast.show();
                                                startActivity(new Intent(
                                                        RegisterActivity.this,
                                                        LoginActivity.class));
                                                finish();
                                            }
                                        });
                                        userInfoTask.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("userTypeAdded", "Error writing document", e);
                                            }
                                        });

                            } else {
                                // If sign up fails, display a message to the user.
                                Log.w("creationSuccess", "createUserWithEmail:failure",
                                        task.getException());
                                Toast toast = Toast.makeText(context, "Email is already " +
                                                "associated with account",
                                        Toast.LENGTH_SHORT);
                                toast.show();
                                startActivity(new Intent(RegisterActivity.this,
                                        RegisterActivity.class));
                                finish();
                            }
                        }
                    });
        }
    }

    //cancels registration
    private void cancel() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
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
