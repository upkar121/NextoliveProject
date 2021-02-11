package com.example.nextoliveproject.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nextoliveproject.R;
import com.example.nextoliveproject.utility.Utility;
import com.example.nextoliveproject.views.fragments.EcommerceFragment;
import com.example.nextoliveproject.views.fragments.FoodDeliveryFragment;
import com.example.nextoliveproject.views.fragments.RechargeFragment;
import com.example.nextoliveproject.views.fragments.ResturantFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainViewActivity extends ManageSideNavigationActivity implements View.OnClickListener {
    BottomNavigationView topavigation;
    ImageView sidenav;
    TextView locality,address;
    LinearLayout locationLayout,offerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        findViews();
        setListners();
        //Loading default fragment
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.fragment_container,new FoodDeliveryFragment()).commit();
    }
    private void findViews() {
        topavigation = findViewById(R.id.top_navigation);
        sidenav = findViewById(R.id.side_nav);
        locationLayout = findViewById(R.id.location_layout);
        offerLayout = findViewById(R.id.offer_layout);
        locality = findViewById(R.id.locality);
        address = findViewById(R.id.address);
    }

    private void setListners() {
        topavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        selectedFragment = new FoodDeliveryFragment();
                        break;
                    case R.id.action_search:
                        selectedFragment = new ResturantFragment();
                        break;
                    case R.id.action_recharge:
                        selectedFragment = new RechargeFragment();
                        break;
                    case R.id.action_ecomm:
                        selectedFragment = new EcommerceFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container,selectedFragment).commit();
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.offer_layout:
                break;
            case R.id.location_layout:
                break;
        }
    }



    public void handleMenuClicks(View view) {
    }
}