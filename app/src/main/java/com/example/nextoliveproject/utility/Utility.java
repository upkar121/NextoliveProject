package com.example.nextoliveproject.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nextoliveproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

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


    public static void dialogbox(Context activity,String title,String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_design);
        TextView text = (TextView) dialog.findViewById(R.id.title_textview);
        text.setText(title);
        TextView define_text = (TextView) dialog.findViewById(R.id.define_text);
        define_text.setText(msg);
        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.cancel);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public static boolean isInternetAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return Objects.requireNonNull(connectivityManager).getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static float calculateTime(double lat1,double lang1,double lat2,double lang2){
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lang1);

        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lang2);

        float distance = loc1.distanceTo(loc2);

        int speed=30;
        float time = distance/speed;

        return time;
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.60934;
        return (dist);
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


}
