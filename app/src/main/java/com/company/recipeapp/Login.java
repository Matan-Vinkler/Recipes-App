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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText email, pass;
    Button login, back;
    DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.button);
        dbr = FirebaseDatabase.getInstance().getReferenceFromUrl("https://recipeapp-1dcdf-default-rtdb.firebaseio.com/");
        back = findViewById(R.id.button5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString().trim();
                String password = pass.getText().toString().trim();

                if(em.equals("admin@gmail.com") && password.equals("J5k>=r"))
                {
                    startActivity(new Intent(getApplicationContext(), Admin.class));
                    finish();
                }
                else
                {
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
                    em = em.replace(".", ",");

                    String finalEm = em;
                    dbr.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(finalEm))
                            {
                                if(snapshot.child(finalEm).child("password").getValue(String.class).equals(password))
                                {
                                    startActivity(new Intent(getApplicationContext(), Menu.class));
                                    finish();
                                }
                                else
                                {
                                    pass.setError("Wrong Password");
                                    return;
                                }
                            }
                            else
                            {
                                email.setError("Wrong Email");
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


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