package com.company.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class Admin extends AppCompatActivity {
    Button add, watch,Search,  logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        add = findViewById(R.id.button2);
        Search = findViewById(R.id.button1);
        watch = findViewById(R.id.button);
        logOut = findViewById(R.id.button5);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin.this, Search.class);
                i.putExtra("ID", "2");
                startActivity(i);
                finish();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Admin.this, Categories.class);
                i.putExtra("ID", "2");
                startActivity(i);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddRecipes.class));
                finish();
            }
        });
    }
}