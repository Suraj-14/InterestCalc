package com.example.interestcalc;

public class Calc_data {

    private	int	id;
    private	String s_amount,tot_interesr,tot_amount,given_date,return_date,duration,c_time,r_int;

   /* public Calc_data(int id, String s_amount, String tot_interesr, String tot_amount, String given_date, String return_date, String duration) {
        this.id = id;
        this.s_amount = s_amount;
        this.tot_interesr = tot_interesr;
        this.tot_amount = tot_amount;
        this.given_date = given_date;
        this.return_date = return_date;
        this.duration = duration;
    }
    public Calc_data( String s_amount, String tot_interesr, String tot_amount, String given_date, String return_date, String duration) {
        this.s_amount = s_amount;
        this.tot_interesr = tot_interesr;
        this.tot_amount = tot_amount;
        this.given_date = given_date;
        this.return_date = return_date;
        this.duration = duration;
    }*/

    public Calc_data(int id, String s_amount, String tot_interesr, String tot_amount, String given_date, String return_date, String duration, String c_time, String r_int) {
        this.id = id;
        this.s_amount = s_amount;
        this.tot_interesr = tot_interesr;
        this.tot_amount = tot_amount;
        this.given_date = given_date;
        this.return_date = return_date;
        this.duration = duration;
        this.c_time = c_time;
        this.r_int = r_int;
    }

    public Calc_data(String s_amount, String tot_interesr, String tot_amount, String given_date, String return_date, String duration, String c_time, String r_int) {
        this.s_amount = s_amount;
        this.tot_interesr = tot_interesr;
        this.tot_amount = tot_amount;
        this.given_date = given_date;
        this.return_date = return_date;
        this.duration = duration;
        this.c_time = c_time;
        this.r_int = r_int;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getS_amount() {
        return s_amount;
    }

    public void setS_amount(String s_amount) {
        this.s_amount = s_amount;
    }

    public String getTot_interesr() {
        return tot_interesr;
    }

    public void setTot_interesr(String tot_interesr) {
        this.tot_interesr = tot_interesr;
    }

    public String getTot_amount() {
        return tot_amount;
    }

    public void setTot_amount(String tot_amount) {
        this.tot_amount = tot_amount;
    }

    public String getGiven_date() {
        return given_date;
    }

    public void setGiven_date(String given_date) {
        this.given_date = given_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public String getR_int() {
        return r_int;
    }

    public void setR_int(String r_int) {
        this.r_int = r_int;
    }
}
