package com.example.nextoliveproject.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.nextoliveproject.utility.ConstantVariable;

public class SharedData {
    public static Activity activity;
    public static SharedPreferences.Editor sharedPreferenceseditor;
    public static SharedPreferences sharedPreferences;
    public static String defvalue = "";
    public static String companyIdValue = "";
    public static boolean booleandefvalue = false;
    public static boolean isCardInUpdatedMode = false;

    public static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(ConstantVariable.SHARED_DATABASE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor putSP(Context context) {
        return context.getSharedPreferences(ConstantVariable.SHARED_DATABASE_NAME, Context.MODE_PRIVATE).edit();
    }

    public static String SHARED_DATABASE_NAME(Context context) {
        return getSP(context).getString(ConstantVariable.SHARED_DATABASE_NAME, defvalue);
    }

    public static void User_Id(Context context, String value) {

        putSP(context).putString(ConstantVariable.User_Id, value).commit();
    }

    public static String User_Id(Context context) {

        return getSP(context).getString(ConstantVariable.User_Id, defvalue);
    }
}
