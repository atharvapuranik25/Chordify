package com.example.chordify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText LogEmail;
    EditText LogPass;
    Button LogButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LogEmail = findViewById(R.id.editTextTextEmailAddress);
        LogPass = findViewById(R.id.editTextTextPassword);
        LogButton = findViewById(R.id.login2);

        mAuth = FirebaseAuth.getInstance();

        LogButton.setOnClickListener(view -> {
            loginUser();
        });
    }

    private void loginUser() {
        String email = LogEmail.getText().toString();
        String password = LogPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            LogEmail.setError("Email cannot be empty");
            LogEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)){
            LogPass.setError("Password cannot be empty");
            LogPass.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void backHome(View v){
        startActivity(new Intent(LoginActivity.this, GoogleSignInActivity.class));
    }
}