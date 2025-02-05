package com.example.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HealthTipsDetailsActivity extends AppCompatActivity {

    TextView tv1;
    ImageView iv1;
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_health_tips_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv1 = findViewById(R.id.textViewHADTitle);
        iv1 = findViewById(R.id.imageViewHAD);
        btnback = findViewById(R.id.buttonHADBack);

        Intent intent = getIntent();
        tv1.setText(intent.getStringExtra("text1"));

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int res_img = bundle.getInt("text2");
            iv1.setImageResource(res_img);
        }

        btnback.setOnClickListener(v -> {
            Intent i = new Intent(HealthTipsDetailsActivity.this, HealthTipsActivity.class);
            startActivity(i);
        });
    }
}