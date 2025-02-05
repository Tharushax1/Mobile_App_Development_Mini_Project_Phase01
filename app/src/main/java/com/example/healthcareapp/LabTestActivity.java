package com.example.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity {


    //"..", add another of this if a error occurs
    private String[][] packages = {
            {"Package 1: Full Body Checkup", " ", " ", " ", " ", "999"},
            {"Package 2: Blood Glucose Fasting", " ", " ", " ", " ", "299"},
            {"Package 3: COVID-19 Antibody - IgG", " ", " ", " ", " ", "899"},
            {"Package 4: Thyroid Check", " ", " ", " ", " ", "499"},
            {"Package 5: Immunity Check", " ", " ", " ", " ", "699"}
    };

    private String[] package_details = {
            "Blood Glucose Fasting\n" +
                    "  Complete Hemogram\n" +
                    "  HbA1c\n" +
                    "  Iron Studies\n" +
                    "  Kidney Function Test\n" +
                    "  LDH Lactate Dehydrogenase, Serum\n" +
                    "  Lipid Profile\n" +
                    "  Liver Function Test\n",

            "Blood Glucose Fasting",
            "COVID-19 Antibody - IgG",
            "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)",
            "Complete Hemogram\n" +
                    "  CRP (C Reactive Protein) Quantitative, Serum\n" +
                    "  Iron Studies\n" +
                    "  Kidney Function Test\n" +
                    "  Vitamin D Total-25 Hydroxy\n" +
                    "  Liver Function Test\n" +
                    "  Lipid Profile"
    };
    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    Button btnGoTOCart, btnBack;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnGoTOCart = findViewById(R.id.buttonLTGoToCart);
        btnBack = findViewById(R.id.buttonLTBack);
        listView = findViewById(R.id.listViewLT);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestActivity.this, HomeActivity.class));
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            item = new HashMap<String, String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", packages[i][4]);
            item.put("line6", "Total Cost: "+packages[i][5]+"LKR");
            list.add(item);
        }
        sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line6", "line5", "line4"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e, R.id.line_f});
        listView.setAdapter(sa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
                intent.putExtra("package", packages[i][0]);
                intent.putExtra("details", package_details[i]);
                intent.putExtra("cost", packages[i][5]);
                startActivity(intent);
            }
        });

        btnGoTOCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestActivity.this, CartLabActivity.class));
            }
        });
    }
}