package com.example.david.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;

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

    private Credentials creds;

    // Regex Patterns
    private Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        creds = new Credentials();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerSpinnerOptions = new Object[UserType.values().length+1];
        registerSpinnerOptions[0] = (Object) "PLEASE SELECT USER TYPE";
        int k = 1;
        for (UserType i : UserType.values()) {
            registerSpinnerOptions[k++] = i;
        }

        email = (EditText) findViewById(R.id.RegisterPageEmail);
        pass1 = (EditText) findViewById(R.id.RegisterPassword1);
        pass2 = (EditText) findViewById(R.id.RegisterPassword2);

        userSpinner = (Spinner) findViewById(R.id.userSpinner);
        userSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<UserType> adapterNext = new ArrayAdapter(this, android.R.layout.simple_spinner_item, registerSpinnerOptions);
        adapterNext.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapterNext);
        userSpinner.setOnItemSelectedListener(this);


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
        } else {
            creds.add(new User(emailText, passText1, (UserType) userSpinner.getSelectedItem()));
            Context context = getApplicationContext();
            CharSequence text = "You have been registered!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

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
