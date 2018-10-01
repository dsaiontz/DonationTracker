package com.example.david.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private int minPasswordLength = 6;

    // UI References
    private EditText email;
    private EditText pass1;
    private EditText pass2;

    // Regex Patterns
    private Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText) findViewById(R.id.RegisterPageEmail);
        pass1 = (EditText) findViewById(R.id.RegisterPassword1);
        pass2 = (EditText) findViewById(R.id.RegisterPassword2);

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
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailText);
        if (!matcher.find()) {
            email.setError("Invalid email address");
            email.requestFocus();
        } else if (!passText1.equals(passText2)) {
            pass1.setError("Passwords need to match");
            pass2.setError("Passwords need to match");
            pass1.requestFocus();
        } else if (pass1.length() < minPasswordLength) {
            pass1.setError("Password must be at least " + minPasswordLength + " characters long");
            pass1.requestFocus();
        } else {

        }
    }

    private void cancel() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }
}
