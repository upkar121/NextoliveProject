package com.example.nextoliveproject.views.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import com.example.nextoliveproject.views.LocationActivity;
import com.example.nextoliveproject.views.login.GetOTPActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText fullname,email,password,confirmpassword;
    String name,emailId,password1,confirmpass;
    private LinearLayout social_connect,nextPage;
    public static LogoProgressDialog pdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewByIds();

        name = fullname.getText().toString().trim();
        emailId = email.getText().toString().trim();
        password1 = password.getText().toString().trim();
        confirmpass = confirmpassword.getText().toString().trim();

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.isEmpty()){
                    fullname.setError("Enter name...");
                }else if(emailId.isEmpty()){
                    email.setError("Enter email...");
                }else if(password1.isEmpty()){
                    password.setError("Enter password...");
                }else if(confirmpass.isEmpty()){
                    confirmpassword.setError("Enter confirm password...");
                }else if(password1.length()<=5){
                    password.setError("Too Short");
                }else if(password1 != confirmpass){
                    confirmpassword.setError("Password and confirm password should be same.");
                }else{
                    Toast.makeText(SignupActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void findViewByIds(){
        nextPage = findViewById(R.id.nextPage);
        social_connect = findViewById(R.id.social_connect);
        fullname =  findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
    }

    public void RegisteredUser(String number){
        try {
            if (Utility.isInternetAvailable(SignupActivity.this)){
                try {
                    pdialog = new LogoProgressDialog(SignupActivity.this);
                    pdialog.setProgress("Please Wait...");

                    Map<String, String> params = new HashMap();
                    params.put("phone", number);

                    JSONObject parameters = new JSONObject(params);
                    RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
                    requestQueue.getCache().clear();
                    JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, Server_URL.register_user_URL, parameters, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {


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
                Toast.makeText(SignupActivity.this, "Internet Required", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Log.d("Api Error",""+ex);
        }
    }

}