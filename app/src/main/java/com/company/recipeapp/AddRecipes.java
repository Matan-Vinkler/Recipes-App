package com.company.recipeapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Base64;

class Food {
    public String Image;
    public String Name;
    public String Recipe;

    public Food() {

    }

    public Food(String Image, String Name, String Recipe) {
        this.Image = Image;
        this.Name = Name;
        this.Recipe = Recipe;
    }
}

public class AddRecipes extends AppCompatActivity {
    EditText name, category, recipe, recipe2, imageText;
    ImageView image;
    Button add, back;
    DatabaseReference dbr;

    private String ImageBase64;
    private final String DATABASE_URL = "https://recipeapp-1dcdf-default-rtdb.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipes);
        name = findViewById(R.id.name);
        recipe = findViewById(R.id.recipe);
        recipe2 = findViewById(R.id.recipe2);
        category = findViewById(R.id.category);
        imageText = findViewById(R.id.image);
        image = findViewById(R.id.imageView);
        dbr = FirebaseDatabase.getInstance().getReferenceFromUrl(DATABASE_URL);

        add = findViewById(R.id.button);
        back = findViewById(R.id.button5);
        imageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] categorys = new String[]{"cookies", "Homemade food", "Soups", "Asian food"};
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddRecipes.this);
                alertDialog.setTitle("Choose Category");
                alertDialog.setSingleChoiceItems(categorys, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        category.setText(categorys[i]);
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog mDialog = alertDialog.create();
                mDialog.show();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    validateFields();

                    String recipe_data = recipe.getText().toString() + ";" + recipe2.getText().toString();

                    Food food = new Food(ImageBase64, name.getText().toString(), recipe_data);
                    dbr.child("Recipe").child(category.getText().toString()).child(food.Name).setValue(food);

                    Intent intent = new Intent(AddRecipes.this, Admin.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception e) {
                    Toast.makeText(AddRecipes.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void validateFields() throws Exception {
        if(recipe.getText().toString().contains(";") || recipe2.getText().toString().contains(";")) {
            throw new Exception("Those fields cannot contain ';'.");
        }

        if(name.getText().toString().trim().equals("")) {
            throw new Exception("All fields are required.");
        }

        if(recipe.getText().toString().trim().equals("")) {
            throw new Exception("All fields are required.");
        }

        if(recipe2.getText().toString().trim().equals("")) {
            throw new Exception("All fields are required.");
        }

        if(category.getText().toString().trim().equals("")) {
            throw new Exception("All fields are required.");
        }

        if(imageText.getText().toString().trim().equals("")) {
            throw new Exception("All fields are required.");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(resultCode == RESULT_OK){
            Uri selectedImage = imageReturnedIntent.getData();
            try {
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                ImageBase64 = bitmapToBase64(bitmap);
                imageText.setText("Selected");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private String bitmapToBase64(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return base64;
    }
}
