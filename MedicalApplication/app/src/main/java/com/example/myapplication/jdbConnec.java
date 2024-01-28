package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class jdbConnec extends SQLiteOpenHelper {
    public jdbConnec(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersQuery = "create table users(username text, email text, password text)";
        db.execSQL(createUsersQuery);

        String createCartQuery = "create table cart(invoice text,inv_date text,item_name text, price text, qty_ordered text)";
        db.execSQL(createCartQuery);

        String createApptQuery = "create table appointments(docname text,appt_date text,appt_time text)";
        db.execSQL(createApptQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    protected void register(String username,String email,String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username" ,username);
        contentValues.put("email" ,email);
        contentValues.put("password" ,password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users",null,contentValues);
        db.close();
    }

    protected  int loginVerify(String username,String password){
        int check=0;
        String entries[] = new String[2];
        entries[0] = username;
        entries[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where username=? and password=?",entries);
        if(c.moveToFirst()){
            check=1;
        }
        return check;
    }

    protected void inputAppointments(String docName,String apptDate,String apptTime){
        ContentValues contentValues = new ContentValues();
        contentValues.put("docname" ,docName);
        contentValues.put("appt_date",apptDate);
        contentValues.put("appt_time" ,apptTime);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("appointments",null,contentValues);
        db.close();
    }

    protected void delOrder(String invoice){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart","invoice=?",new String[]{invoice});
        db.close();
    }

    protected ArrayList<Appointment> getAppointments(){
        ArrayList<Appointment> appointments = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from appointments",null);
        if(c.moveToFirst()){
            do{
                appointments.add(new Appointment(c.getString(0),c.getString(1),c.getString(2)));
            }while(c.moveToNext());
        }
        return appointments;
    }

    protected  void inputDataInCart(String invoice,String date,String medName,String medPrice,String orderQty){
        ContentValues contentValues = new ContentValues();
        contentValues.put("invoice" ,invoice);
        contentValues.put("inv_date",date);
        contentValues.put("item_name" ,medName);
        contentValues.put("price" ,medPrice);
        contentValues.put("qty_ordered" ,orderQty);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart",null,contentValues);
        db.close();
    }

    protected boolean invoiceCheck(String invoice){
        boolean check=false;
        String entry[] = new String[1];
        entry[0] = invoice;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select invoice from cart where invoice=?",entry);
        if(c.moveToFirst()){
            check=true;
        }
        return check;
    }

    protected ArrayList<Order> getOrderDetails(String invoice){
        String args[] = new String[1];
        args[0] = invoice;
        ArrayList<Order> orders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from cart where invoice=?",args);
        if(c.moveToFirst()){
            do{
                orders.add(new Order(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4)));
            }while(c.moveToNext());
        }
        return  orders;
    }

    protected ArrayList<OrderThumbnail> getInvoiceDate(){
        ArrayList<OrderThumbnail> orderThumbnails = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select distinct invoice,inv_date from cart",null);
        if(c.moveToFirst()){
            do{
                orderThumbnails.add(new OrderThumbnail(c.getString(0),c.getString(1)));
            }while(c.moveToNext());
        }
        return orderThumbnails;
    }
}
