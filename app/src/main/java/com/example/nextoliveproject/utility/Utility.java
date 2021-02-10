package com.example.nextoliveproject.utility;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {
    private static final int MAX_DECIMAL = 3;
    public static int FriendId = 0;
    public static Boolean isChatOpen = false;
    public static boolean CheckIfUserLoggedIn(Activity activity) {

        try {
            SharedPreferences shp;
            shp = activity.getSharedPreferences(ConstantVariable.SHARED_DATABASE_NAME, activity.MODE_PRIVATE);
            if (!TextUtils.isEmpty(shp.getString(ConstantVariable.User_Id, ""))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public static String ParseMillisTotDate(long millis) {

        String dateString = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("en"));
            Date dateSms = new Date(millis);
            dateString = sdf.format(dateSms);

        } catch (Exception ex) {

        }
        return dateString;
    }
}
