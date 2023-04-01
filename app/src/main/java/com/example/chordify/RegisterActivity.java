package com.example.chordify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText RegEmail;
    EditText RegPass;
    EditText RegUser;
    Button RegButton;

    String userID;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegEmail = findViewById(R.id.editTextTextEmailAddress2);
        RegPass = findViewById(R.id.editTextTextPassword2);
        RegUser = findViewById(R.id.editTextTextPersonName);
        RegButton = findViewById(R.id.register3);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        RegButton.setOnClickListener(view ->{
            createUser();
        });
    }

    private void createUser() {
        String email = RegEmail.getText().toString();
        String password = RegPass.getText().toString();
        String username = RegUser.getText().toString();

        if (TextUtils.isEmpty(email)){
            RegEmail.setError("Email cannot be empty");
            RegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)){
            RegPass.setError("Password cannot be empty");
            RegPass.requestFocus();
        } else if (TextUtils.isEmpty(username)){
            RegUser.setError("Username cannot be empty");
            RegUser.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = mStore.collection("users").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("username", username);
                        user.put("email", email);
                        user.put("chapter", 0);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RegisterActivity.this, "User profile created for" + userID, Toast.LENGTH_SHORT).show();
                            }
                        });
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



    public void backHome(View v){
        startActivity(new Intent(RegisterActivity.this, GoogleSignInActivity.class));
    }
}