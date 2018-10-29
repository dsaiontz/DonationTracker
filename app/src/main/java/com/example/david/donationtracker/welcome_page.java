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
        Button LoginButton = (Button) findViewById(R.id.WelcomePageLoginButton);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        Button RegisterButton = (Button) findViewById(R.id.WelcomePageRegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        Button skipLoginButton = (Button) findViewById(R.id.SkipLoginButton);
        skipLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(welcome_page.this, MainPage.class));
                finish();
            }
        });
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
