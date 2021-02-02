package com.example.interestcalc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.interestcalc.R.id.back;
import static com.example.interestcalc.R.id.tv_amt;
import static com.example.interestcalc.R.id.tv_date;
import static com.example.interestcalc.R.id.tv_days;
import static com.example.interestcalc.R.id.tv_int_per_day;

public class Result extends AppCompatActivity implements View.OnClickListener {
    private TextView amount,tot_int,tot_amt,int_per_day,int_per_month,int_per_year,time,r_int,today;
    private Float str_amount,str_tot_int,str_tot_amt,str_int_per_day,str_int_per_month,str_int_per_year,str_r_int;
    private String str_time,start_date,end_date;
    private Button buttonBack, buttonShare;
    public TextView start_d, end_d;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        amount = (TextView) findViewById(tv_amt);
        tot_int= (TextView) findViewById(R.id.tv_tot_int);
        r_int= (TextView) findViewById(R.id.tv_r_int);
        tot_amt = (TextView) findViewById(R.id.tv_tot_amt);
        today = (TextView) findViewById(R.id.c_time);

       int_per_day = (TextView) findViewById(tv_int_per_day);
        int_per_month= (TextView) findViewById(R.id.tv_int_per_month);
        int_per_year = (TextView) findViewById(R.id.tv_int_per_year);

        start_d = (TextView) findViewById(tv_date);
        end_d = (TextView) findViewById(R.id.tv_date1);

        time=findViewById(R.id.time);
        buttonBack = findViewById(R.id.back);
       buttonShare =findViewById(R.id.share);
        buttonBack.setOnClickListener(this);
       buttonShare.setOnClickListener(this);

        Intent i=getIntent();
        str_amount=i.getFloatExtra("amount",0);
        str_tot_int=i.getFloatExtra("tot_int",0);
        str_tot_amt=i.getFloatExtra("tot_amt",0);
        str_int_per_day=i.getFloatExtra("int_day",0);
        str_int_per_month=i.getFloatExtra("int_month",0);
        str_int_per_year=i.getFloatExtra("int_year",0);
        str_time=i.getStringExtra("time");
        start_date=i.getStringExtra("start_date");
        end_date=i.getStringExtra("end_date");
        str_r_int=i.getFloatExtra("r_int",0);


        /*Intent intent = new Intent(Result.this, History.class );
        intent.putExtra("time", str_time);
        intent.putExtra("amount", str_amount);
        intent.putExtra("tot_int", str_tot_int);
        intent.putExtra("tot_amt", str_tot_amt);
        intent.putExtra("int_day", str_int_per_day);
        intent.putExtra("int_month",  str_int_per_month);
        intent.putExtra("int_year", str_int_per_year);
        intent.putExtra("start_date",  start_date);
        intent.putExtra("end_date", end_date);
        startActivity(intent);*/

        time.setText(str_time);
        start_d.setText(start_date);
        end_d.setText(end_date);
        r_int.setText(""+str_r_int);
        String string = "\u20B9";

        amount.setText(""+string+" "+str_amount+" /-");
        tot_int.setText(""+string+" "+str_tot_int+" /-");
        tot_amt .setText( ""+string+" "+str_tot_amt+" /-");

        int_per_day.setText(""+string+" "+str_int_per_day+" /-");
        int_per_month.setText(""+string+" "+str_int_per_month+" /-");
        int_per_year.setText( ""+string+" "+str_int_per_year+" /-");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c_time = df.format(c.getTime());
        today.setText(c_time);

    }

    @Override
    public void onClick(View v) {
    if(v==buttonBack){
        Intent i=new Intent(Result.this,MainActivity.class);
        startActivity(i);
    }
    if(v==buttonShare){
        String string = "\u20B9";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Start Date :"+start_date +"\n"+"End date :"+end_date +"\n"+"Time :"+str_time +"\n"+"Amount :"+string+" "+str_amount+"\n"+"Total Interest :"+string+" "+str_tot_int
                +"\n"+ "Total Amount :"+string+" "+str_tot_amt     +"\n"+ "Interest Per Month :"+string+" "+str_int_per_month);
        startActivity(sendIntent);

         }
         }
@Override
public void onBackPressed() {
        super.onBackPressed();
        }
        }
