package com.company.recipeapp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Categories extends AppCompatActivity {
    private ImageButton btnSoup;
    private ImageButton btnCookies;
    private ImageButton btnAsianFood;
    private ImageButton btnHomeFood;
    private Button btnSearch, back;
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        btnSoup = findViewById(R.id.btn_soup);
        btnCookies = findViewById(R.id.btn_cookies);
        btnAsianFood = findViewById(R.id.btn_asianfood);
        btnHomeFood = findViewById(R.id.btn_homefood);

        back = findViewById(R.id.button5);
        Intent intent = getIntent();
        sessionId = intent.getStringExtra("ID");

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

        btnSoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Recipes.class);
                intent.putExtra("Action", "Choose");
                intent.putExtra("Category", "Soups");
                intent.putExtra("ID", sessionId);

                startActivity(intent);
            }
        });

        btnCookies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Recipes.class);
                intent.putExtra("Action", "Choose");
                intent.putExtra("Category", "cookies");
                intent.putExtra("ID", sessionId);

                startActivity(intent);
            }
        });

        btnAsianFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Recipes.class);
                intent.putExtra("Action", "Choose");
                intent.putExtra("Category", "Asian food");
                intent.putExtra("ID", sessionId);

                startActivity(intent);
            }
        });

        btnHomeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, Recipes.class);
                intent.putExtra("Action", "Choose");
                intent.putExtra("Category", "Homemade food");
                intent.putExtra("ID", sessionId);

                startActivity(intent);
            }
        });


    }
}