package com.company.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Recipes extends AppCompatActivity {
    private DatabaseReference mDatabase, mDatabase2;

    private LinearLayout mainLayout;
    private FrameLayout frameLayout;
    private FrameLayout.LayoutParams params;
    private FragmentTransaction ft;

    private Button backButton;

    private boolean flag;
    private String sessionId;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        String category = getIntent().getStringExtra("Category");
        mDatabase = FirebaseDatabase.getInstance("https://recipeapp-1dcdf-default-rtdb.firebaseio.com/").getReference(); // You can put your database's path here...
        Intent intent = getIntent();
        sessionId = intent.getStringExtra("ID");
        mainLayout = findViewById(R.id.main_layout);

        if(getIntent().getStringExtra("Action").equals("Search")) {
            mDatabase2 = mDatabase.child("Recipe").child(category).child(getIntent().getStringExtra("Name"));
            flag = true;
        }
        else {
            mDatabase2 = mDatabase.child("Recipe").child(category);
            flag = false;
        }

        mDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Name, Image, Recipe;

                if(flag) {
                    Name = snapshot.child("Name").getValue().toString();
                    Image = snapshot.child("Image").getValue().toString();
                    Recipe = snapshot.child("Recipe").getValue().toString();

                    generateFragment(Name, Image, Recipe);
                }
                else {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Name = snap.child("Name").getValue().toString();     // Add "Name" Children to each food in Firebase.
                        Image = snap.child("Image").getValue().toString();   // Add "Image" Children to each food in Firebase, this field should contain Base64 encode of the image.
                        Recipe = snap.child("Recipe").getValue().toString(); // Add "Recipe" Children to each food in Firebase, to separate between lines in the recipe, add "\n".

                        generateFragment(Name, Image, Recipe);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Recipes.this, "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        backButton = findViewById(R.id.button5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String action = getIntent().getStringExtra("Action");
                Intent intent;

                if(action.equals("Search")) {
                    Intent i = new Intent(Recipes.this, Search.class);
                    i.putExtra("ID", sessionId);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i = new Intent(Recipes.this, Categories.class);
                    i.putExtra("ID", sessionId);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    public void generateFragment(String Name, String Image, String Recipe) {
        frameLayout = new FrameLayout(Recipes.this);
        frameLayout.setId(View.generateViewId());
        params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 64;
        params.bottomMargin = 64;
        frameLayout.setLayoutParams(params);

        mainLayout.addView(frameLayout, params);

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(frameLayout.getId(), CardFragment.newInstance(Name, Image, Recipe)).commit();
    }
}