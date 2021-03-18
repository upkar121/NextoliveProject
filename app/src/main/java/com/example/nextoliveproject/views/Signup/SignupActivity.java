package com.example.nextoliveproject.views.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.example.nextoliveproject.MainActivity;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.models.RegisterUser;
import com.example.nextoliveproject.network.Server_URL;
import com.example.nextoliveproject.utility.Utility;
import com.example.nextoliveproject.views.LocationActivity;
import com.example.nextoliveproject.views.MainViewActivity;
import com.example.nextoliveproject.views.SocialActivity;
import com.example.nextoliveproject.views.login.GetOTPActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnFocusChangeListener,View.OnClickListener {

    private TextInputEditText fullname,email,password,confirmpassword,phoneno;
    private TextInputLayout phoneTextLayout,fullnameTextLayout,emailTextLayout,passwordTextLayout,confirmpasswordTextLayout;
    String name,emailId,password1,confirmpass,phone;
    private LinearLayout social_connect,nextPage;
    public static LogoProgressDialog pdialog;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewByIds();
        //Click
        back.setOnClickListener(this);
        social_connect.setOnClickListener(this);
        nextPage.setOnClickListener(this);
        //Text Focus
        phoneno.setOnFocusChangeListener(this);
        fullname.setOnFocusChangeListener(this);
        email.setOnFocusChangeListener(this);
        password.setOnFocusChangeListener(this);
        confirmpassword.setOnFocusChangeListener(this);

    }


    public void findViewByIds(){
        back = findViewById(R.id.main_back);
        nextPage = findViewById(R.id.nextPage);
        social_connect = findViewById(R.id.social_connect);
        fullname =  findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        phoneno = findViewById(R.id.phone);
        phoneTextLayout=findViewById(R.id.phonetextlayout);
        fullnameTextLayout = findViewById(R.id.fullnametextlayout);
        emailTextLayout = findViewById(R.id.emailtextlayout);
        passwordTextLayout = findViewById(R.id.passwordtextlayout);
        confirmpasswordTextLayout = findViewById(R.id.confirmpasswordtextlayout);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_back:
                onBackPressed();
                break;
            case R.id.social_connect:
                startActivity(new Intent(SignupActivity.this, SocialActivity.class));
                break;
            case R.id.nextPage:
                verifyTextInput();
                break;
        }
    }

    public void verifyTextInput(){
        name = fullname.getText().toString().trim();
        emailId = email.getText().toString().trim();
        password1 = password.getText().toString().trim();
        confirmpass = confirmpassword.getText().toString().trim();
        phone = phoneno.getText().toString().trim();
        if(phone.isEmpty()){
            phoneTextLayout.setErrorEnabled(true);
            phoneTextLayout.setError("Enter phone...");
        }else if(name.isEmpty()){
            fullnameTextLayout.setErrorEnabled(true);
            fullnameTextLayout.setError("Enter name...");
        }else if(emailId.isEmpty()){
            emailTextLayout.setErrorEnabled(true);
            emailTextLayout.setError("Enter email...");
        }else if(password1.isEmpty()){
            passwordTextLayout.setErrorEnabled(true);
            passwordTextLayout.setError("Enter password...");
        }else if(confirmpass.isEmpty()){
            confirmpasswordTextLayout.setErrorEnabled(true);
            confirmpasswordTextLayout.setError("Enter confirm password...");
        }else if(password1.length()<=5){
            passwordTextLayout.setErrorEnabled(true);
            passwordTextLayout.setError("Too Short");
        }else if(!password1.equals(confirmpass)){
            confirmpasswordTextLayout.setErrorEnabled(true);
            confirmpasswordTextLayout.setError("Password and confirm password should be same.");
        }else{

            String arr[] = name.split(" ", 2);
            String firstname = arr[0];
            String lastname = arr[1];

            RegisterUser registerUser = new RegisterUser();
            registerUser.setFirstname(firstname);
            registerUser.setLastname(lastname);
            registerUser.setEmail(emailId);
            registerUser.setPassword(confirmpass);

            // RegisterUserApi(registerUser);


            Toast.makeText(SignupActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
        }
    }

    public void RegisterUserApi(RegisterUser registerUser){
        try {
            if (Utility.isInternetAvailable(SignupActivity.this)){
                try {
                    pdialog = new LogoProgressDialog(SignupActivity.this);
                    pdialog.setProgress("Please Wait...");

                    Map<String, String> params = new HashMap();
                    params.put("firstname",registerUser.getFirstname());
                    params.put("lastname", registerUser.getLastname());
                    params.put("email", registerUser.getEmail());
                    params.put("phone", registerUser.getPhone());
                    params.put("state", registerUser.getState());
                    params.put("city", registerUser.getCity());
                    params.put("address", registerUser.getAddress());
                    params.put("zip", registerUser.getZip());
                    params.put("password", registerUser.getPassword());

                    JSONObject parameters = new JSONObject(params);
                    RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
                    requestQueue.getCache().clear();
                    JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, Server_URL.register_user_URL, parameters, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getBoolean("success")){

                                    Intent i = new Intent(SignupActivity.this, MainViewActivity.class);
                                    i.putExtra(MainViewActivity.EXTRA_ADDRESS,"");
                                    i.putExtra(MainViewActivity.EXTRA_AREA_ADDRESS,"");
                                    startActivity(i);

                                }else{
                                    Utility.dialogbox(SignupActivity.this,"Error",response.getJSONObject("data").getString("error"));
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
                Toast.makeText(SignupActivity.this, "Internet Required", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Log.d("Api Error",""+ex);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.phone:
                phoneTextLayout.setErrorEnabled(false);
                break;
            case R.id.fullname:
                fullnameTextLayout.setErrorEnabled(false);
                break;
            case R.id.email:
                emailTextLayout.setErrorEnabled(false);
                break;
            case R.id.password:
                passwordTextLayout.setErrorEnabled(false);
                break;
            case R.id.confirmpassword:
                confirmpasswordTextLayout.setErrorEnabled(false);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}