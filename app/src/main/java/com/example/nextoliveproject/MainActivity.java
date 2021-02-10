package com.example.nextoliveproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.nextoliveproject.Adapter.SectionsPagerAdapter;
import com.example.nextoliveproject.views.SideNavigationMasterActivity;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends SideNavigationMasterActivity {

        private ViewPager mViewPager;
        private SectionsPagerAdapter mSectionsPagerAdapter;
        Activity activity;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            super.setContentView(R.layout.activity_main);
            activity = this;

            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());        // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        }

        @Override
        public void onBackPressed() {
            super.onBackPressed();

            Log.e("BACKPRESS", "PRESSED");

        }
}