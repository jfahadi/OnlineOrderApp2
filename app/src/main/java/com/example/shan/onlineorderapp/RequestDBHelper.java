package com.example.shan.onlineorderapp;

/**
 * Created by Shan on 04-Apr-16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RequestDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "oos_db.db";
    public static final String PRODUCT_TABLE_NAME = "itemsdetails";
    public static final String PURCHASED_PRODUCT="itempurchased";
    //Create Product table
    String SQL_CREATE_PRODUCT = "CREATE TABLE " + PRODUCT_TABLE_NAME + "(_ID INT PRIMARY KEY, ItemCode INT UNIQUE,ItemName TEXT, Quantity INT," +
            "Description TEXT, SellingPrice INT,Image_Path INT)";
    String SQL_CREATE_USER = "CREATE TABLE users(_ID INT PRIMARY KEY, username TEXT)";
    String SQL_DELETE_PRODUCT = "DROP TABLE IF EXISTS itemsdetails";
    String SQL_DELETE_USERS = "DROP TABLE IF EXISTS users";
    String SQL_DELETE_PURCHAE="DROP TABLE IF EXISTS itempurchased";
    String SQL_DEFAULT_USER = "INSERT INTO users(username) VALUES ('GUEST')";
    String SQL_CREATE_PURCHASE = "CREATE TABLE " +PURCHASED_PRODUCT+"(_ID INT PRIMARY KEY, ItemCode INT,ItemName TEXT, Quantity INT," +
            "Description TEXT, SellingPrice INT)";

    public RequestDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCT);
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_DEFAULT_USER);
        db.execSQL(SQL_CREATE_PURCHASE);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PRODUCT);
        db.execSQL(SQL_DELETE_USERS);
        db.execSQL(SQL_DELETE_PURCHAE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public String AddData(int product_code,String product_name,int product_qty,String product_description,int product_price) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO "+ PURCHASED_PRODUCT+"(ItemCode,ItemName,Quantity,Description,SellingPrice) VALUES('" + product_code +"','" + product_name + "','" + product_qty + "','" + product_description + "','" + product_price + "');");
            return "New Product added to cart";
        } catch (Exception ex) {
            return "ERROR! Data Not Inserted";
        }
    }

    public String UpdateData(String product_name, int product_qty) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("UPDATE itempurchased SET Quantity='" + product_qty + "' WHERE ItemName = '" + product_name + "';");
            return "Updates Saved";
        } catch (Exception ex) {
            return "Error! "+ex.getMessage();
        }
    }

    public String UpdateUser(String username) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("UPDATE users SET username = '" + username + "';");
            return "Updates Saved";
        } catch (Exception ex) {
            return "Error! "+ex.getMessage();
        }
    }

    public String GetCurrentUser()
    {
        String selectQuery = "SELECT username FROM users";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null && c.moveToFirst()) {
            return  c.getString(c.getColumnIndex("username")).toUpperCase();
        }
        else
            return "GUEST";
    }

    public ArrayList<Product> getAllRequests() {
        ArrayList<Product> products = new ArrayList<Product>();
        String selectQuery = "SELECT  * FROM PRODUCT_TABLE_NAME";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {Product product = new Product(c.getInt(1),c.getInt(2),c.getString(3),c.getInt(4),c.getInt(5),c.getString(6),c.getInt(7));

                products.add(product);
            } while (c.moveToNext());
        }
        return products;
    }


    public ArrayList<PurchaseObject> getAllOrder() {
        ArrayList<PurchaseObject> purchases = new ArrayList<PurchaseObject>();
        String selectQuery = "SELECT  * FROM "+PURCHASED_PRODUCT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null && c.moveToFirst()) {
            do {
                PurchaseObject purchase = new PurchaseObject();
                purchase.code = c.getInt(c.getColumnIndex("code"));
                purchase.productname = c.getString(c.getColumnIndex("productname"));
                purchase.User_Qty = c.getInt(c.getColumnIndex("User_Qty"));
                purchase.productdescription = c.getString(c.getColumnIndex("productdescription"));
                purchase.Total_price = c.getInt(c.getColumnIndex("Total_price"));

                purchases.add(purchase);
            } while (c.moveToNext());
        }
        return purchases;
    }


    public String DeleteItem() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM "+ PURCHASED_PRODUCT);
            return "Deletion Completed";
        } catch (Exception ex) {
            return "Error! "+ex.getMessage();
        }
    }
}

