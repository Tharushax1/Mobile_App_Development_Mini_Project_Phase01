package com.example.healthcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BuyMedicineDetailsActivity extends AppCompatActivity {

    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
    Button btnAddToCart, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buy_medicine_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvPackageName = findViewById(R.id.textViewBMDTitle);
        edDetails = findViewById(R.id.editTextBMDMultiLine);
        edDetails.setKeyListener(null);
        tvTotalCost = findViewById(R.id.textViewBMDCost);
        btnAddToCart = findViewById(R.id.buttonBMDGoToCart);
        btnBack = findViewById(R.id.buttonBMDBack);

        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost: " + intent.getStringExtra("text3") + "LKR");

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class));
        });

        btnAddToCart.setOnClickListener(view ->{
            SharedPreferences sp = getSharedPreferences("sharedPref", MODE_PRIVATE);
            String username = sp.getString("username", "").toString();
            String product = tvPackageName.getText().toString();
            float price = Float.parseFloat(intent.getStringExtra("text3").toString());

            Database db = new Database(getApplicationContext(), "healthcare", null, 1);

            if (db.checkCart(username,product)==1) {
                Toast.makeText(getApplicationContext(), "Item already in the cart", Toast.LENGTH_SHORT).show();
            } else {
                db.addCart(username,product,price,"medicine");
                Toast.makeText(getApplicationContext(), "Item added to the cart", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class));
            }

        });
    }
}