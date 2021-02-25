package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(getResources().getColor(R.color.dark_blue_800));
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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