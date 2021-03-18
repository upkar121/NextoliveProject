package com.example.nextoliveproject.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nextoliveproject.R;
import com.example.nextoliveproject.utility.Utility;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static com.example.nextoliveproject.views.login.LoginActivity.REQUEST_PERMISSIONS_REQUEST_CODE;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener {

    public static int REQUEST_CHECK_SETTINGS = 100;
    public ImageView main_back;
    public RelativeLayout relative_turned_off, relative_turned_on;
    public Button turn_on_button;
    private LinearLayout manually;

    @Override
    protected void onStart() {
        super.onStart();
       createLocationRequest();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        findViewByIds();

        manually.setOnClickListener(this);
        main_back.setOnClickListener(this);
        relative_turned_off.setOnClickListener(this);
        relative_turned_on.setOnClickListener(this);


    }

    public void findViewByIds() {
        main_back = findViewById(R.id.main_back);
        relative_turned_on = findViewById(R.id.relative_turned_on);
        relative_turned_off = findViewById(R.id.relative_turned_off);
        turn_on_button = findViewById(R.id.turn_on_button);
        manually = findViewById(R.id.manually);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_back:
                onBackPressed();
                break;
            case R.id.relative_turned_off:
                createLocationRequest();
                break;
            case R.id.relative_turned_on:
                startActivity(new Intent(LocationActivity.this,MapLocActivity.class));
                break;
            case R.id.manually:
                startActivity(new Intent(LocationActivity.this,AddLocationActivity.class));
                break;
        }
    }

    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                relative_turned_off.setVisibility(View.GONE);
                relative_turned_on.setVisibility(View.VISIBLE);

                Log.d("location settings",locationSettingsResponse.toString());
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().

                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(LocationActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQUEST_CHECK_SETTINGS){

            if(resultCode==RESULT_OK){
                relative_turned_on.setVisibility(View.VISIBLE);
                relative_turned_off.setVisibility(View.GONE);
                //if user allows to open gps
                Log.d("result ok",data.toString());

            }else if(resultCode==RESULT_CANCELED){
                relative_turned_on.setVisibility(View.GONE);
                relative_turned_off.setVisibility(View.VISIBLE);
                // in case user back press or refuses to open gps
                Log.d("result cancelled",data.toString());
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {

                } else {
                   // Toast.makeText(this, "Location permission declined", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}