package com.example.nextoliveproject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nextoliveproject.Helper.SharedData;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.utility.Utility;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import java.util.List;

import static com.example.nextoliveproject.views.login.LoginActivity.REQUEST_PERMISSIONS_REQUEST_CODE;


public class MapLocActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {
    public static String EXTRA_ADDRESS = "Address";
    public static String EXTRA_AREA_ADDRESS = "Area_Address";
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    public TextView address_text,address_first;
    public ImageView main_back;
    public RelativeLayout current_location;
    public Button change_current;
    View mapView;
    //Get Address from pin
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;

    //New variables for current place picker
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private final String TAG = MapLocActivity.class.getSimpleName();
    private String address ,area_address;

    LinearLayout btnSave;

    public GroundOverlay circle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_loc);

        findViewByIds();

        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        configureCameraIdle();

        main_back.setOnClickListener(this);
        current_location.setOnClickListener(this);
        change_current.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main_back:
                onBackPressed();
                finish();
                break;
            case R.id.relative_current_location:
                getDeviceLocation();
                break;
            case R.id.change_current:
                startActivity(new Intent(MapLocActivity.this,LocationActivity.class));
                finish();
                break;
            case R.id.btnConfirmLoc:
                Intent mapResult = new Intent(MapLocActivity.this,MainViewActivity.class);
                mapResult.putExtra(EXTRA_ADDRESS, address);
                mapResult.putExtra(EXTRA_AREA_ADDRESS, area_address);
                startActivity(mapResult);
                finish();
                break;

        }
    }

    public void findViewByIds() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        btnSave = findViewById(R.id.btnConfirmLoc);
        address_text = findViewById(R.id.address_textview);
        address_first = findViewById(R.id.address_first);
        main_back = findViewById(R.id.main_back);
        current_location = findViewById(R.id.relative_current_location);
        change_current = findViewById(R.id.change_current);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(onCameraIdleListener);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
        }
        mMap.setMyLocationEnabled(true);

        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 1, 1);
            locationButton.setVisibility(View.GONE);
        }

        pickCurrentPlace();

    }

    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                LatLng latLng = mMap.getCameraPosition().target;
                Geocoder geocoder = new Geocoder(MapLocActivity.this);

                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

                    SharedData.latitude(MapLocActivity.this,""+latLng.latitude);
                    SharedData.longitude(MapLocActivity.this,""+latLng.longitude);

                    if (addressList != null && addressList.size() > 0) {
                        area_address = addressList.get(0).getLocality();
                        String locality = addressList.get(0).getAddressLine(0);
                        SharedData.locality(MapLocActivity.this,locality);
                        String country = addressList.get(0).getCountryName();
                        if (!locality.isEmpty() && !country.isEmpty()) {
                            address = locality + "  " + country;
                            address_text.setText(address);
                        }
                        if(!area_address.isEmpty()){
                            address_first.setText(area_address);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    private void getDeviceLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions();
            }
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = location;
                            Log.d(TAG, "Latitude: " + mLastKnownLocation.getLatitude());
                            Log.d(TAG, "Longitude: " + mLastKnownLocation.getLongitude());

                            LatLng latLng =  new LatLng(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
                            if(circle == null){
                                circleOverlay(latLng);
                            }else{
                                circle.remove();
                                circle = null;
                                circleOverlay(latLng);
                            }



                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
//                            mMap.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));

                            Utility.dialogbox(MapLocActivity.this,"Location Error","* Check weather your location is on or not.\n* Check gps location is on.\n* On gps to access your current location.");

                        }

                    }
                });
        } catch (Exception e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void pickCurrentPlace() {
        if (mMap == null) {
            return;
        }
        getDeviceLocation();
    }


    private void requestPermissions() {
// Request permission
        ActivityCompat.requestPermissions(MapLocActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                } else {
                    Toast.makeText(this, "Location permission declined", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void circleOverlay(LatLng searchStopPoint) {
        // The drawable to use for the circle
        GradientDrawable d = new GradientDrawable();
        d.setShape(GradientDrawable.OVAL);
        d.setSize(1000, 1000);
        d.setColor(Color.parseColor("#1F678F"));
        d.setStroke(5, Color.TRANSPARENT);

        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth()
                , d.getIntrinsicHeight()
                , Bitmap.Config.ARGB_8888);

        // Convert the drawable to bitmap
        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        d.draw(canvas);

        // Radius of the circle
        final int radius = 200;

        // Add the circle to the map
        circle = mMap.addGroundOverlay(new GroundOverlayOptions()
                .position(searchStopPoint, 2 * radius).image(BitmapDescriptorFactory.fromBitmap(bitmap)));
        circle.setTransparency(0.5f);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setIntValues(0, radius);
        valueAnimator.setDuration(3000);
        valueAnimator.setEvaluator(new IntEvaluator());
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedFraction = valueAnimator.getAnimatedFraction();
                circle.setDimensions(animatedFraction * radius * 2);
            }
        });
        valueAnimator.start();
    }
}