package com.example.chordify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FavouritesActivity extends AppCompatActivity {


    private FirestoreRecyclerAdapter adapter;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        //Here
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                HomeActivity.class));
                        overridePendingTransition(0,0);
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
                        return true;
                }

                return false;
            }
        });
        //Here

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        RecyclerView favList = findViewById(R.id.firestore_recycler);

        userID = mAuth.getCurrentUser().getUid();

        //Query
        Query query = firebaseFirestore.collection("users").document(userID).collection("progressions");

        //Recycler Options
        FirestoreRecyclerOptions<FavouritesRecycler> options = new FirestoreRecyclerOptions.Builder<FavouritesRecycler>()
                .setQuery(query, FavouritesRecycler.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<FavouritesRecycler, ProgViewHolder>(options) {
            @NonNull
            @Override
            public ProgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progression_card, parent, false);
                return new ProgViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProgViewHolder holder, int position, @NonNull FavouritesRecycler model) {
                holder.prog_mode.setText(model.getMode());
                holder.prog_key.setText(model.getKey());
                holder.prog.setText(model.getProgression());
            }
        };

        favList.setHasFixedSize(true);
        favList.setLayoutManager(new LinearLayoutManager(this));
        favList.setAdapter(adapter);
    }

    private static class ProgViewHolder extends RecyclerView.ViewHolder {

        private TextView prog_mode;
        private TextView prog_key;
        private TextView prog;

        public ProgViewHolder(@NonNull View itemView) {
            super(itemView);

            prog_mode = itemView.findViewById(R.id.mode);
            prog_key = itemView.findViewById(R.id.key);
            prog = itemView.findViewById(R.id.progression);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }



    public void goBack(View v) {
        startActivity(new Intent(FavouritesActivity.this, ProfileActivity.class));
    }
}





