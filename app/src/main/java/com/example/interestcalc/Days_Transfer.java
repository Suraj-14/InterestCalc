package com.example.interestcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import static com.example.interestcalc.R.id.tv_date;
import static com.example.interestcalc.R.id.tv_days;

public class Days_Transfer extends AppCompatActivity implements View.OnClickListener {
    private TextView no_days,no_month,no_year;
    private Button buttonShow, buttonClear;
    private EditText no_of_days;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days__transfer);
        no_days = (TextView) findViewById(tv_days);
        no_month = (TextView) findViewById(R.id.tv_months);
       no_year = (TextView) findViewById(R.id.tv_years);
       no_of_days=findViewById(R.id.no_of_days);
        buttonShow = findViewById(R.id.show);
        buttonClear =findViewById(R.id.clear);
        buttonShow.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==buttonShow) {
            calculate();
        }
        if(v==buttonClear) {
            no_of_days.setText("");
           no_days.setText("");
            no_month.setText("");
            no_year.setText("");
        }
    }
    public void calculate()
    {
        int total_days= Integer.parseInt(no_of_days.getText().toString().trim());
            int year, months, days;
            year = total_days / 365;
            months = (total_days % 365) /30;
            days = (total_days % 365) % 30;

        no_days.setText(""+days);
        no_month.setText(""+months);
        no_year.setText(""+year);
        }
}
