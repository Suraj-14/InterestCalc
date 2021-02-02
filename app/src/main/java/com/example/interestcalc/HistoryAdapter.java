package com.example.interestcalc;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> //implements Filterable

{

    private Context context;
    private ArrayList<Calc_data> listContacts;
    private ArrayList<Calc_data> mArrayList;

    private HistorySQLiteConnection mDatabase;

    long days = 0;
    int year, months, day;
    String str_amount, str_tot_int, str_tot_amt,time;

    public HistoryAdapter(Context context, ArrayList<Calc_data> listContacts) {
        this.context = context;
        this.listContacts = listContacts;
        this.mArrayList=listContacts;
        mDatabase = new HistorySQLiteConnection(context);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);
        return new HistoryViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        final Calc_data contacts = listContacts.get(position);

       /* Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(c.getTime());*/

        holder.amount.setText(contacts.getS_amount());
        holder.tot_int.setText(contacts.getTot_interesr());
        holder.tot_amt.setText(contacts.getTot_amount());
        holder.givendate.setText(contacts.getGiven_date());
        holder.returndate.setText(contacts.getReturn_date());
        holder.duration.setText(contacts.getDuration());
        holder.c_date.setText(contacts.getC_time());
        holder.r_int.setText(contacts.getR_int());

        holder.editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(contacts);
            }
        });

        holder.deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteContact(contacts.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = "\u20B9";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Given Date :"+contacts.getGiven_date() +"\n"+"End date :"+contacts.getReturn_date()+"\n"+"Time :"+contacts.getDuration() +"\n"+"Amount :"+string+" "+contacts.getS_amount()+"\n"+"Total Interest :"+string+" "+contacts.getTot_interesr()
                        +"\n"+ "Total Amount :"+string+" "+contacts.getTot_amount()    +"\n"+ "Rate of Interest :"+" "+contacts.getR_int());
                context.startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listContacts.size();
    }


    private void editTaskDialog(final Calc_data contacts){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.contact_list_layout, null);

        final EditText edit_g_date = subView.findViewById(R.id.edit_g_date);
        final EditText edit_e_date = subView.findViewById(R.id.edit_r_date);
        final EditText edit_bal = (EditText)subView.findViewById(R.id.edit_amount);
        final EditText edit_int = (EditText)subView.findViewById(R.id.edit_interest);

        if(contacts != null){
            edit_g_date.setText(contacts.getGiven_date());
           edit_e_date.setText(contacts.getReturn_date());
            edit_bal.setText(contacts.getS_amount());
            edit_int .setText(contacts.getR_int());

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.DialogTheme);
        builder.setTitle("Edit Record Details");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Save & Calculate", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String g_date = edit_g_date.getText().toString();
                final String e_date = edit_e_date.getText().toString();
                final String n_bal = edit_bal.getText().toString();
                final String n_int = edit_int.getText().toString();

                if(TextUtils.isEmpty(n_bal)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                if(TextUtils.isEmpty(n_int)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
               else{
                    calculate(g_date,e_date);
                    interest_calc(n_bal, n_int);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String c_time = df.format(c.getTime());

                    mDatabase.updateContacts(new Calc_data(contacts.getId(),n_bal, str_tot_int, str_tot_amt,g_date,e_date,time,c_time,n_int));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }
    public void calculate(String g_date,String e_date) {
        DateTimeFormatter dateFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateFormatter = DateTimeFormatter.ofPattern("d/M/u");
        }
        LocalDate startDateValue = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startDateValue = LocalDate.parse(g_date, dateFormatter);
        }
        LocalDate endDateValue = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            endDateValue = LocalDate.parse(e_date, dateFormatter);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            days = ChronoUnit.DAYS.between(startDateValue, endDateValue) + 1;

        }
       // Toast.makeText(context, "days  " + days, Toast.LENGTH_SHORT).show();
    }
    public void interest_calc(String bal,String r_int) {
        float intrest_day, intrest_month, intrest_year;
        float total_interest;
        float total;

        year = (int) (days / 365);
        months = ((int) (days % 365)) / 30;
        day = (int) ((days % 365) % 30);

         time = day + " Day || " + months + " Month || " + year + " Year";

        float balance = Float.parseFloat(bal);
        float interest = Float.parseFloat(r_int);

        intrest_month = (balance * interest) / 100;
        intrest_day = intrest_month / 30;
        intrest_year = 12 * intrest_month;
        // Toast.makeText(this,intrest_year+" Year  "+intrest_month+" month  " +intrest_day +" Days",Toast.LENGTH_SHORT).show();

        total_interest = (year * intrest_year) + (months * intrest_month) + (day * intrest_day);
        total = total_interest + balance;

        str_amount= String.valueOf(balance);
        str_tot_int = String.valueOf(total_interest);
        str_tot_amt= String.valueOf(total);
        /*Calc_data newContact = new Calc_data(str_amount, str_tot_int, str_tot_amt,startDate,endDate,time);
        mDatabase.addContacts(newContact);*/
       /* finish();
        startActivity(getIntent());*/
    }
}