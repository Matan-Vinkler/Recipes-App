package com.company.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Search extends AppCompatActivity {
    private EditText edtName;
    private Spinner spinCategory;
    private Button btnSearch, back;

    private String[] categories = {"Soups", "cookies", "Asian food", "Homemade food"};
    private String selectedCategory;
    private String selectedName;
    private String sessionId;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mDatabase = FirebaseDatabase.getInstance("https://recipeapp-1dcdf-default-rtdb.firebaseio.com/").getReference();

        edtName = findViewById(R.id.edt_name);
        spinCategory = findViewById(R.id.spin_category);
        btnSearch = findViewById(R.id.btn_search);
        Intent intent = getIntent();
        //dbr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smart-work-dfc1f-default-rtdb.firebaseio.com/");
        sessionId = intent.getStringExtra("ID");

        back = findViewById(R.id.button5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sessionId.equals("1"))
                {
                    startActivity(new Intent(getApplicationContext(), Menu.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), Admin.class));
                    finish();
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(Search.this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(adapter);

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categories[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedName = edtName.getText().toString();
                if (selectedName == "") {
                    Toast.makeText(Search.this, "Invalid Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                mDatabase.child("Recipe").child(selectedCategory).child(selectedName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        try {
                            String Name = snapshot.child("Name").getValue().toString();
                            String Image = snapshot.child("Image").getValue().toString();
                            String Recipe = snapshot.child("Recipe").getValue().toString();

                            Intent intent = new Intent(Search.this, Recipes.class);
                            intent.putExtra("Action", "Search");
                            intent.putExtra("Name", Name);
                            intent.putExtra("Category", selectedCategory);
                            intent.putExtra("ID", sessionId);

                            startActivity(intent);
                        }
                        catch (Exception e) {
                            Toast.makeText(Search.this, "Not Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}