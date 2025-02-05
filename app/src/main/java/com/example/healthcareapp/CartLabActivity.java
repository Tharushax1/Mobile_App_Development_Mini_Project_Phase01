package com.example.healthcareapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartLabActivity extends AppCompatActivity {

    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    TextView tvTotal;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton, btnCheckout, btnBack;
    private String[][] packages = {};
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart_lab);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dateButton = findViewById(R.id.buttonCartDate);
        timeButton = findViewById(R.id.buttonCartTime);
        btnCheckout = findViewById(R.id.buttonCartCheckout);
        btnBack = findViewById(R.id.buttonCartBack);
        tvTotal = findViewById(R.id.textViewCartTotalCost);
        lst = findViewById(R.id.listViewCart);

        SharedPreferences sp = getSharedPreferences("sharedPref", MODE_PRIVATE);
        String username = sp.getString("username", "").toString();

        Database db = new Database(getApplicationContext(), "healthcare", null, 1);

        float totalAmount = 0;
        ArrayList dbData = db.getCartData(username, "lab");
        //Toast.makeText(getApplicationContext(), "" + dbData, Toast.LENGTH_LONG).show();

        packages = new String[dbData.size()][];
        for (int i = 0; i < packages.length; i++) {
            packages[i] = new String[6];
        }
        for (int i = 0; i < dbData.size(); i++) {
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0] = strData[0];
            packages[i][4] = "Cost: " + strData[1] + " LKR";
            totalAmount = totalAmount + Float.parseFloat(strData[1]);
        }
        tvTotal.setText("Total Cost : "+totalAmount);

        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            item = new HashMap<String, String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            item.put("line4", packages[i][3]);
            item.put("line5", packages[i][4]);
            item.put("line6", packages[i][5]);
            list.add(item);
        }

        sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line5", "line4", "line6"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e, R.id.line_f});
        lst.setAdapter(sa);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartLabActivity.this, LabTestActivity.class));
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ensure all required fields have valid values
                String price = tvTotal.getText().toString().trim();
                String date = dateButton.getText().toString().trim();
                String time = timeButton.getText().toString().trim();

                if (price.isEmpty() || price.equals("Total Cost : 0.0")) {
                    Toast.makeText(CartLabActivity.this, "Cart is empty. Please add items.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (date.equals("Select Date")) { // Default button text
                    Toast.makeText(CartLabActivity.this, "Please select a date.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (time.equals("Select Time")) { // Default button text
                    Toast.makeText(CartLabActivity.this, "Please select a time.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(CartLabActivity.this, LabTestBookActivity.class);
                intent.putExtra("price", price);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                startActivity(intent);
            }
        });

        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

    }

    //DatePicker and TimePicker
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                dateButton.setText(day + "/" + month + "/" + year);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                timeButton.setText(hour + " : " + minute);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, true);
    }

}