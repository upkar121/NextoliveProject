package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.nextoliveproject.R;
import com.example.nextoliveproject.views.login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent =new Intent(SplashScreenActivity.this, FirstScreen.class);
                        startActivity(intent);
                        finish();
                    }
                });


            }
        }, 6000);
    }
}