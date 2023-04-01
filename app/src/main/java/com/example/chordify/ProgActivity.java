package com.example.chordify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProgActivity extends AppCompatActivity {

    String userID;

    FirebaseAuth mAuth;
    FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prog);

        //Here
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.create);

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

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        ImageButton addToFavourite = findViewById(R.id.add_to_fav);

        ImageButton generate = findViewById(R.id.generate);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Spinner spinMode = (Spinner) findViewById(R.id.mode);
                Spinner spinKey = (Spinner) findViewById(R.id.key);
                TextView chord_prog = (TextView) findViewById(R.id.cp);

                String newMode = String.valueOf(spinMode.getSelectedItem());
                String newKey = String.valueOf(spinKey.getSelectedItem());

                String major = "Major";
                String minor = "Minor";

                int V = 8;
                ArrayList<ArrayList<Integer> > graph = new ArrayList<>(V);

                if(newMode.equals(major)) {
                    graph = createMajorGraph();
                } else if(newMode.equals(minor)) {
                    graph = createMinorGraph();
                }

                ArrayList<Integer> progression;

                progression = getProgression(graph);

                ArrayList<String> scale;
                scale = getScale(newMode, newKey);

                int c1 = progression.get(0);
                int c2 = progression.get(1);
                int c3 = progression.get(2);
                int c4 = progression.get(3);

                String ch1 = scale.get(c1);
                String ch2 = scale.get(c2);
                String ch3 = scale.get(c3);
                String ch4 = scale.get(c4);

                String cp = ch1 + " - " + ch2 + " - " + ch3 + " - " + ch4;

                chord_prog.setText(cp);
            }

            public ArrayList<String> getScale(String mode, String key) {
                ArrayList<String> scale = new ArrayList<>(8);
                ArrayList<String> chromatic = new ArrayList<>(12);
                chromatic.add("C");
                chromatic.add("C#");
                chromatic.add("D");
                chromatic.add("D#");
                chromatic.add("E");
                chromatic.add("F");
                chromatic.add("F#");
                chromatic.add("G");
                chromatic.add("G#");
                chromatic.add("A");
                chromatic.add("A#");
                chromatic.add("B");

                int index = chromatic.indexOf(key);

                String major = "Major";
                String minor = "Minor";

                if (mode.equals(major)) {
                    scale.add(chromatic.get(index % 12));
                    scale.add(chromatic.get((index+2) % 12) + "m");
                    scale.add(chromatic.get((index+4) % 12) + "m");
                    scale.add(chromatic.get((index+5) % 12));
                    scale.add(chromatic.get((index+7) % 12));
                    scale.add(chromatic.get((index+9) % 12) + "m");
                    scale.add(chromatic.get((index+11) % 12) + "dim");
                } else if (mode.equals(minor)) {
                    scale.add(chromatic.get(index % 12) + "m");
                    scale.add(chromatic.get((index+2) % 12) + "dim");
                    scale.add(chromatic.get((index+3) % 12));
                    scale.add(chromatic.get((index+5) % 12) + "m");
                    scale.add(chromatic.get((index+7) % 12) + "m");
                    scale.add(chromatic.get((index+8) % 12));
                    scale.add(chromatic.get((index+10) % 12));
                }

                return scale;
            }

            public void addEdge(ArrayList<ArrayList<Integer>> adj, int u, int v) {
                adj.get(u).add(v);
                adj.get(v).add(u);
            }

            public ArrayList<Integer> addChord(ArrayList<ArrayList<Integer>> graph) {

                ArrayList<Integer> progression = new ArrayList<>(4);
                progression.add(0);
                int i = 0;

                while(progression.size() != 4) {
                    int size = graph.get(i).size();
                    Random rand = new Random();
                    int rand_int = rand.nextInt(size);
                    i = graph.get(i).get(rand_int);
                    progression.add(i);
                }

                return(progression);
            }

            public ArrayList<Integer> getProgression(ArrayList<ArrayList<Integer>> adj) {

                ArrayList<Integer> progression;

                progression = addChord(adj);

                return(progression);
            }

            public ArrayList<ArrayList<Integer>> createMajorGraph() {
                // Creating a graph with 7 vertices
                int V = 8;
                ArrayList<ArrayList<Integer> > adj = new ArrayList<>(V);

                for (int i = 0; i < V; i++)
                    adj.add(new ArrayList<>());

                // Adding edges one by one
                addEdge(adj, 0, 2);
                addEdge(adj, 0, 3);
                addEdge(adj, 0, 4);
                addEdge(adj, 0, 5);
                addEdge(adj, 1, 0);
                addEdge(adj, 1, 4);
                addEdge(adj, 2, 1);
                addEdge(adj, 2, 3);
                addEdge(adj, 2, 5);
                addEdge(adj, 3, 0);
                addEdge(adj, 3, 1);
                addEdge(adj, 3, 4);
                addEdge(adj, 4, 0);
                addEdge(adj, 4, 5);
                addEdge(adj, 5, 1);
                addEdge(adj, 5, 3);

                return(adj);
            }

            public ArrayList<ArrayList<Integer>>  createMinorGraph() {
                // Creating a graph with 7 vertices
                int V = 8;
                ArrayList<ArrayList<Integer> > adj = new ArrayList<>(V);

                for (int i = 0; i < V; i++)
                    adj.add(new ArrayList<>());

                // Adding edges one by one
                addEdge(adj, 0, 2);
                addEdge(adj, 0, 3);
                addEdge(adj, 0, 4);
                addEdge(adj, 0, 5);
                addEdge(adj, 0, 6);


                addEdge(adj, 2, 3);
                addEdge(adj, 2, 5);
                addEdge(adj, 2, 6);
                addEdge(adj, 3, 0);

                addEdge(adj, 3, 4);
                addEdge(adj, 4, 0);
                addEdge(adj, 4, 5);

                addEdge(adj, 5, 2);
                addEdge(adj, 5, 3);
                addEdge(adj, 5, 6);
                addEdge(adj, 6, 2);
                addEdge(adj, 6, 5);

                return(adj);
            }

        });
    }

    public void backHome(View v) {
        startActivity(new Intent(ProgActivity.this, HomeActivity.class));
    }

//    public void goToFavAct(View v) {
//        startActivity(new Intent(ProgActivity.this, FavouritesActivity.class));
//    }

    public void favourite(View v) {
        Spinner spinMode = (Spinner) findViewById(R.id.mode);
        Spinner spinKey = (Spinner) findViewById(R.id.key);
        TextView chord_prog = (TextView) findViewById(R.id.cp);

        String newMode = String.valueOf(spinMode.getSelectedItem());
        String newKey = String.valueOf(spinKey.getSelectedItem());
        String progression = chord_prog.getText().toString();

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = mStore.collection("users").document(userID).collection("progressions").document();
        Map<String,Object> user = new HashMap<>();
        user.put("mode", newMode);
        user.put("key", newKey);
        user.put("progression", progression);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ProgActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }

}