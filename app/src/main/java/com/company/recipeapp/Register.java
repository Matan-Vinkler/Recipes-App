package com.company.recipeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    EditText email, pass, fullName, pass2;
    Button register, back;
    DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        fullName = findViewById(R.id.name);
        register = findViewById(R.id.button);
        dbr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://recipeapp-1dcdf-default-rtdb.firebaseio.com/");
        back = findViewById(R.id.button5);
        pass2 = findViewById(R.id.pass2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String name = fullName.getText().toString().trim();
                String password2 = pass2.getText().toString().trim();
                if(emailValidator(em) == false)
                {
                    email.setError("Please Enter valid Email address !");
                    return;
                }

                if(TextUtils.isEmpty(password) || password.length() < 6)
                {
                    pass.setError("Please Enter at least 6 character");
                    return;
                }
                if(password.equals(password2) == false)
                {
                    pass2.setError("Please Enter password verification");
                    return;
                }
                if(TextUtils.isEmpty(name) || name.contains(" ") == false)
                {
                    fullName.setError("Please Enter Full Name");
                    return;
                }
                em = em.replace(".", ",");

                String finalEm = em;
                dbr.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(finalEm))
                        {
                            Toast.makeText(Register.this, "This Email already registered", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            dbr.child("Users").child(finalEm).child("name").setValue(name);
                            dbr.child("Users").child(finalEm).child("password").setValue(password);
                            Toast.makeText(Register.this, "Registration succeeded", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
    public boolean emailValidator(String email) {

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            return false;
        }
    }
}