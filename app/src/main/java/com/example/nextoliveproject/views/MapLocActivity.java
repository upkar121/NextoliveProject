package com.example.nextoliveproject.views;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nextoliveproject.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;


public class MapLocActivity extends FragmentActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    LinearLayout btnConfirmLoc;
    private GoogleMap mMap;
    public TextView address_text;

    //Get Address from pin
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;

    //New variables for current place picker
//    private PlacesClient mPlacesClient;
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
//
//    // Used for selecting the current place.
//    private static final int M_MAX_ENTRIES = 5;
//    private String[] mLikelyPlaceNames;
//    private String[] mLikelyPlaceAddresses;
//    private String[] mLikelyPlaceAttributions;
//    private LatLng[] mLikelyPlaceLatLngs;

    private final String TAG = MapLocActivity.class.getSimpleName();
    private String address;

    ImageView ivClose;
    LinearLayout btnSave;

    Intent mapResult = new Intent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_loc);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        // ivClose = findViewById(R.id.iv_close);
        btnSave = findViewById(R.id.btnConfirmLoc);
        address_text = findViewById(R.id.address_textview);

        mapFragment.getMapAsync(this);

//        String apiKey = getString(R.string.google_maps_key);
//        Places.initialize(getApplicationContext(), apiKey);
//        mPlacesClient = Places.createClient(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

//        pickCurrentPlace();

        configureCameraIdle();

//        ivClose.setOnClickListener(v -> {
//            setResult(RESULT_CANCELED, mapResult);
//            finish();
//        });


        btnSave.setOnClickListener(v -> {
            mapResult.putExtra("address", address);
            setResult(RESULT_OK, mapResult);
            finish();
        });

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnCameraIdleListener(onCameraIdleListener);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                configureCameraIdle();
            }
        });
        // Prompt the user for permission.
        getLocationPermission();

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
                    if (addressList != null && addressList.size() > 0) {
                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();
                        if (!locality.isEmpty() && !country.isEmpty()) {
//                            resutText.setText(locality + "  " + country);
                            address = locality + "  " + country;
                            address_text.setText(address);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
    }

//    private void getCurrentPlaceLikelihoods() {
//        // Use fields to define the data types to return.
//        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
//                Place.Field.LAT_LNG);
//
//        // Get the likely places - that is, the businesses and other points of interest that
//        // are the best match for the device's current location.
//        @SuppressWarnings("MissingPermission") final FindCurrentPlaceRequest request =
//                FindCurrentPlaceRequest.builder(placeFields).build();
//        Task<FindCurrentPlaceResponse> placeResponse = mPlacesClient.findCurrentPlace(request);
//        placeResponse.addOnCompleteListener(this,
//                new OnCompleteListener<FindCurrentPlaceResponse>() {
//                    @RequiresApi(api = Build.VERSION_CODES.O)
//                    @Override
//                    public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
//                        if (task.isSuccessful()) {
//                            FindCurrentPlaceResponse response = task.getResult();
//                            // Set the count, handling cases where less than 5 entries are returned.
//                            int count;
//                            if (response.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
//                                count = response.getPlaceLikelihoods().size();
//                            } else {
//                                count = M_MAX_ENTRIES;
//                            }
//
//                            int i = 0;
//                            mLikelyPlaceNames = new String[count];
//                            mLikelyPlaceAddresses = new String[count];
//                            mLikelyPlaceAttributions = new String[count];
//                            mLikelyPlaceLatLngs = new LatLng[count];
//
//                            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
//                                Place currPlace = placeLikelihood.getPlace();
//                                mLikelyPlaceNames[i] = currPlace.getName();
//                                mLikelyPlaceAddresses[i] = currPlace.getAddress();
//                                mLikelyPlaceAttributions[i] = (currPlace.getAttributions() == null) ?
//                                        null : String.join(" ", currPlace.getAttributions());
//                                mLikelyPlaceLatLngs[i] = currPlace.getLatLng();
//
//                                String currLatLng = (mLikelyPlaceLatLngs[i] == null) ?
//                                        "" : mLikelyPlaceLatLngs[i].toString();
//
//                                Log.i(TAG, "Place " + currPlace.getName()
//                                        + " has likelihood: " + placeLikelihood.getLikelihood()
//                                        + " at " + currLatLng);
//
//                                i++;
//                                if (i > (count - 1)) {
//                                    break;
//                                }
//                            }
//
//
//                            // COMMENTED OUT UNTIL WE DEFINE THE METHOD
//                            // Populate the ListView
////                            fillPlacesList();
//                        } else {
//                            Exception exception = task.getException();
//                            if (exception instanceof ApiException) {
//                                ApiException apiException = (ApiException) exception;
//                                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
//                            }
//                        }
//                    }
//                });
//    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Toast.makeText(this, "I am here", Toast.LENGTH_SHORT).show();
                return;
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
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        }

//                        getCurrentPlaceLikelihoods();
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

        if (mLocationPermissionGranted) {
            getDeviceLocation();
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");

            // Add a default marker, because the user hasn't selected a place.
//            mMap.addMarker(new MarkerOptions()
//                    .title(getString(R.string.default_info_title))
//                    .position(mDefaultLocation)
//                    .snippet(getString(R.string.default_info_snippet)));

            // Prompt the user for permission.
            getLocationPermission();
        }
    }
}