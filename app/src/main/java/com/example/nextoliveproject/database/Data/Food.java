package com.example.nextoliveproject.database.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nextoliveproject.database.FoodItemsDatabase;
import com.example.nextoliveproject.models.CartItems;
import com.example.nextoliveproject.models.FoodItem;
import com.example.nextoliveproject.utility.Utility;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Food {

    public static final String  PRODUCTID = "ProductID";
    public static final String USERID = "UserID";
    public static final String MENUID = "MenuID";
    public static final String NAME = "Name";
    public static final String PRICE = "Price";
    public static final String AVAILABILITY = "Availbility";
    public static final String IMAGE = "Image";
    public static final String DESCRIPTION = "Description";
    public static final String TITLE = "Title";
    public static final String DATE = "Date";

    FoodItemsDatabase sqLiteOpenHelper;
    SQLiteDatabase db;
    Context context;

    public Food(Context context){
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
    public boolean insertRecordCart(FoodItem foodItem, String TEST_TABLE_NAME) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCTID,foodItem.getProductid());
        contentValues.put(MENUID,foodItem.getMenuId());
        contentValues.put(USERID,foodItem.getUserId());
        contentValues.put(NAME,foodItem.getName());
        contentValues.put(PRICE,foodItem.getPrice());
        contentValues.put(AVAILABILITY,foodItem.getAvailability());
        contentValues.put(IMAGE,foodItem.getImage());
        contentValues.put(DESCRIPTION,foodItem.getDescriptions());
        contentValues.put(TITLE, foodItem.getTitle());
        long time = System.currentTimeMillis();
        contentValues.put(DATE, Utility.ParseMillisTotDate(time));
        db.insert(TEST_TABLE_NAME, null, contentValues);
        return true;
    }

    //DELETE All Records Of TABLE
    public void deleteAllRecord(String TEST_TABLE_NAME){
        db.execSQL("delete from "+ TEST_TABLE_NAME);
    }

    //Get Selected Data
    public FoodItem getData(int menuid, String TEST_TABLE_NAME) {
        Cursor cursor = db.rawQuery("select * from " + TEST_TABLE_NAME + " where "+MENUID+"=" + menuid + "", null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    int columnCount=cursor.getColumnCount();
                    int i=0;
                    FoodItem foodItem = new FoodItem();
                    while(i<columnCount)
                    {
                        String columnname  = cursor.getColumnName(i);
                       switch (columnname){
                           case Food.MENUID:
                               foodItem.setMenuId(cursor.getInt(i));
                               break;
                           case Food.PRODUCTID:
                               foodItem.setProductid(cursor.getString(i));
                               break;
                           case Food.USERID:
                               foodItem.setUserId(cursor.getInt(i));
                               break;
                           case Food.NAME:
                               foodItem.setName(cursor.getString(i));
                               break;
                           case Food.TITLE:
                               foodItem.setTitle(cursor.getString(i));
                               break;
                           case Food.DESCRIPTION:
                               foodItem.setDescriptions(cursor.getString(i));
                               break;
                           case Food.PRICE:
                               foodItem.setPrice(cursor.getInt(i));
                               break;
                           case Food.AVAILABILITY:
                               foodItem.setAvailability(cursor.getString(i));
                               break;
                           case Food.IMAGE:
                               foodItem.setImage(cursor.getString(i));
                               break;
                           case Food.DATE:
                               foodItem.setDate(cursor.getString(i));
                               break;
                       }
                        i++;
                    }

                    return foodItem;
                } catch (Exception ex) {

                }
            } while (cursor.moveToNext());
        }
        return null;
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
