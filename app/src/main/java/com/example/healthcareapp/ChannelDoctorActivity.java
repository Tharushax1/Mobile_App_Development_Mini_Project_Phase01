package com.example.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChannelDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_channel_doctor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CardView back = findViewById(R.id.cardCDBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChannelDoctorActivity.this, HomeActivity.class));
            }
        });
        CardView familyPhysician = findViewById(R.id.cardCDFamilyPhysician);
        familyPhysician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = (new Intent(ChannelDoctorActivity.this, DoctorDetailsActivity.class));
                it.putExtra("title", "Family Physicians");
                startActivity(it);
            }
        });
        CardView dietician = findViewById(R.id.cardCDDietician);
        dietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = (new Intent(ChannelDoctorActivity.this, DoctorDetailsActivity.class));
                it.putExtra("title", "Dieticians");
                startActivity(it);
            }
        });
        CardView dentist = findViewById(R.id.cardCDDentist);
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = (new Intent(ChannelDoctorActivity.this, DoctorDetailsActivity.class));
                it.putExtra("title", "Dentists");
                startActivity(it);
            }
        });
        CardView surgeon = findViewById(R.id.cardCDSurgeon);
        surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = (new Intent(ChannelDoctorActivity.this, DoctorDetailsActivity.class));
                it.putExtra("title", "Surgeons");
                startActivity(it);
            }
        });
        CardView cardiologist = findViewById(R.id.cardCDCardiologist);
        cardiologist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = (new Intent(ChannelDoctorActivity.this, DoctorDetailsActivity.class));
                it.putExtra("title", "Cardiologists");
                startActivity(it);
            }
        });

    }
}