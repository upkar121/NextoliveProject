package com.example.nextoliveproject.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.nextoliveproject.views.fragments.FoodDeliveryFragment;
import com.example.nextoliveproject.views.fragments.ResturantFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // return HomeFragment.newInstance(position + 1);
        int currentpos = position + 1;
        Fragment fragmentActivity = new Fragment();
        if (currentpos == 1) {
            fragmentActivity = new FoodDeliveryFragment();
        } else if (currentpos == 2) {
            fragmentActivity = new ResturantFragment();
        } else if (currentpos == 3) {
           // fragmentActivity = new CampaignsPageFragment();
        }
        return fragmentActivity;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }
}