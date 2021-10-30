package com.company.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button contact, watch, logOut, Search, aboutApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        contact = findViewById(R.id.button2);
        watch = findViewById(R.id.button);
        logOut = findViewById(R.id.button5);
        aboutApp = findViewById(R.id.button3);
        Search = findViewById(R.id.button1);

        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), about_app.class));
                finish();
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menu.this, Search.class);
                i.putExtra("ID", "1");
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
                Intent i = new Intent(Menu.this, Categories.class);
                i.putExtra("ID", "1");
                startActivity(i);
                finish();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Contact_Us.class));
                finish();
            }
        });
    }
}