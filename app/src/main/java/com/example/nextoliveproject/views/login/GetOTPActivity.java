package com.example.nextoliveproject.views.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nextoliveproject.Helper.OtpEditText;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.network.SmsListener;
import com.example.nextoliveproject.network.SmsReceiver;
import com.example.nextoliveproject.views.LocationActivity;
import com.example.nextoliveproject.views.MapLocActivity;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class GetOTPActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQ_USER_CONSENT = 200;
    public ImageView main_back;
    public LinearLayout verifyOTP,line_error,nextPage;
    public TextView contact,resend_textview,timer_textview;
    public OtpEditText et_otp;
    Activity activity;

    TimerTask task1;
    Timer timer1;
    long diffInSeconds = 20;

    public String mobileNo="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_o_t_p);
        activity = this;
        findViewByIds();
        mobileNo = getIntent().getStringExtra(LoginActivity.EXTRA_CONTACT_NUMBER);
        contact.setText(mobileNo);
        showTimer();

        main_back.setOnClickListener(this);
        nextPage.setOnClickListener(this);

        et_otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              line_error.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void findViewByIds(){
        verifyOTP = findViewById(R.id.nextPage);
        contact   = findViewById(R.id.contact);
        resend_textview  = findViewById(R.id.resend);
        timer_textview   = findViewById(R.id.timer);
        main_back = findViewById(R.id.main_back);
        et_otp = findViewById(R.id.et_otp);
        line_error = findViewById(R.id.line_error);
        nextPage = findViewById(R.id.nextPage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_back:
                onBackPressed();
                break;
            case R.id.nextPage:
                 checkOtp();
                break;
            case R.id.resend:
                   resendCode();
                break;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void checkOtp(){
        resend_textview.setText(getResources().getString(R.string.resend_code));
        resend_textview.setTextColor(getResources().getColor(R.color.text_hint));
        String otp = et_otp.getText().toString();
        String defaultOTP = "1234";
        String text = mobileNo +". ";
        if(otp.isEmpty()){
            contact.setText(Html.fromHtml(text + "<font color=#f37328>" + getResources().getString(R.string.check_correct_mobile_number) + "</font>"));
            ///contact.setText(mobileNo+""+getResources().getString(R.string.check_correct_mobile_number));
        }else if(!otp.equals(defaultOTP)){
            contact.setText(Html.fromHtml(text + "<font color=#f37328>" + getResources().getString(R.string.check_correct_mobile_number) + "</font>"));
            line_error.setVisibility(View.VISIBLE);
        }else{
              startActivity(new Intent(GetOTPActivity.this, LocationActivity.class));
              finish();
        }
    }

    public void resendCode(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    private void showTimer() {

        resend_textview.setText(getResources().getString(R.string.resend_code));
        resend_textview.setTextColor(getResources().getColor(R.color.text_hint));
        timer1 = new Timer();
        task1 = new TimerTask() {

            public void run() {

                runOnUiThread(new Runnable() {
                    public void run() {

                        if (diffInSeconds > 0) {
                            String timeMessage = calculateTime(diffInSeconds);
                            String finalMessage = timeMessage;
                            timer_textview.setText(" "+finalMessage);
                            diffInSeconds--;
                        } else {
                            task1.cancel();
                            timer1.cancel();
                            timer_textview.setText("");
                            timer_textview.setVisibility(View.VISIBLE);
                            resend_textview.setText("Resend code");
                            resend_textview.setTextColor(getResources().getColor(R.color.text_web_link));
                            resend_textview.setOnClickListener(GetOTPActivity.this);
                            line_error.setVisibility(View.GONE);
                            contact.setText(mobileNo+".");
                        }
                    }
                });
            }
        };
        timer1.scheduleAtFixedRate(task1, 0, 1000);
    }

    public static String calculateTime(long seconds) {

        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) -
                TimeUnit.DAYS.toHours(day);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) -
                TimeUnit.DAYS.toMinutes(day) -
                TimeUnit.HOURS.toMinutes(hours);
        long second = TimeUnit.SECONDS.toSeconds(seconds) -
                TimeUnit.DAYS.toSeconds(day) -
                TimeUnit.HOURS.toSeconds(hours) -
                TimeUnit.MINUTES.toSeconds(minute);
        String finalTime = "";
        if (minute > 0) {
            if (String.valueOf(minute).length() == 1)
                finalTime += "0" + minute + " : ";
            else
                finalTime += "" + minute + " : ";
        } else {
            finalTime += "00 : ";
        }
        if (second > 0) {
            if (String.valueOf(second).length() == 1)
                finalTime += "0" + second;
            else
                finalTime += "" + second;
        } else
            finalTime += "00";
        return finalTime;
    }


    @Override
    protected void onResume() {

        super.onResume();
        RequestReadSMSPermission();
    }

    public void RequestReadSMSPermission() {
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_SMS}, 1);
        } else {
            bindSMSListener();
        }

    }

    private void bindSMSListener() {
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String Sender, String messageText) {

                if (Sender.contains("+98100077")) {
                    String value = messageText;
                    timer_textview.setText(value);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {
            case 1: {
                bindSMSListener();
                break;
            }

        }
    }
}
