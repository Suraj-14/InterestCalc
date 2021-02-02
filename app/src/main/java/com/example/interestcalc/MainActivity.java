package com.example.interestcalc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private DatePickerDialog fromDatePickerDialog, fromDatePickerDialog2;
    private SimpleDateFormat dateFormatter;
    public TextView fromDateEtxt, endDateEtxt;
    ImageView dtpicker1,dtpicker2;

    private RadioGroup radioBatchGroup, radioGroup_interest;
    private Button buttonShow, buttonClear;
    private EditText amount, R_interest;
    long days = 0;
    int year, month, day;
    private String startDate,endDate;

    private long doubleBackToExitPressedOnce ;
    private String date;
    private HistorySQLiteConnection mDatabase;

    private Switch sw1;
   // private int buttonState = 1;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        fromDateEtxt = (TextView) findViewById(R.id.tv_date);
        endDateEtxt = (TextView) findViewById(R.id.tv_date1);
        dtpicker1 = (ImageView) findViewById(R.id.dtpicker1);
        dtpicker2 = (ImageView) findViewById(R.id.dtpicker2);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        fromDateEtxt.setText(date);
        endDateEtxt.setText(date);
        setDateTimeField();
        setDateTimeField2();

        amount = findViewById(R.id.balance);
        R_interest = findViewById(R.id.intrest);
        buttonShow = findViewById(R.id.calculate);
        buttonClear = findViewById(R.id.clear);
        radioBatchGroup = (RadioGroup) findViewById(R.id.radiogroup);
        //radioGroup_interest = (RadioGroup) findViewById(R.id.radiogroup_int);
        buttonShow.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        radioBatchGroup.setOnCheckedChangeListener(this);
       // radioGroup_interest.setOnCheckedChangeListener(this);

        mDatabase = new HistorySQLiteConnection(this);

        sw1 = (Switch)findViewById(R.id.switch1);
    }
