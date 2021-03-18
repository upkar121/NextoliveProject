package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.nextoliveproject.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.internal.$Gson$Preconditions;
import com.tiper.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class AddLocationActivity extends AppCompatActivity implements View.OnFocusChangeListener,View.OnClickListener {
    private TextInputLayout addresstextlayout,statetextlayout,citytextlayout,zipcodetextlayout;
    private TextInputEditText addressText,stateText,cityText,zipcodeText;
    private MaterialSpinner typeSpinner;
    private LinearLayout addAddress;
    private ImageView back;
    List<String> type ;
    String selectedValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        findViewByIds();
         type = new ArrayList<>();
         type.add("Home");
         type.add("Work");
         type.add("Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,type);
        typeSpinner.setSelection(0);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {

                selectedValue = type.get(i);

            }
        });

        addAddress.setOnClickListener(this);
        back.setOnClickListener(this);

        addressText.setOnFocusChangeListener(this);
        stateText.setOnFocusChangeListener(this);
        cityText.setOnFocusChangeListener(this);
        zipcodeText.setOnFocusChangeListener(this);
    }

    public void findViewByIds(){
        addAddress = findViewById(R.id.addAddress);
        back = findViewById(R.id.main_back);
        typeSpinner  = findViewById(R.id.type_spinner);
        addresstextlayout = findViewById(R.id.addresstextlayout);
        statetextlayout = findViewById(R.id.statetextlayout);
        citytextlayout = findViewById(R.id.citytextlayout);
        zipcodetextlayout = findViewById(R.id.zipcodetextlayout);
        addressText = findViewById(R.id.address);
        stateText = findViewById(R.id.state);
        cityText = findViewById(R.id.city);
        zipcodeText = findViewById(R.id.zipcode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_back:
                onBackPressed();
                break;
            case R.id.addAddress:
                verifydetails();
                break;
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.address:
                addresstextlayout.setErrorEnabled(false);
                break;
            case R.id.state:
                statetextlayout.setErrorEnabled(false);
                break;
            case R.id.city:
                citytextlayout.setErrorEnabled(false);
                break;
            case R.id.zipcode:
                zipcodetextlayout.setErrorEnabled(false);
                break;
        }
    }


    public void verifydetails(){
        String address = addressText.getText().toString().trim();
        String state = stateText.getText().toString().trim();
        String city = cityText.getText().toString().trim();
        String zipcode = zipcodeText.getText().toString().trim();

        if(address.isEmpty()){
            addresstextlayout.setErrorEnabled(true);
            addresstextlayout.setError("*Required Field Address");
        }else if(state.isEmpty()){
            statetextlayout.setErrorEnabled(true);
            statetextlayout.setError("*Required Field State");
        }else if(city.isEmpty()){
            citytextlayout.setErrorEnabled(true);
            citytextlayout.setError("*Required Field City");
        }else if(zipcode.isEmpty()){
            zipcodetextlayout.setErrorEnabled(true);
            zipcodetextlayout.setError("*Required Field Zipcode");
        }else{

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}