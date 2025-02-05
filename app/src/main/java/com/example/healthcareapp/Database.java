package com.example.healthcareapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query1 = "CREATE TABLE users (username TEXT, email TEXT, password TEXT)";
        sqLiteDatabase.execSQL(query1);

        String query2 = "CREATE TABLE cart (username TEXT, product TEXT, price FLOAT, otype TEXT)";
        sqLiteDatabase.execSQL(query2);

        String query3 = "CREATE TABLE orderplace (username TEXT, fullname TEXT, address TEXT, contactno TEXT, pincode INT, date TEXT, time TEXT, amount FLOAT, otype TEXT)";
        sqLiteDatabase.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void registerUser(String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    public int loginUser(String username, String password) {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            result = 1;
        }
        return result;
    }

    public void addCart(String username, String product, float price, String otype) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("product", product);
        cv.put("price", price);
        cv.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart", null, cv);
        db.close();
    }

    public int checkCart(String username, String product) {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cart WHERE username = ? AND product = ?", new String[]{username, product});
        if (cursor.moveToFirst()) {
            result = 1;
        }
        return result;
    }

    public void removeCart(String username, String otype) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart", "username = ? AND otype = ?", new String[]{username, otype});
        db.close();
    }

    public ArrayList getCartData(String username, String otype) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cart WHERE username = ? AND otype = ?", new String[]{username, otype});
        if (cursor.moveToFirst()) {
            do {
                String product = cursor.getString(1);
                String price = cursor.getString(2);
                arr.add(product + "$" + price);
            } while (cursor.moveToNext());
        }
        db.close();
        return arr;
    }

    public void addOrder(String username, String fullname, String address, String contactno, String pincode, String date, String time, float amount, String otype) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("fullname", fullname);
        cv.put("address", address);
        cv.put("contactno", contactno);
        cv.put("pincode", pincode);
        cv.put("date", date);
        cv.put("time", time);
        cv.put("amount", amount);
        cv.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("orderplace", null, cv);
        db.close();
    }

    public ArrayList getOrderData(String username) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orderplace WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            do {
                String fullname = cursor.getString(1);
                String address = cursor.getString(2);
                String contactno = cursor.getString(3);
                String pincode = cursor.getString(4);
                String date = cursor.getString(5);
                String time = cursor.getString(6);
                String amount = cursor.getString(7);
                String otype = cursor.getString(8);

                arr.add(fullname + "$" + address + "$" + contactno + "$" + pincode + "$" + date + "$" + time + "$" + amount + "$" + otype);
            } while (cursor.moveToNext());
        }
        db.close();
        return arr;
    }

    public int checkAppointmentExists(String username, String fullname, String address, String contactno, String date, String time) {
        int result = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orderplace WHERE username = ? AND fullname = ? AND address = ? AND contactno = ? AND date = ? AND time = ?",
                new String[]{username, fullname, address, contactno, date, time});
        if (cursor.moveToFirst()) {
            result = 1;
        }
        db.close();
        return result;
    }

}

