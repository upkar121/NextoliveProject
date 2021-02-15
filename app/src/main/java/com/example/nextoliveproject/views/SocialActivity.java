package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.nextoliveproject.R;

public class SocialActivity extends Activity implements View.OnClickListener {

    Animation bottomUp;
    ViewGroup PoiPanel;
LinearLayout facebook,google;
RelativeLayout blank;
ImageView socialClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        findViewByIds();

        bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up_poi_dialog);
        animateUp(); // Show details activity with animation slide from bottom to up

        blank.setOnClickListener(SocialActivity.this);
        socialClose.setOnClickListener(SocialActivity.this);

    }

    public void findViewByIds(){
       facebook = findViewById(R.id.facebook);
       google = findViewById(R.id.google);
       blank = findViewById(R.id.rl_close);
       socialClose = findViewById(R.id.social_close);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.facebook:
                facebook();
                break;
            case R.id.google:
                google();
                break;
            case R.id.rl_close:
            case R.id.social_close:
                   finish();
                   break;
        }
    }

    public void facebook(){

    }



    public void google(){

    }

    void animateUp()
    {
        try {
            // Animate Up with show tag
            PoiPanel = (ViewGroup) findViewById(R.id.panel);
            PoiPanel.startAnimation(bottomUp);
        }
        catch (Exception ex)
        {
        }
    }
}