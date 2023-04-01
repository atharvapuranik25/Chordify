package com.example.chordify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    Button logout;
    String userID;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Here
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.create:
                        startActivity(new Intent(getApplicationContext(),
                                ProgActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.learn:
                        startActivity(new Intent(getApplicationContext(),
                                LearnActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),
                                ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
        //Here


//        username = findViewById(R.id.username);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

//        userID = mAuth.getCurrentUser().getUid();
//
//        DocumentReference documentReference = mStore.collection("users").document(userID);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                username.setText(value.getString("username"));
//            }
//        });
    }

    public void learnMusicTheory(View v){
        startActivity(new Intent(HomeActivity.this, LearnActivity.class));
    }

    public void generateChordProgression(View v){
        startActivity(new Intent(HomeActivity.this, ProgActivity.class));
    }
}