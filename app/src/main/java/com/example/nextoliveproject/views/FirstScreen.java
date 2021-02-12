package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.nextoliveproject.R;
import com.example.nextoliveproject.views.login.LoginActivity;

public class FirstScreen extends AppCompatActivity {
RelativeLayout screen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        screen = findViewById(R.id.screen);

        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstScreen.this, LoginActivity.class));
            }
        });
    }
}