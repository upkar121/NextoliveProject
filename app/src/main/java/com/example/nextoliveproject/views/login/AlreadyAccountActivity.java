package com.example.nextoliveproject.views.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.example.nextoliveproject.views.MainViewActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AlreadyAccountActivity extends AppCompatActivity implements View.OnClickListener{
   public ImageView main_back,visibility;
   public EditText password;
   public TextView forget,signin_error,no_account;
   public LinearLayout nextPage,line_error;
   int click = 0;
    public static LogoProgressDialog pdialog;
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
        line_error = findViewById(R.id.line_error);
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

    public void loginApi(String number){
        try {
            if (Utility.isInternetAvailable(AlreadyAccountActivity.this)){
                try {
                    pdialog = new LogoProgressDialog(AlreadyAccountActivity.this);
                    pdialog.setProgress("Please Wait...");

                    Map<String, String> params = new HashMap();
                    params.put("zipcode", number);
                    params.put("locality", "aliganjjj");

                    JSONObject parameters = new JSONObject(params);
                    RequestQueue requestQueue = Volley.newRequestQueue(AlreadyAccountActivity.this);
                    requestQueue.getCache().clear();
                    JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, Server_URL.login_user_URL, parameters, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                if(response.getBoolean("success")){

                                    JSONArray jsonArray = response.getJSONArray("data");


                                }

                            } catch (Exception ex) {
                                Log.d("Response Error",""+ex);
                            }finally {
                                if (pdialog.getDialog().isShowing()) {
                                    pdialog.getDialog().dismiss();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (pdialog.getDialog().isShowing()) {
                                pdialog.getDialog().dismiss();
                            }
                            Log.d("Api Error 1",""+error);
                        }
                    });
                    sr.setRetryPolicy(new DefaultRetryPolicy(3000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(sr);

                } catch (Exception ex) {
                    Log.d("Api Error 2",""+ex);
                }
            } else {
                Toast.makeText(AlreadyAccountActivity.this, "Internet Required", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Log.d("Api Error",""+ex);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}