package com.example.healthcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LabTestDetailsActivity extends AppCompatActivity {

    TextView tvPackageName, tvTotalCost;
    EditText edPackageDetails;
    Button btnAddToCart, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab_test_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvPackageName = findViewById(R.id.textViewLTDTitle);
        tvTotalCost = findViewById(R.id.textViewLTDCost);
        edPackageDetails = findViewById(R.id.editTextTextMultiLine);
        btnAddToCart = findViewById(R.id.buttonLTDGoToCart);
        btnBack = findViewById(R.id.buttonLTDBack);

        edPackageDetails.setKeyListener(null);

        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("package"));
        tvTotalCost.setText("Total Cost: " + intent.getStringExtra("cost") + "LKR");
        edPackageDetails.setText(intent.getStringExtra("details"));

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class));
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("sharedPref", MODE_PRIVATE);
                String username = sp.getString("username", "").toString();
                String product = tvPackageName.getText().toString();
                float price = Float.parseFloat(intent.getStringExtra("cost").toString());

                Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                if (db.checkCart(username,product)==1) {
                    Toast.makeText(getApplicationContext(), "Item already in the cart", Toast.LENGTH_SHORT).show();
                } else {
                    db.addCart(username,product,price,"lab");
                    Toast.makeText(getApplicationContext(), "Item added to the cart", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class));
                }
            }
        });

    }
}