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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {

    EditText ed1, ed2, ed3, ed4;
    TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton, btnBook, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_appointment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv = findViewById(R.id.textViewAppTitle);
        ed1 = findViewById(R.id.editTextAppDrName);
        ed2 = findViewById(R.id.editTextAppDrHospital);
        ed3 = findViewById(R.id.editTextAppDrNo);
        ed4 = findViewById(R.id.editTextAppDrConsFee);
        dateButton = findViewById(R.id.buttonAppDate);
        timeButton = findViewById(R.id.buttonAppTime);
        btnBook = findViewById(R.id.buttonBookApp);
        btnBack = findViewById(R.id.buttonAppBack);

        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        Intent it = getIntent();
        String title = it.getStringExtra("text5");
        String fullname = it.getStringExtra("text1");
        String address = it.getStringExtra("text2");
        String contactno = it.getStringExtra("text3");
        String fee = it.getStringExtra("text4");

        tv.setText("Category: " + title);
        ed1.setText(fullname);
        ed2.setText(address);
        ed3.setText("No: " + contactno);
        ed4.setText("Cons Fee: " + fee + "LKR.");

        //datepicker
        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        //timepicker
        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookAppointmentActivity.this, ChannelDoctorActivity.class));
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                SharedPreferences sp = getSharedPreferences("sharedPref", MODE_PRIVATE);
                String username = sp.getString("username", "").toString();

                // Validate date
                String selectedDate = dateButton.getText().toString().trim();
                if (selectedDate.equals("Select Date")) {
                    Toast.makeText(getApplicationContext(), "Please select a valid date.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate time
                String selectedTime = timeButton.getText().toString().trim();
                if (selectedTime.equals("Select Time")) {
                    Toast.makeText(getApplicationContext(), "Please select a valid time.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (db.checkAppointmentExists(username, title + " => " + fullname, address, contactno, dateButton.getText().toString(), timeButton.getText().toString()) == 1) {
                    Toast.makeText(getApplicationContext(), "Appointment already exists", Toast.LENGTH_SHORT).show();
                    return;
                } else
                    db.addOrder(username, title + " :- " + fullname, address, contactno, String.valueOf(0), dateButton.getText().toString(), timeButton.getText().toString(),
                            Float.parseFloat(fee), "appointment");
                Toast.makeText(getApplicationContext(), "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BookAppointmentActivity.this, HomeActivity.class));


            }
        });
    }

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