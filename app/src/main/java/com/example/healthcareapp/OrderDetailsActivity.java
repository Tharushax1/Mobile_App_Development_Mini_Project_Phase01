package com.example.healthcareapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class OrderDetailsActivity extends AppCompatActivity {

    private String[][] order_details = {};

    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView lst;
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnback = findViewById(R.id.buttonODBack);
        lst = findViewById(R.id.listViewOD);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderDetailsActivity.this, HomeActivity.class));
            }
        });

        Database db = new Database(getApplicationContext(), "healthcare", null, 1);
        SharedPreferences sp = getSharedPreferences("sharedPref", MODE_PRIVATE);
        String username = sp.getString("username", "").toString();
        ArrayList dbData = db.getOrderData(username);

        order_details = new String[dbData.size()][];
        for (int i = 0; i < order_details.length; i++) {
            order_details[i] = new String[6];
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            order_details[i][0] = strData[0];
            order_details[i][1] = strData[1];
            if (strData[7].compareTo("medicine") == 0) {
                order_details[i][3] = "Del:"+strData[4];
            } else {
                order_details[i][3] = "Del:" + strData[4] + "  " + strData[5];
            }
            order_details[i][2] = strData[6]+"LKR";
            order_details[i][5] = strData[7];

        }

        list = new ArrayList<>();
        for (int i = 0; i < order_details.length; i++) {
            item = new HashMap<>();
            item.put("line1", order_details[i][0]);
            item.put("line2", order_details[i][1]);
            item.put("line3", order_details[i][2]);
            item.put("line4", order_details[i][3]);
            item.put("line5", order_details[i][4]);
            item.put("line6", order_details[i][5]);
            list.add(item);
        }
        sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5", "line6"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e, R.id.line_f});
        lst.setAdapter(sa);





    }
}