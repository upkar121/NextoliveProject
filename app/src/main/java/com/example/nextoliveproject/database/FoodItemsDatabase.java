package com.example.nextoliveproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.nextoliveproject.database.Data.Cart;
import com.example.nextoliveproject.database.Data.Food;

public class FoodItemsDatabase extends SQLiteOpenHelper {

    // DB
    public static final String DATABASE_NAME = "FoodItems";
    public static final int DBVersion = 1;

    // Tables
    public static final String Food_Table = "Food";
    public static final String Cart_Table = "Cart";
    // End tables

    // Create Tables Queries
    public String createFoodTable = "create table IF NOT EXISTS " + Food_Table +"("+ Food.MENUID + " INTEGER PRIMARY KEY ,"+Food.PRODUCTID + " TEXT,"+Food.USERID + " TEXT,"+ Food.NAME + " TEXT,"+ Food.PRICE +" REAL,"+ Food.AVAILABILITY +" TEXT,"+Food.TITLE + " TEXT,"+Food.DESCRIPTION + " TEXT,"+Food.IMAGE + " TEXT," + Food.DATE +" TEXT);";
    public String createCartTable = "create table IF NOT EXISTS " + Cart_Table +"("+ Cart.MENUID + " INTEGER PRIMARY KEY ,"+Cart.USERID + " TEXT,"+ Cart.NAME + " TEXT,"+ Cart.PRICE +" REAL,"+ Cart.QUANTITY +" INTEGER," + Cart.DATE +" TEXT);";

    public FoodItemsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createFoodTable);
        db.execSQL(createCartTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Cart_Table);
        db.execSQL("DROP TABLE IF EXISTS " + Food_Table);
    }


}
