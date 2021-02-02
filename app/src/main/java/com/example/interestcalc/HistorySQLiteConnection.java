package com.example.interestcalc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class HistorySQLiteConnection extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	1;
    private	static final String	DATABASE_NAME = "InterestCalc.db";
    private	static final String TABLE_NAME = "History";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_Amount = "amount";
    private static final String COLUMN_Tot_int = "tot_int";
    private static final String COLUMN_Tot_Amt = "tot_amt";
    private static final String COLUMN_Given_Date = "date1";
    private static final String COLUMN_Return_Date = "date2";
    private static final String COLUMN_Duration = "date3";
    private static final String COLUMN_Current_Date = "date4";
    private static final String COLUMN_R_Interest = "interest";

    public HistorySQLiteConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String	CREATE_CONTACTS_TABLE = "CREATE	TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_Amount + " TEXT," + COLUMN_Tot_int + " TEXT," +  COLUMN_Tot_Amt + " TEXT,"+ COLUMN_Given_Date+ " TEXT," + COLUMN_Return_Date + " TEXT," + COLUMN_Duration + " TEXT,"+COLUMN_Current_Date + " TEXT," + COLUMN_R_Interest + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<Calc_data> listContacts(){
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Calc_data> storeContacts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String s_amount = cursor.getString(1);
                String tot_interesr = cursor.getString(2);
                String tot_amount = cursor.getString(3);
                String givendate = cursor.getString(4);
                String returndate = cursor.getString(5);
                String duration = cursor.getString(6);
                String c_date = cursor.getString(7);
                String r_interest = cursor.getString(8);
                storeContacts.add(new Calc_data(id,s_amount , tot_interesr,tot_amount,givendate,returndate,duration,c_date,r_interest));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return storeContacts;
    }

    public void addContacts(Calc_data contacts){
        ContentValues values = new ContentValues();
        values.put(COLUMN_Amount, contacts.getS_amount());
        values.put(COLUMN_Tot_int, contacts.getTot_interesr());
        values.put(COLUMN_Tot_Amt, contacts.getTot_amount());
        values.put(COLUMN_Given_Date, contacts.getGiven_date());
        values.put(COLUMN_Return_Date, contacts.getReturn_date());
        values.put(COLUMN_Duration, contacts.getDuration());
        values.put(COLUMN_Current_Date, contacts.getC_time());
        values.put(COLUMN_R_Interest, contacts.getR_int());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }

    public void updateContacts(Calc_data contacts){
        ContentValues values = new ContentValues();
        values.put(COLUMN_Amount, contacts.getS_amount());
        values.put(COLUMN_Tot_int, contacts.getTot_interesr());
        values.put(COLUMN_Tot_Amt, contacts.getTot_amount());
        values.put(COLUMN_Given_Date, contacts.getGiven_date());
        values.put(COLUMN_Return_Date, contacts.getReturn_date());
        values.put(COLUMN_Duration, contacts.getDuration());
        values.put(COLUMN_Current_Date, contacts.getC_time());
        values.put(COLUMN_R_Interest, contacts.getR_int());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(contacts.getId())});
    }

    /*public Calc_data findContacts(int id){
        String query = "Select * FROM "	+ TABLE_NAME+ " WHERE " + COLUMN_ID + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Calc_data contacts = null;
        Cursor cursor = db.rawQuery(query,	null);
        if	(cursor.moveToFirst()){
            id = Integer.parseInt(cursor.getString(0));
            String get_amount = cursor.getString(1);
            String get_tot_int = cursor.getString(2);
            String get_tot_amt = cursor.getString(1);
            String get_givendate = cursor.getString(2);
            String get_returndate = cursor.getString(1);
            String get_duration = cursor.getString(2);
            String get_currentdate = cursor.getString(1);
            String get_r_int = cursor.getString(2);
            contacts = new Calc_data(id, get_amount,get_tot_int,get_tot_amt,get_givendate,get_returndate,get_duration,get_currentdate,get_r_int);
        }
        cursor.close();
        return contacts;
    }*/

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
         db.delete(TABLE_NAME,null,null);
        //db.execSQL("delete * from"+ TABLE_NAME);
       // db.execSQL("TRUNCATE table" + TABLE_NAME);
        db.close();
    }
}