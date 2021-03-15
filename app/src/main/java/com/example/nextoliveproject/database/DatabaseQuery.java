package com.example.nextoliveproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nextoliveproject.database.Data.Cart;
import com.example.nextoliveproject.models.CartItems;
import com.example.nextoliveproject.utility.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseQuery {
    FoodItemsDatabase sqLiteOpenHelper;
    SQLiteDatabase db;
    Context context;
    public DatabaseQuery(Context context){
        this.context = context;
    }
    public void openDatabase(){
        sqLiteOpenHelper = new FoodItemsDatabase(context);
        db  = sqLiteOpenHelper.getWritableDatabase();
    }
    //Insert Record
    public boolean insertRecordCart(CartItems cartItems, String TEST_TABLE_NAME) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Cart.MENUID,cartItems.getMenuId());
        contentValues.put(Cart.NAME,cartItems.getName());
        contentValues.put(Cart.PRICE,cartItems.getPrice());
        contentValues.put(Cart.QUANTITY,cartItems.getQuantity());
        long time = System.currentTimeMillis();
        contentValues.put(Cart.DATE, Utility.ParseMillisTotDate(time));
        db.insert(TEST_TABLE_NAME, null, contentValues);
        return true;
    }

    //Close Database
    public void close(){
        db.close();
    }

    //Update Record
    public boolean updateRecord(String id,CartItems cartItems,String TEST_TABLE_NAME,String date ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cart.MENUID,cartItems.getMenuId());
        contentValues.put(Cart.NAME,cartItems.getName());
        contentValues.put(Cart.PRICE,cartItems.getPrice());
        contentValues.put(Cart.QUANTITY,cartItems.getQuantity());
        contentValues.put(Cart.DATE,date);
        db.update(TEST_TABLE_NAME, contentValues, Cart.MENUID+"= ? ", new String[]{id});
        return true;
    }

    //Delete Record
    public Integer deleteRecord(Integer id,String TEST_TABLE_NAME) {
        return db.delete(TEST_TABLE_NAME,
                Cart.MENUID+" = ? ",
                new String[]{Integer.toString(id)});

    }

    //DELETE All Records Of TABLE
    public void deleteAllRecord(String TEST_TABLE_NAME){
        db.execSQL("delete from "+ TEST_TABLE_NAME);
    }


    //Get Selected Data
    public JSONObject getData(int id,String TEST_TABLE_NAME) {
        JSONObject rowData = new JSONObject();
        Cursor cursor = db.rawQuery("select * from " + TEST_TABLE_NAME + " where ID=" + id + "", null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    int columnCount=cursor.getColumnCount();
                    int i=0;
                    while(i<columnCount)
                    {
                        rowData.put(cursor.getColumnName(i), cursor.getString(i));
                        i++;
                    }
                } catch (Exception ex) {

                }


            } while (cursor.moveToNext());
        }
        return rowData;
    }


    //Get All data
    public ArrayList<JSONObject> getAllData(String TEST_TABLE_NAME) {
        Cursor cursor = db.rawQuery("select * from " + TEST_TABLE_NAME, null);
        ArrayList<JSONObject> arrayList =  new ArrayList<JSONObject>();
        JSONObject jsonObject = null;
        if (cursor.moveToFirst()) {

            do {

                try {
                    int columnCount=cursor.getColumnCount();
                    jsonObject = new JSONObject();
                    for(int i=0;i<columnCount;i++){
                        jsonObject.put(cursor.getColumnName(i), cursor.getString(i));

                    }
                    arrayList.add(jsonObject);
                } catch (Exception ex) {

                }

            } while (cursor.moveToNext());
        }
        return arrayList;
    }
}
