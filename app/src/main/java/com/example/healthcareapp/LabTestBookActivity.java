package com.example.healthcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LabTestBookActivity extends AppCompatActivity {

    EditText etName, etContact, etPincode, etAddress;
    Button btnBooking, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab_test_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.editTextLTBName);
        etAddress = findViewById(R.id.editTextLTBAddress);
        etPincode = findViewById(R.id.editTextLTBPincode);
        etContact = findViewById(R.id.editTextLTBContact);
        btnBooking = findViewById(R.id.buttonLTBBooking);
        btnBack = findViewById(R.id.buttonLTBBack);

        Intent intent = getIntent();
        String price = intent.getStringExtra("price");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        if (price == null || date == null || time == null) {
            Toast.makeText(this, "Missing booking details. Please try again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Validate and process price
        String[] priceSplit = price.split(":");
        if (priceSplit.length < 2) {
            Toast.makeText(this, "Invalid price format.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String priceValue = priceSplit[1].trim(); // Extract price value

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("sharedPref", MODE_PRIVATE);
                String username = sp.getString("username", "").toString();

                if (username.isEmpty() || etName.getText().toString().isEmpty() ||
                        etAddress.getText().toString().isEmpty() || etContact.getText().toString().isEmpty() ||
                        etPincode.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all the details.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                    db.addOrder(username,
                            etName.getText().toString(),
                            etAddress.getText().toString(),
                            etContact.getText().toString(),
                            etPincode.getText().toString(),
                            date,
                            time,
                            Float.parseFloat(priceValue),
                            "lab");
                    db.removeCart(username, "lab");
                    Toast.makeText(getApplicationContext(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LabTestBookActivity.this, HomeActivity.class));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed to place order: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestBookActivity.this, CartLabActivity.class));
            }
        });

    }
}