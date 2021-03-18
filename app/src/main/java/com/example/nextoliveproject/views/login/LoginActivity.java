package com.example.nextoliveproject.views.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nextoliveproject.Helper.LogoProgressDialog;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.network.Server_URL;
import com.example.nextoliveproject.utility.Utility;
import com.example.nextoliveproject.views.SocialActivity;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA_CONTACT_NUMBER = "CONTACT_NUMBER";
    public static int REQUEST_PERMISSIONS_REQUEST_CODE = 100;
    public ImageView main_back,clear_text;
    public TextView main_text,error;
    public CountryCodePicker ccp;
    public EditText contact_number;
    public LinearLayout line_error,social_connect,nextPage;
    public View view;
    public static LogoProgressDialog pdialog;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
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
                line_error.setVisibility(View.GONE);
                view.setBackgroundColor(getResources().getColor(R.color.dark_blue_800));
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
        line_error = findViewById(R.id.line_error);
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
            error.setText(getResources().getString(R.string.invalid_phone_number));
            view.setBackgroundColor(getResources().getColor(R.color.text_warning));
            line_error.setVisibility(View.VISIBLE);
        }else{
            /* Check if user is new user or already registered user.
            * If user already registered we go to AlreadyAccountActivity
            * If user is new we move to GetOTPActivity*/
            //Add Api Content here
             Intent i = new Intent(LoginActivity.this, GetOTPActivity.class);
             i.putExtra(this.EXTRA_CONTACT_NUMBER,country_code+" "+number);
             startActivity(i);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}