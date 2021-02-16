package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.nextoliveproject.R;

public class OfferActivity extends AppCompatActivity implements View.OnClickListener{
ImageView main_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}