package com.example.david.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class welcome_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
<<<<<<< HEAD
        Button LoginButton = (Button) findViewById(R.id.LogInButtonThing);
=======
        Button LoginButton = (Button) findViewById(R.id.WelcomePageLoginButton);
>>>>>>> Spencer
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
<<<<<<< HEAD
=======
        Button RegisterButton = (Button) findViewById(R.id.WelcomePageRegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
>>>>>>> Spencer
    }

    private void logout() {
        startActivity(new Intent(welcome_page.this, LoginActivity.class));
        finish();
    }
<<<<<<< HEAD
=======

    private void register() {
        startActivity(new Intent(welcome_page.this, RegisterActivity.class));
        finish();
    }
>>>>>>> Spencer
}
