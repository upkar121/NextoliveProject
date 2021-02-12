package com.example.nextoliveproject.views.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nextoliveproject.R;
import com.example.nextoliveproject.views.MainViewActivity;

public class AlreadyAccountActivity extends AppCompatActivity implements View.OnClickListener{
   public ImageView main_back,visibility;
   public EditText password;
   public TextView forget,signin_error,no_account;
   public LinearLayout nextPage;
   int click = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_account);
        findViewByIds();
        visibility.setImageDrawable(getResources().getDrawable(R.drawable.visibility_off));
        main_back.setOnClickListener(this);
        visibility.setOnClickListener(this);
        nextPage.setOnClickListener(this);
    }

    public void findViewByIds(){
        main_back = findViewById(R.id.main_back);
        visibility = findViewById(R.id.visibility);
        password = findViewById(R.id.password);
        forget = findViewById(R.id.forget_password);
        signin_error = findViewById(R.id.signin_error);
        no_account = findViewById(R.id.no_account);
        nextPage = findViewById(R.id.nextPage);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nextPage:
                if(!password.getText().toString().isEmpty()){
                    startActivity(new Intent(AlreadyAccountActivity.this,MainViewActivity.class));
                }
                break;
            case R.id.main_back:
                onBackPressed();
                break;
            case R.id.visibility:
                if(click == 0){
                    visibility.setImageDrawable(getResources().getDrawable(R.drawable.visibility));
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    click = 1;
                }else{
                    visibility.setImageDrawable(getResources().getDrawable(R.drawable.visibility_off));
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    click = 0;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}