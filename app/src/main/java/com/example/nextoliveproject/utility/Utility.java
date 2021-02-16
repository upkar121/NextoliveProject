package com.example.nextoliveproject.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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



}
