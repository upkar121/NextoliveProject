package com.example.nextoliveproject.database.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nextoliveproject.database.FoodItemsDatabase;
import com.example.nextoliveproject.models.CartItems;
import com.example.nextoliveproject.utility.Utility;

import org.json.JSONObject;

import java.util.ArrayList;

public class Cart {

    public static final String MENUID = "MenuId";
    public static final String USERID = "UserId";
    public static final String NAME = "Name";
    public static final String PRICE = "Price";
    public static final String QUANTITY = "Quantity";
    public static final String DATE = "Date";

    FoodItemsDatabase sqLiteOpenHelper;
    SQLiteDatabase db;
    Context context;

    public Cart(Context context){
        this.context = context;
        sqLiteOpenHelper = new FoodItemsDatabase(context);
    }

    //Open Database
    public void openDatabase(){
        db  = sqLiteOpenHelper.getWritableDatabase();
    }

    //Close Database
    public void close(){
        db.close();
    }

    //Insert Record
    public boolean insertRecordCart(CartItems cartItems, String TEST_TABLE_NAME) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MENUID,cartItems.getMenuId());
        contentValues.put(USERID,cartItems.getUserId());
        contentValues.put(NAME,cartItems.getName());
        contentValues.put(PRICE,cartItems.getPrice());
        contentValues.put(QUANTITY,cartItems.getQuantity());
        long time = System.currentTimeMillis();
        contentValues.put(DATE, Utility.ParseMillisTotDate(time));
        db.insert(TEST_TABLE_NAME, null, contentValues);
        return true;
    }

    //Update Record
    public boolean updateRecord(String id,CartItems cartItems,String TEST_TABLE_NAME,String date ) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MENUID,cartItems.getMenuId());
        contentValues.put(USERID,cartItems.getUserId());
        contentValues.put(NAME,cartItems.getName());
        contentValues.put(PRICE,cartItems.getPrice());
        contentValues.put(QUANTITY,cartItems.getQuantity());
        contentValues.put(DATE,date);
        db.update(TEST_TABLE_NAME, contentValues, MENUID+"= ? ", new String[]{id});
        return true;
    }

    //Delete Record
    public Integer deleteRecord(Integer id,String TEST_TABLE_NAME) {
        return db.delete(TEST_TABLE_NAME,
                MENUID+" = ? ",
                new String[]{""+id});
    }

    //DELETE All Records Of TABLE
    public void deleteAllRecord(String TEST_TABLE_NAME){
        db.execSQL("delete from "+ TEST_TABLE_NAME);
    }

    public int getQuantity(int menuid,String TABLE_NAME){
        Cursor cursor = db.rawQuery("select "+ QUANTITY +" from " + TABLE_NAME + " where "+ MENUID +" =" + menuid + "", null);
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            return cursor.getInt((cursor.getCount()-1));
        }
       return 0;
    }

    public int totalQuantity(String TABLE_NAME){
        Cursor cursor = db.rawQuery("select SUM("+ QUANTITY +") from " + TABLE_NAME, null);
        try {
            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                return cursor.getInt((cursor.getCount() - 1));
            }
        }catch (Exception e){
            Log.d("Count Error",""+e);
        }
        return 0;
    }
    public float getPrice(int menuid,String TABLE_NAME){
        Cursor cursor = db.rawQuery("select "+ PRICE +" from " + TABLE_NAME + " where "+ MENUID +" =" + menuid + "", null);
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            return cursor.getFloat((cursor.getCount()-1));
        }
        return 0;
    }


    //Get Selected Data
    public JSONObject getData(int id, String TEST_TABLE_NAME) {
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
    public ArrayList<CartItems> getAllData(String TEST_TABLE_NAME) {
        Cursor cursor = db.rawQuery("select * from " + TEST_TABLE_NAME, null);
        ArrayList<CartItems> arrayList =  new ArrayList<CartItems>();
        CartItems cartItems = null;
        if (cursor.moveToFirst()) {

            do {

                try {
                    int columnCount=cursor.getColumnCount();
                   cartItems = new CartItems();
                    for(int i=0;i<columnCount;i++){
                        switch (cursor.getColumnName(i)){
                            case MENUID:
                                cartItems.setMenuId(cursor.getInt(i));
                                break;
                            case USERID:
                                cartItems.setUserId(cursor.getInt(i));
                                break;
                            case NAME:
                                cartItems.setName(cursor.getString(i));
                                break;
                            case PRICE:
                                cartItems.setPrice(cursor.getInt(i));
                                break;
                            case QUANTITY:
                                cartItems.setQuantity(cursor.getInt(i));
                                break;
                            case DATE:
                                cartItems.setDate(cursor.getString(i));
                                break;
                        }
                    }
                    arrayList.add(cartItems);
                } catch (Exception ex) {
                    Log.d("Database Error",""+ex);
                }

            } while (cursor.moveToNext());
        }
        return arrayList;
    }


    public int getItemsCount(String TEST_TABLE_NAME){
        Cursor cursor = db.rawQuery("select * from " + TEST_TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

}
