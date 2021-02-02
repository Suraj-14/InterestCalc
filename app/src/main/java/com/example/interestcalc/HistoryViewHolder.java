package com.example.interestcalc;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

        public TextView amount, tot_int,tot_amt,givendate,returndate,duration,c_date,r_int;
        public ImageView deleteContact;
        public ImageView editContact;
        public ImageView share;

    public HistoryViewHolder(View itemView) {
            super(itemView);
            amount = (TextView) itemView.findViewById(R.id.amount);
            tot_int= (TextView) itemView.findViewById(R.id.tot_int);
            tot_amt = (TextView) itemView.findViewById(R.id.tot_amt);
            givendate = (TextView) itemView.findViewById(R.id.tv_g_date);
            returndate= (TextView) itemView.findViewById(R.id.tv_r_date);
            duration = (TextView) itemView.findViewById(R.id.d_time);
            c_date = (TextView) itemView.findViewById(R.id.c_date);
            r_int = (TextView) itemView.findViewById(R.id.r_interest);
            deleteContact = (ImageView) itemView.findViewById(R.id.delete_contact);
            editContact = (ImageView) itemView.findViewById(R.id.edit_contact);
            share = (ImageView) itemView.findViewById(R.id.share);
        }
    }