/*DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                      //Todo your work here
                   }
               }, yy, mm, dd);

               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

               dialog.show();*/
    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        dtpicker1.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog =new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Todo your work here
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, day);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }
        }, 2020, 01, 01);

        fromDatePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        fromDatePickerDialog.setTitle("Set Start Date");

       /* fromDatePickerDialog.show();*/

    }

    private void setDateTimeField2() {
        endDateEtxt.setOnClickListener(this);
        dtpicker2.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();

        fromDatePickerDialog2 =new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //Todo your work here
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, day);
                endDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }
        }, 2020, 01, 01);

        fromDatePickerDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        fromDatePickerDialog2.setTitle("Set End Date");
        /*fromDatePickerDialog2.show();*/


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v == fromDateEtxt) {
            fromDatePickerDialog.show();
        }
        if (v == endDateEtxt) {
            fromDatePickerDialog2.show();
        }
        if (v == dtpicker1) {

            Calendar newCalendar = Calendar.getInstance();
            fromDatePickerDialog =new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            fromDatePickerDialog.show();

        }
        if (v == dtpicker2) {

            Calendar newCalendar = Calendar.getInstance();
            fromDatePickerDialog2 =new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    endDateEtxt.setText(dateFormatter.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            fromDatePickerDialog2.show();


        }
        if (v == buttonShow) {
               /* if(buttonState % 2 == 0){
                   buttonShow.setBackgroundColor(getResources().getColor(R.color.btn));
                }
                else{
                    buttonShow.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                buttonState++;*/
            if (validate_data()) {
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    interest_calc();*/

                String str1;
                if (sw1.isChecked()) {
                    str1 = sw1.getTextOn().toString();
                    interest_calc_year();
                }
                else {
                    str1 = sw1.getTextOff().toString();
                    interest_calc();
                }
               // Toast.makeText(this, ""+str1, Toast.LENGTH_SHORT).show();

                }

            }
        if (v == buttonClear) {

            radioBatchGroup.clearCheck();
            fromDateEtxt.setText(date);
            endDateEtxt.setText(date);
            amount.setText("");
            R_interest.setText("");
            amount.setHint("eg:5000.60");
            R_interest.setHint("eg: 2.5");
        }

    }

    public void calculate() {
        DateTimeFormatter dateFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateFormatter = DateTimeFormatter.ofPattern("d/M/u");
        }

         startDate = fromDateEtxt.getText().toString().trim();
         endDate = endDateEtxt.getText().toString().trim();

        LocalDate startDateValue = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startDateValue = LocalDate.parse(startDate, dateFormatter);
        }
        LocalDate endDateValue = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            endDateValue = LocalDate.parse(endDate, dateFormatter);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            days = ChronoUnit.DAYS.between(startDateValue, endDateValue) + 1;

        }
       // Toast.makeText(this, "days  " + days, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        calculate();
        LayoutInflater li = getLayoutInflater();
        View layout = li.inflate(R.layout.custom_toast, (ViewGroup)       findViewById(R.id.custom_toast_layout_id));
        TextView tv = (TextView) layout.findViewById(R.id.text);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);//setting the view of custom toast layout
        toast.show();
        switch (checkedId) {
            case R.id.day:
                tv.setText(days + "  day " );
                //Toast.makeText(this, "days  " + days, Toast.LENGTH_SHORT).show();
                break;

            case R.id.month:
                year = (int) (days / 365);
                month = ((int) (days % 365)) / 30;
                day = (int) ((days % 365) % 30);
                int m=(int)days/30;
                int d=((int)days%30)%30;
                tv.setText( m + " month  " + d + " Day");
               // Toast.makeText(this, m + " month  " + d + " Days", Toast.LENGTH_SHORT).show();
                break;

            case R.id.year:
                year = (int) (days / 365);
                month = ((int) (days % 365)) / 30;
                day = (int) ((days % 365) % 30);
                tv.setText( year + " Year  " + month + " month  " + day + " Day");
               // Toast.makeText(this, year + " Year  " + months + " month  " + day + " Days", Toast.LENGTH_SHORT).show();
                break;

             default:
                 toast.cancel();
                 break;

         /*   case R.id.monthly:
                Toast.makeText(this, "monthly ", Toast.LENGTH_SHORT).show();
                break;

            case R.id.yearly:

                Toast.makeText(this, "yearly ", Toast.LENGTH_SHORT).show();
                break;*/
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void interest_calc() {
        calculate();
        float intrest_day, intrest_month, intrest_year;
        float total_interest;
        float total;

        year = (int) (days / 365);
        month = ((int) (days % 365)) / 30;
        day = (int) ((days % 365) % 30);

        String time = day + " Day || " + month + " Month || " + year + " Year";

        float balance = Float.parseFloat(amount.getText().toString().trim());
        float interest = Float.parseFloat(R_interest.getText().toString().trim());

        intrest_month = (balance * interest) / 100;
        intrest_day = intrest_month / 30;
        intrest_year = 12 * intrest_month;
        // Toast.makeText(this,intrest_year+" Year  "+intrest_month+" month  " +intrest_day +" Days",Toast.LENGTH_SHORT).show();

        total_interest = (year * intrest_year) + (month * intrest_month) + (day * intrest_day);
        total = total_interest + balance;
      //  Toast.makeText(this, " " + total_interest + "   ||  Total  " + total, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(MainActivity.this, Result.class );
        i.putExtra("time", time);
        i.putExtra("amount", balance);
        i.putExtra("tot_int", total_interest);
        i.putExtra("tot_amt", total);
        i.putExtra("int_day", intrest_day);
        i.putExtra("int_month", intrest_month);
        i.putExtra("int_year", intrest_year);
        i.putExtra("start_date", startDate);
        i.putExtra("end_date", endDate);
        i.putExtra("r_int",interest );
        startActivity(i);

        String str_amount, str_tot_int, str_tot_amt,str_int;
        str_amount= String.valueOf(balance);
        str_tot_int = String.valueOf(total_interest);
        str_tot_amt= String.valueOf(total);
        str_int= String.valueOf(interest);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c_time = df.format(c.getTime());

        Calc_data newContact = new Calc_data(str_amount, str_tot_int, str_tot_amt,startDate,endDate,time,c_time,str_int);
        mDatabase.addContacts(newContact);
       /* finish();
        startActivity(getIntent());*/
    }

    private boolean validate_data() {
        String balance = amount.getText().toString();
        String interest = R_interest.getText().toString();

        if (balance.isEmpty()) {
            amount.setError("! Enter the Amount");
            amount.requestFocus();
            return false;
        } else if (interest.isEmpty()) {
            R_interest.setError("! Enter Interest");
            R_interest.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce + 1000 > System.currentTimeMillis()) {
            SweetAlertDialog progressDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            progressDialog.setCancelable(false);
            progressDialog.setTitleText("Are you sure you want to exit?");
            progressDialog.setCancelText("No");
            progressDialog.setConfirmText("Yes");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    MainActivity.super.onBackPressed();
                }
            });
            progressDialog.show();
            return;
        } else {
            Toast.makeText(this, "Double Back press to exit", Toast.LENGTH_SHORT).show();
        }
        doubleBackToExitPressedOnce = System.currentTimeMillis();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.history) {
           // Toast.makeText(this, "Clicked " , Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this,History.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void interest_calc_year() {
        calculate();
        float intrest_day, intrest_month, intrest_year;
        float total_interest;
        float total;

        year = (int) (days / 365);
        month = ((int) (days % 365)) / 30;
        day = (int) ((days % 365) % 30);

        String time = day + " Day || " + month+ " Month || " + year + " Year";

        float balance = Float.parseFloat(amount.getText().toString().trim());
        float interest = Float.parseFloat(R_interest.getText().toString().trim());

        intrest_year = (balance * interest) / 100;
        intrest_month = intrest_year / 12;
        intrest_day = intrest_month /30;
        // Toast.makeText(this,intrest_year+" Year  "+intrest_month+" month  " +intrest_day +" Days",Toast.LENGTH_SHORT).show();

        total_interest = (year * intrest_year) + (month * intrest_month) + (day * intrest_day);
        total = total_interest + balance;
      //  Toast.makeText(this, " " + total_interest + "   ||  Total  " + total, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(MainActivity.this, Result.class );
        i.putExtra("time", time);
        i.putExtra("amount", balance);
        i.putExtra("tot_int", total_interest);
        i.putExtra("tot_amt", total);
        i.putExtra("int_day", intrest_day);
        i.putExtra("int_month", intrest_month);
        i.putExtra("int_year", intrest_year);
        i.putExtra("start_date", startDate);
        i.putExtra("end_date", endDate);
        i.putExtra("r_int",interest );
        startActivity(i);

        String str_amount, str_tot_int, str_tot_amt,str_int;
        str_amount= String.valueOf(balance);
        str_tot_int = String.valueOf(total_interest);
        str_tot_amt= String.valueOf(total);
        str_int= String.valueOf(interest);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String c_time = df.format(c.getTime());

        Calc_data newContact = new Calc_data(str_amount, str_tot_int, str_tot_amt,startDate,endDate,time,c_time,str_int);
        mDatabase.addContacts(newContact);
       /* finish();
        startActivity(getIntent());*/
    }
}
