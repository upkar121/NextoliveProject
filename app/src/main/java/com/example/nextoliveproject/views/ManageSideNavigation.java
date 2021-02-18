package com.example.nextoliveproject.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewParent;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.nextoliveproject.Adapter.CustomExpandableListAdapter;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.models.ExpandedMenuModel;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageSideNavigation extends AppCompatActivity {
    private final static int REQUEST_PERMISSION_REQ_CODE = 34, REQUEST_PERMISSION_CAM_CODE = 35;
    private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
    List<ExpandedMenuModel> listDataHeader;
    public static RelativeLayout moreMenus;
    HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listDataChild;
    HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listDataChildOfChild;//3rd level
    List<ExpandedMenuModel> listDataHeaderThirdLevel = new ArrayList<>();//3rd level
    private File outPutFile = null;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ExpandableListView expandableList;
    CustomExpandableListAdapter mMenuAdapter;
    Activity activity;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_REQ_CODE);
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_REQ_CODE);
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAM_CODE);
        }

        initView();
    }

    public void initView() {
        moreMenus = (RelativeLayout) findViewById(R.id.moreMenus);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);

        ActionBarDrawerToggle mDrawerToggle;
        setTitle("");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {

                public void onDrawerClosed(View view) {

                }

                public void onDrawerOpened(View drawerView) {

                }
            };
            if(drawerLayout!=null){
                drawerLayout.setDrawerListener(mDrawerToggle);
            }

        }

        ManageSideNavigation();
        ImageView goBack = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
            }
        });
    }
    private void ManageSideNavigation() {

        prepareListData();

        mMenuAdapter = new CustomExpandableListAdapter(this, listDataHeader,
                listDataChild, expandableList, listDataChildOfChild, listDataHeaderThirdLevel);
        expandableList.setAdapter(mMenuAdapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                return false;
            }

        });

        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {


                return false;
            }
        });//1st level

    }

    private void prepareListData() {

        try {
            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<>();

            ExpandedMenuModel item0 = new ExpandedMenuModel();
            item0.setIconName(getResources().getString(R.string.editProfile));
            item0.setIconImg(R.drawable.user_account);
            listDataHeader.add(item0);

            ExpandedMenuModel item1 = new ExpandedMenuModel();
            item1.setIconName(getResources().getString(R.string.myOrders));
            item1.setIconImg(R.drawable.playlist);
            listDataHeader.add(item1);

            ExpandedMenuModel item2 = new ExpandedMenuModel();
            item2.setIconName(getResources().getString(R.string.myCarts));
            item2.setIconImg(R.drawable.cart);
            listDataHeader.add(item2);

            ExpandedMenuModel item3 = new ExpandedMenuModel();
            item3.setIconName(getResources().getString(R.string.wallet));
            item3.setIconImg(R.drawable.wallet);
            listDataHeader.add(item3);

            ExpandedMenuModel item4 = new ExpandedMenuModel();
            item4.setIconName(getResources().getString(R.string.notifications));
            item4.setIconImg(R.drawable.comment);
            listDataHeader.add(item4);

            ExpandedMenuModel item5 = new ExpandedMenuModel();
            item5.setIconName(getResources().getString(R.string.myAddresses));
            item5.setIconImg(R.drawable.marker);
            listDataHeader.add(item5);


            ExpandedMenuModel item6 = new ExpandedMenuModel();
            item6.setIconName(getResources().getString(R.string.contactUs));
            item6.setIconImg(R.drawable.phone_call);
            listDataHeader.add(item6);

            ExpandedMenuModel item8 = new ExpandedMenuModel();
            item8.setIconName(getResources().getString(R.string.logout));
            item8.setIconImg(R.drawable.hold);
            listDataHeader.add(item8);


            // Adding child data


            List<ExpandedMenuModel> heading1 = new ArrayList<ExpandedMenuModel>();
            ExpandedMenuModel subchild1 = new ExpandedMenuModel();
            subchild1.setIconName(getResources().getString(R.string.store));
            //subchild1.setIconImg(R.drawable.most_viewed);
            heading1.add(subchild1);

            subchild1 = new ExpandedMenuModel();
            subchild1.setIconName(getResources().getString(R.string.market));
            //  subchild1.setIconImg(R.drawable.most_wowed);
            heading1.add(subchild1);

            subchild1 = new ExpandedMenuModel();
            subchild1.setIconName(getResources().getString(R.string.online_food_ordering));
            //  subchild1.setIconImg(R.drawable.most_wowed);
            heading1.add(subchild1);

            subchild1 = new ExpandedMenuModel();
            subchild1.setIconName(getResources().getString(R.string.tickets));
            //  subchild1.setIconImg(R.drawable.most_wowed);
            heading1.add(subchild1);

            subchild1 = new ExpandedMenuModel();
            subchild1.setIconName(getResources().getString(R.string.funFair));
            //  subchild1.setIconImg(R.drawable.most_wowed);
            heading1.add(subchild1);

            subchild1 = new ExpandedMenuModel();
            subchild1.setIconName(getResources().getString(R.string.BankQueueOrder));
            //  subchild1.setIconImg(R.drawable.most_wowed);
            heading1.add(subchild1);

            /////////////////////////////////////////////////////////

            List<ExpandedMenuModel> heading2 = new ArrayList<ExpandedMenuModel>();

            ExpandedMenuModel subchild2 = new ExpandedMenuModel();
            subchild2.setIconName(getResources().getString(R.string.store));
            //subchild1.setIconImg(R.drawable.most_viewed);
            heading2.add(subchild2);

            subchild2 = new ExpandedMenuModel();
            subchild2.setIconName(getResources().getString(R.string.market));
            //  subchild1.setIconImg(R.drawable.most_wowed);
            heading2.add(subchild2);

            subchild2 = new ExpandedMenuModel();
            subchild2.setIconName(getResources().getString(R.string.online_food_ordering));
            //  subchild1.setIconImg(R.drawable.most_wowed);
            heading2.add(subchild2);

            subchild2 = new ExpandedMenuModel();
            subchild2.setIconName(getResources().getString(R.string.tickets));
            //  subchild1.setIconImg(R.drawable.most_wowed);
            heading2.add(subchild2);

            subchild2 = new ExpandedMenuModel();
            subchild2.setIconName(getResources().getString(R.string.funFair));
            //  subchild1.setIconImg(R.drawable.most_wowed);
            heading2.add(subchild2);


            listDataChild.put(listDataHeader.get(1), heading1);
            listDataChild.put(listDataHeader.get(2), heading2);

            for (ExpandedMenuModel expandedMenuModel : listDataChild.get(listDataHeader.get(1))) {
                listDataHeaderThirdLevel.add(expandedMenuModel);
            }


        } catch (Exception ex) {

            Log.d("Exception",""+ex);
        }
    }

    public void CloseMoreMenus(ViewParent parent) {

        moreMenus.setVisibility(View.GONE);
        GridLayout gridLLayout = (GridLayout) parent;
        for (int i = 0; i < gridLLayout.getChildCount(); i++) {
            try {
                View child = gridLLayout.getChildAt(i);
                child.setVisibility(View.INVISIBLE);

            } catch (Exception ex) {

            }

        }
    }
    @SuppressLint("WrongConstant")
    public void navClick(View view) {
        drawerLayout.openDrawer(Gravity.START);
    }
    public void CloseMoreMenusLL(ViewParent parent) {

        moreMenus.setVisibility(View.GONE);
        LinearLayout gridLLayout = (LinearLayout) parent;
        for (int i = 0; i < gridLLayout.getChildCount(); i++) {
            try {
                View child = gridLLayout.getChildAt(i);
                child.setVisibility(View.INVISIBLE);

            } catch (Exception ex) {

            }

        }
    }
}