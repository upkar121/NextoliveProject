package com.example.nextoliveproject.views.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nextoliveproject.MainActivity;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.views.SocialActivity;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA_CONTACT_NUMBER = "CONTACT_NUMBER";
    public static int REQUEST_PERMISSIONS_REQUEST_CODE = 100;
    public ImageView main_back,clear_text;
    public TextView main_text,error;
    public CountryCodePicker ccp;
    public EditText contact_number;
    public LinearLayout social_connect,nextPage;
    public View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewByIds();
        main_back.setOnClickListener(this);
        social_connect.setOnClickListener(this);
        clear_text.setOnClickListener(this);
        nextPage.setOnClickListener(this);
        contact_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                error.setVisibility(View.GONE);
               if(!contact_number.getText().toString().isEmpty()){
                   clear_text.setVisibility(View.VISIBLE);
               }else{
                   clear_text.setVisibility(View.GONE);
               }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void findViewByIds(){
        main_back = findViewById(R.id.main_back);
        main_text = findViewById(R.id.main_text);
        ccp = findViewById(R.id.ccp);
        contact_number = findViewById(R.id.contactNumber);
        clear_text = findViewById(R.id.clear_text);
        social_connect = findViewById(R.id.social_connect);
        nextPage = findViewById(R.id.nextPage);
        error = findViewById(R.id.error);
        view = findViewById(R.id.view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions();
        }
    }


    private void requestPermissions() {
        // Request permission
        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {

                }
            }
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_back:
                onBackPressed();
                break;
            case R.id.social_connect:
                 startActivity(new Intent(LoginActivity.this, SocialActivity.class));
                break;
            case R.id.clear_text:
                clear_text.setVisibility(View.GONE);
                contact_number.setText(null);
                break;
            case R.id.nextPage:
                checkNumbervalidation();
                break;
        }
    }

    public void checkNumbervalidation(){
        String country_code = ccp.getSelectedCountryCode();
        String number = contact_number.getText().toString().trim();

        if(number.isEmpty()){
            contact_number.setFocusable(true);
            view.setBackgroundColor(getResources().getColor(R.color.text_warning));
            error.setVisibility(View.VISIBLE);
        }else{
             Intent i = new Intent(LoginActivity.this, AlreadyAccountActivity.class);
             i.putExtra(this.EXTRA_CONTACT_NUMBER,country_code+" "+number);
             startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}