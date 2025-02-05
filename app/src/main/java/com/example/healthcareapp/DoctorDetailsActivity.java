package com.example.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {
    //dummy static data
    private String[][] doctorDetails1 = {
            //Name, Hospital, Mobile no., Experience, Rating, Consultation Fees
            {"Dr. Isabella Thomas", "Indraprastha Apollo", "9876543230", "14 years", "5.0", "700"},
            {"Dr. Henry Walker", "Nanavati Hospital", "9876543231", "8 years", "4.1", "350"},
            {"Dr. Charlotte Adams", "Ruby Hall Clinic", "9876543232", "7 years", "3.9", "300"},
            {"Dr. Liam Perez", "Global Hospital", "9876543233", "5 years", "3.4", "250"},
            {"Dr. Abigail Hall", "Manipal Northside", "9876543234", "3 years", "2.7", "150"}
    };
    private String[][] doctorDetails2 = {
            //Name, Hospital, Mobile no., Experience, Rating, Consultation Fees
            {"Dr. Emily Clark", "Columbia Asia Hospital", "9876543215", "12 years", "4.8", "600"},
            {"Dr. Michael Smith", "Manipal Hospital", "9876543216", "9 years", "4.3", "450"},
            {"Dr. Sarah Wilson", "Fortis Escorts", "9876543217", "7 years", "3.9", "350"},
            {"Dr. William Brown", "Artemis Hospital", "9876543218", "5 years", "3.6", "250"},
            {"Dr. Olivia Johnson", "BLK Hospital", "9876543219", "3 years", "2.8", "150"}
    };
    private String[][] doctorDetails3 = {
            //Name, Hospital, Mobile no., Experience, Rating, Consultation Fees
            {"Dr. David Lee", "Sir Ganga Ram Hospital", "9876543220", "14 years", "4.9", "700"},
            {"Dr. Emma Martinez", "Narayana Hospital", "9876543221", "11 years", "4.6", "550"},
            {"Dr. Daniel Rodriguez", "Paras Hospital", "9876543222", "9 years", "4.2", "450"},
            {"Dr. Sophia Garcia", "Yashoda Hospital", "9876543223", "6 years", "3.7", "350"},
            {"Dr. Matthew Hernandez", "Kailash Hospital", "9876543224", "4 years", "3.1", "250"}
    };
    private String[][] doctorDetails4 = {
            //Name, Hospital, Mobile no., Experience, Rating, Consultation Fees
            {"Dr. Isabella Martinez", "Metro Hospital", "9876543225", "16 years", "5.0", "800"},
            {"Dr. Ethan Brown", "Saket Hospital", "9876543226", "13 years", "4.7", "650"},
            {"Dr. Mia Wilson", "Indraprastha Hospital", "9876543227", "10 years", "4.4", "500"},
            {"Dr. Noah Johnson", "Safdarjung Hospital", "9876543228", "8 years", "4.1", "400"},
            {"Dr. Ava Lee", "Holy Family Hospital", "9876543229", "5 years", "3.8", "300"}
    };
    private String[][] doctorDetails5 = {
            //Name, Hospital, Mobile no., Experience, Rating, Consultation Fees
            {"Dr. Alexander Smith", "Lok Nayak Hospital", "9876543230", "18 years", "5.0", "900"},
            {"Dr. Mia Clark", "Ram Manohar Lohia Hospital", "9876543231", "15 years", "4.8", "750"},
            {"Dr. Ethan Wilson", "Deen Dayal Upadhyay Hospital", "9876543232", "12 years", "4.5", "600"},
            {"Dr. Isabella Johnson", "Guru Teg Bahadur Hospital", "9876543233", "9 years", "4.2", "450"},
            {"Dr. Noah Lee", "Dr. Baba Saheb Ambedkar Hospital", "9876543234", "6 years", "3.6", "350"}
    };


    TextView tv;
    Button btn;
    String[][] doctor_Details={};
    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv = findViewById(R.id.textViewDDTitle);
        btn = findViewById(R.id.buttonDDBack);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        tv.setText(title);

        if (title == null || title.isEmpty()) {
            Toast.makeText(this, "Invalid or empty title received!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (title.equals("Family Physicians")) {
            doctor_Details = doctorDetails1;
        } else if (title.equals("Dieticians")) {
            doctor_Details = doctorDetails2;
        } else if (title.equals("Dentists")) {
            doctor_Details = doctorDetails3;
        } else if (title.equals("Surgeons")) {
            doctor_Details = doctorDetails4;
        } else if (title.equals("Cardiologists")) {
            doctor_Details = doctorDetails5;
        } else {
            doctor_Details = new String[][]{}; // Empty array if no match
            Toast.makeText(this, "No matching title found!", Toast.LENGTH_SHORT).show();
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorDetailsActivity.this, ChannelDoctorActivity.class));
            }
        });

        list = new ArrayList<>();
        for (String[] doctorDetail : doctor_Details) {
            item = new HashMap<String, String>();
            item.put("line1", "Name: "+doctorDetail[0]);
            item.put("line2", "Hospital: "+doctorDetail[1]);
            item.put("line3", "No: " + doctorDetail[2]);
            item.put("line4", "Exp: " + doctorDetail[3]);
            item.put("line5", "Rating: " + doctorDetail[4]);
            item.put("line6", doctorDetail[5] + "LKR");
            list.add(item);
        }
        SimpleAdapter sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1", "line2", "line4", "line3", "line5", "line6"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e, R.id.line_f}
        );
        ListView listView = findViewById(R.id.listViewDD);
        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
                intent.putExtra("text5", title);
                intent.putExtra("text1", doctor_Details[i][0]);
                intent.putExtra("text2", doctor_Details[i][1]);
                intent.putExtra("text3", doctor_Details[i][2]);
                intent.putExtra("text4", doctor_Details[i][5]);
                startActivity(intent);
            }
        });




    }
}