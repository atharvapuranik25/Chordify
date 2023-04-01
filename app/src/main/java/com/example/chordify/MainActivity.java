package com.example.chordify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        final Runnable r = new Runnable() {
            public void run() {
                if(user != null){
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), GoogleSignInActivity.class));
                }
            }
        };

        final Handler handler = new Handler();
        handler.postDelayed(r,2000);
    }
}