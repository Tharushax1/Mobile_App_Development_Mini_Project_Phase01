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

public class BuyMedicineBookActivity extends AppCompatActivity {

    EditText etName, etContact, etPincode, etAddress;
    Button btnBooking, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buy_medicine_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.editTextBMBName);
        etAddress = findViewById(R.id.editTextBMBAddress);
        etPincode = findViewById(R.id.editTextBMBPincode);
        etContact = findViewById(R.id.editTextBMBContact);
        btnBooking = findViewById(R.id.buttonBMBBooking);
        btnBack = findViewById(R.id.buttonBMBBack);

        Intent intent = getIntent();
        String price = intent.getStringExtra("price");
        String date = intent.getStringExtra("date");

        if (price == null || date == null) {
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
                            "",
                            Float.parseFloat(priceValue),
                            "medicine");
                    db.removeCart(username, "medicine");
                    Toast.makeText(getApplicationContext(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(BuyMedicineBookActivity.this, HomeActivity.class));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed to place order: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineBookActivity.this, CartBuyMedicineActivity.class));
            }
        });


    }
}