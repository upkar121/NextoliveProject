package com.example.nextoliveproject.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.BuildConfig;
import com.example.nextoliveproject.Adapter.CropingOptionAdapter;
import com.example.nextoliveproject.Adapter.CustomExpandableListAdapter;
import com.example.nextoliveproject.Helper.CropingOption;
import com.example.nextoliveproject.Helper.RoundedImageView;
import com.example.nextoliveproject.Helper.SharedData;
import com.example.nextoliveproject.MainActivity;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.models.ExpandedMenuModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.view.View.GONE;

public class SideNavigationMasterActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    CustomExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList, expandableListchild;
    List<ExpandedMenuModel> listDataHeader;
    public static RelativeLayout moreMenus;
    HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listDataChild;
    HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listDataChildOfChild;//3rd level
    List<ExpandedMenuModel> listDataHeaderThirdLevel = new ArrayList<>();//3rd level
    public static GridLayout mainGrid, bankGrid, reservationGrid, ticketGrid;
    public static LinearLayout ll_bankGrid, ll_reservationGrid, ll_ticketGrid;
    int i = 0;
    Activity activity;
    Intent intent;
    String UserId, encoded;
    RoundedImageView profileImage;
    private final static int REQUEST_PERMISSION_REQ_CODE = 34, REQUEST_PERMISSION_CAM_CODE = 35;
    private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
    private File outPutFile = null;
    Uri uri;
    TextView txtGroupName;
    public static TabLayout tabLayout;
    public static ViewPager container;
    LinearLayout ll_main_search, ll_Search, ll_advSearch;
    int count = 0;
    Button btn_store, btn_merchant, btn_restraunt;
    AlertDialog dialog;
    //public List<Merchant> merchantList = new ArrayList<>();
    String btn_pressed_module = "";
    public static String Ischecked;
    ImageView iv_ll_bankGrid, iv_ll_reservationGrid, iv_ll_ticketGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

        int countTemp = getIntent().getIntExtra("count", 0);
        if (countTemp != 0) {
            count = countTemp;
        }

    }

    public void GetCustomerInformation() {
//        ApiService apiService = ApiClient.getClient(activity)
//                .create(ApiService.class);
//
//// Fetching all notifications
//        apiService.getCustomerInformation(SharedData.User_Id(activity))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<UserModel>() {
//                    @Override
//                    public void onSuccess(UserModel userModel) {
//
//                        try {
//                            txtCurrentRank.setText(userModel.rankName);
//                            txtNextRank.setText(userModel.nextRankName);
//                            txtGroupName.setText(userModel.groupName);
//                            Picasso.with(activity).load(userModel.cdnImage).placeholder(R.drawable.user_icon_header).error(R.drawable.user_icon_header).into(profileImage);
//
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        new SnackMsg(activity, e.getMessage());
//
//                        try {
//                            HttpException ex = (HttpException) e;
//                            if (!TextUtils.isEmpty(ex.response().errorBody().string()) &&
//                                    ex.response().errorBody().string().toLowerCase().contains("access denied")) {
//                                ApiClient.RefreshToken(activity);
//                            } else {
//                                // new SnackMsg(MyAddressesActivity.this, "else called");
//                            }
//                        } catch (Exception ex1) {
//                            ex1.printStackTrace();
//                        }
//                    }
//                });
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

        if (SideNavigationMasterActivity.tabLayout != null) {
            SideNavigationMasterActivity.tabLayout.setEnabled(true);
        }
        if (SideNavigationMasterActivity.container != null) {
            SideNavigationMasterActivity.container.setEnabled(true);
        }

    }

    public void initView() {
        moreMenus = (RelativeLayout) findViewById(R.id.moreMenus);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        bankGrid = (GridLayout) findViewById(R.id.bankGrid);
        ll_bankGrid = (LinearLayout) findViewById(R.id.ll_bankGrid);
        reservationGrid = (GridLayout) findViewById(R.id.reservationGrid);
        ll_reservationGrid = (LinearLayout) findViewById(R.id.ll_reservationGrid);
        ticketGrid = (GridLayout) findViewById(R.id.ticketGrid);
        ll_ticketGrid = (LinearLayout) findViewById(R.id.ll_ticketGrid);
        //
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ll_main_search = (LinearLayout) findViewById(R.id.ll_main_search);
        ll_Search = (LinearLayout) findViewById(R.id.ll_Search);
        ll_advSearch = (LinearLayout) findViewById(R.id.ll_advSearch);
        container = (ViewPager) findViewById(R.id.container);
        btn_store = (Button) findViewById(R.id.btn_store);
        btn_merchant = (Button) findViewById(R.id.btn_merchant);
        btn_restraunt = (Button) findViewById(R.id.btn_restraunt);
        iv_ll_bankGrid = (ImageView) findViewById(R.id.iv_ll_bankGrid);
        iv_ll_reservationGrid = (ImageView) findViewById(R.id.iv_ll_reservationGrid);
        iv_ll_ticketGrid = (ImageView) findViewById(R.id.iv_ll_ticketGrid);


        ActionBarDrawerToggle mDrawerToggle;
        setTitle("");
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            // actionBar.setDisplayHomeAsUpEnabled(true);
            mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {

                public void onDrawerClosed(View view) {
                    // supportInvalidateOptionsMenu();
                    //drawerOpened = false;
                }

                public void onDrawerOpened(View drawerView) {
                    //supportInvalidateOptionsMenu();
                    //drawerOpened = true;
                    // drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);//DrawerLayout instance
                }
            };
            // mDrawerToggle.setDrawerIndicatorEnabled(true);
            if(drawerLayout!=null){
                drawerLayout.setDrawerListener(mDrawerToggle);
            }

            //mDrawerToggle.syncState();
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

        LinearLayout headerLayout = (LinearLayout) navigationView.getHeaderView(0);
        profileImage = (RoundedImageView) headerLayout.findViewById(R.id.profileIcon);
        TextView userName = (TextView) headerLayout.findViewById(R.id.userName);
        //userName.setText(SharedData.Mobile(activity));
        userName.setText("Nextolive");
        UserId = SharedData.User_Id(this);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDiglog(v);
            }
        });

        txtGroupName = headerLayout.findViewById(R.id.txtGroupName);

        GetCustomerInformation();
    }

    private void ManageSideNavigation() {

        prepareListData();

        mMenuAdapter = new CustomExpandableListAdapter(this, listDataHeader,
                listDataChild, expandableList, listDataChildOfChild, listDataHeaderThirdLevel);
        expandableList.setAdapter(mMenuAdapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                if (i == 2 && i1 == 3) {
//                    //  intent = new Intent(SideNavigationMasterActivity.this, CinemaCartActivity.class);
//                    //  intent = new Intent(SideNavigationMasterActivity.this, ThreaterCartActivity.class);
//                    //  intent = new Intent(SideNavigationMasterActivity.this, ConcertCartActivity.class);
//                    /* intent = new Intent(SideNavigationMasterActivity.this, EventCartActivity.class);*/
//                    intent = new Intent(SideNavigationMasterActivity.this, MuseumCartActivity.class);
//
//
//                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else if (i == 2 && i1 == 0) {
//                    intent = new Intent(activity, Cart_StoreActivity.class);
//                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else if (i == 2 && i1 == 1) {
//                    intent = new Intent(activity, Cart_MarketActivity.class);
//                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else if (i == 2 && i1 == 2) {
//                    intent = new Intent(activity, Cart_FoodOrderingActivity.class);
//                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else if (i == 2 && i1 == 4) {
//                    intent = new Intent(activity, Cart_FunFairSystemActivity.class);
//                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else if (i == 1 && i1 == 3) {
//                    //intent = new Intent(SideNavigationMasterActivity.this, TicketsCinemaOrderActivity.class);
//                    //intent = new Intent(SideNavigationMasterActivity.this, TicketsMuseumOrderActivity.class);
//                    intent = new Intent(SideNavigationMasterActivity.this, TicketsEventOrderActivity.class);
//                    //intent = new Intent(SideNavigationMasterActivity.this, Order_HotelActivity.class);//HOTEL ORDERS
//
//                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else if (i == 1 && i1 == 0) {
//                    intent = new Intent(activity, Order_StoreActivity.class);
//                    startActivity(intent);
//                } else if (i == 1 && i1 == 1) {
//                    intent = new Intent(activity, Order_MarketActivity.class);
//                    startActivity(intent);
//                } else if (i == 1 && i1 == 2) {
//                    intent = new Intent(activity, Order_FoodOrderingActivity.class);
//                    startActivity(intent);
//                } else if (i == 1 && i1 == 4) {
//                    intent = new Intent(activity, Order_FunFairSystemActivity.class);
//                    startActivity(intent);
//
//                    //BankQueue Order
//                } else if (i == 1 && i1 == 5) {
//                    intent = new Intent(activity, MyOrder.class);
//                    startActivity(intent);
//                }

                return false;
            }

        });

        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

//                if (i == 0) {
//                    Intent intent = new Intent(SideNavigationMasterActivity.this, ProfileActivity.class);
//                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else if (i == 3) {
//                    Intent intent = new Intent(SideNavigationMasterActivity.this, WalletListActivity.class);
//                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else if (i == 4) {
//                    Intent intent = new Intent(SideNavigationMasterActivity.this, NotificationsActivity.class);
//                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else if (i == 5) {
//                    Intent intent = new Intent(SideNavigationMasterActivity.this, MyAddressesActivity.class);
//                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else if (i == 6) {
//                    Intent intent = new Intent(SideNavigationMasterActivity.this, ContactUsActivity.class);
//                    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                } else if (i == 8) {
//
//                    try {
//                        SharedPreferences.Editor sha = getApplicationContext().getSharedPreferences(ConstantVariable.SHARED_DATABASE_NAME, MODE_PRIVATE).edit();
//                        sha.clear();
//                        sha.apply();
//                        ActivityCompat.finishAffinity(SideNavigationMasterActivity.this);
//                        startActivity(new Intent(SideNavigationMasterActivity.this, LoginActivity.class));
//
//                    } catch (Exception ex) {
//                        Toast.makeText(SideNavigationMasterActivity.this, ""+ex, Toast.LENGTH_SHORT).show();
//                    }
//                } else if (i == 7) {
//
//
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                    // Get the layout inflater
//                    LayoutInflater inflater = getLayoutInflater();
//                    // Inflate and set the layout for the dialog
//                    // Pass null as the parent view because its going in the
//                    // dialog layout
//                    builder.setTitle(activity.getResources().getString(R.string.Choose_Any_Language));
//                    View viewdialog = inflater.inflate(R.layout.language_change, null);
//                    builder.setView(viewdialog);
//
//                    CheckBox ch_english = viewdialog.findViewById(R.id.ch_english);
//                    CheckBox ch_persian = viewdialog.findViewById(R.id.ch_persian);
//
//                    SharedPreferences language = getSharedPreferences("Language", Context.MODE_PRIVATE);
//                    String lang = language.getString("lang", "");
//
//                    if (lang != null && lang.equalsIgnoreCase("en")) {
//                        ch_english.setChecked(true);
//                        ch_persian.setChecked(false);
//                    } else if (lang != null && lang.equalsIgnoreCase("fa")) {
//                        ch_english.setChecked(false);
//                        ch_persian.setChecked(true);
//                    } else {
//                        ch_english.setChecked(false);
//                        ch_persian.setChecked(false);
//                    }
//
//
//                    ch_english.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            // LocaleHelperOld.setLocale(MainActivity.this, mLanguageCode);
//
//                            SharedPreferences loginData = getSharedPreferences("Language", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = loginData.edit();
//                            editor.putString("lang", "en");
//                            editor.apply();
//
//                            LocaleHelper.setLocale(activity, "en");
//                            //recreate();
//                            dialog.dismiss();
//                            startActivity(new Intent(activity, MainActivity.class)
//                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                    .addFlags(FLAG_ACTIVITY_CLEAR_TASK)
//                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            );
//
//                        }
//                    });
//
//                    ch_persian.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            SharedPreferences loginData = getSharedPreferences("Language", Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = loginData.edit();
//                            editor.putString("lang", "fa");
//                            editor.apply();
//
//                            LocaleHelper.setLocale(activity, "fa");
//                            // recreate();
//                            dialog.dismiss();
//                            startActivity(new Intent(activity, MainActivity.class)
//                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                                    .addFlags(FLAG_ACTIVITY_CLEAR_TASK)
//                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            );
//                        }
//                    });
//                    builder.create();
//                    builder.setCancelable(true);
//                    dialog = builder.show();
//
//                }


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
//            listDataChild.put(listDataHeader.get(3), heading3);
//            listDataChild.put(listDataHeader.get(4), null);


            //Third level

//            listDataChildOfChild = new HashMap<>();
//
//            List<ExpandedMenuModel> threechildList = new ArrayList<ExpandedMenuModel>();
//
//            ExpandedMenuModel threerdchild3 = new ExpandedMenuModel();
//            threerdchild3.setIconName(getResources().getString(R.string.museum));
//            threechildList.add(threerdchild3);
//
//            threerdchild3 = new ExpandedMenuModel();
//            threerdchild3.setIconName(getResources().getString(R.string.cinema));
//            threechildList.add(threerdchild3);
//
//            threerdchild3 = new ExpandedMenuModel();
//            threerdchild3.setIconName(getResources().getString(R.string.theater));
//            threechildList.add(threerdchild3);
//
//            threerdchild3 = new ExpandedMenuModel();
//            threerdchild3.setIconName(getResources().getString(R.string.concerts));
//            threechildList.add(threerdchild3);
//
//            threerdchild3 = new ExpandedMenuModel();
//            threerdchild3.setIconName(getResources().getString(R.string.events));
//            threechildList.add(threerdchild3);

//            listDataChildOfChild.put(listDataChild.get(listDataHeader.get(1)).get(3), threechildList);
//            listDataChildOfChild.put(listDataChild.get(listDataHeader.get(2)).get(3), threechildList);

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

    public void handleMenuClicks(final View v) {
        final int tag = Integer.valueOf(v.getTag().toString());
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                switch (tag) {
//
//                    case 1:
//                        startActivity(new Intent(SideNavigationMasterActivity.this, RestaurantDashboardActivity.class));
//                        moreMenus.setVisibility(View.GONE);
//
//                        break;
//                    case 0:
//                        startActivity(new Intent(SideNavigationMasterActivity.this, StoreDashboardActivity.class));
//                        moreMenus.setVisibility(View.GONE);
//                        break;
//
//                    case 2:
//                        startActivity(new Intent(activity, MarketDashboardActivity.class));
//                        moreMenus.setVisibility(View.GONE);
//                        break;
//
//                    case 3:
//                        mainGrid.setVisibility(View.GONE);
//                        bankGrid.setVisibility(View.GONE);
//                        ll_bankGrid.setVisibility(GONE);
//                        reservationGrid.setVisibility(View.VISIBLE);
//                        ll_reservationGrid.setVisibility(View.VISIBLE);
//                        for (int i = 0; i < ll_reservationGrid.getChildCount(); i++) {
//                            View child = ll_reservationGrid.getChildAt(i);
//                            child.setVisibility(View.VISIBLE);
//                        }
//                        ticketGrid.setVisibility(View.GONE);
//                        ll_ticketGrid.setVisibility(GONE);
//                        new showSubMenus().execute(reservationGrid);
//                        break;
//                    case 4:
//                        mainGrid.setVisibility(View.GONE);
//                        bankGrid.setVisibility(View.GONE);
//                        ll_bankGrid.setVisibility(GONE);
////                        lotteryGrid.setVisibility(View.VISIBLE);
////                        ll_lotteryGrid.setVisibility(View.VISIBLE);
////                        for (int i = 0; i < ll_lotteryGrid.getChildCount(); i++) {
////                            View child = ll_lotteryGrid.getChildAt(i);
////                            child.setVisibility(View.VISIBLE);
////                        }
////                        reservationGrid.setVisibility(View.GONE);
////                        ll_reservationGrid.setVisibility(GONE);
////                        ticketGrid.setVisibility(View.GONE);
////                        ll_ticketGrid.setVisibility(GONE);
////                        new showSubMenus().execute(lotteryGrid);
//                        break;
//                    case 5:
//                        mainGrid.setVisibility(View.GONE);
//                        bankGrid.setVisibility(View.VISIBLE);
//                        ll_bankGrid.setVisibility(View.VISIBLE);
//                        // LinearLayout layout = (LinearLayout) findViewById(R.id.my_layout);
//                        for (int i = 0; i < ll_bankGrid.getChildCount(); i++) {
//                            View child = ll_bankGrid.getChildAt(i);
//                            child.setVisibility(View.VISIBLE);
//                        }
////                        ll_lotteryGrid.setVisibility(GONE);
////                        lotteryGrid.setVisibility(View.GONE);
//                        reservationGrid.setVisibility(View.GONE);
//                        ll_reservationGrid.setVisibility(GONE);
//                        ticketGrid.setVisibility(View.GONE);
//                        ll_ticketGrid.setVisibility(GONE);
//                        new showSubMenus().execute(bankGrid);
//                        break;
//                    case 6:
//                        mainGrid.setVisibility(View.GONE);
//                        bankGrid.setVisibility(View.GONE);
//                        ll_bankGrid.setVisibility(GONE);
////                        lotteryGrid.setVisibility(View.GONE);
////                        ll_lotteryGrid.setVisibility(GONE);
//                        reservationGrid.setVisibility(View.GONE);
//                        ll_reservationGrid.setVisibility(GONE);
//                        ticketGrid.setVisibility(View.VISIBLE);
//                        ll_ticketGrid.setVisibility(View.VISIBLE);
//                        for (int i = 0; i < ll_ticketGrid.getChildCount(); i++) {
//                            View child = ll_ticketGrid.getChildAt(i);
//                            child.setVisibility(View.VISIBLE);
//                        }
//                        new showSubMenus().execute(ticketGrid);
//                        break;
//                    case 7:
//                        CloseMoreMenus(v.getParent());
//                        if (SideNavigationMasterActivity.tabLayout != null) {
//                            SideNavigationMasterActivity.tabLayout.setEnabled(true);
//                            SideNavigationMasterActivity.tabLayout.setVisibility(View.VISIBLE);
//                        }
//                        if (SideNavigationMasterActivity.container != null) {
//                            SideNavigationMasterActivity.container.setEnabled(true);
//                            SideNavigationMasterActivity.container.setVisibility(View.VISIBLE);
//                        }
//                        break;
//                    case 8:
//                        // Intent intent = new Intent(SideNavigationMasterActivity.this, BankQueue.class);
//                        UTILITY.bank_queueType = "queue";
//                        intent = new Intent(SideNavigationMasterActivity.this, BankList.class);
//                        startActivity(intent);
//                        break;
//                    case 9:
//                        // Intent intent = new Intent(SideNavigationMasterActivity.this, BankQueue.class);
//                        //UTILITY.bank_queueType = "queue";
//                        intent = new Intent(SideNavigationMasterActivity.this, ATMActivity.class);
//                        startActivity(intent);
//                        break;
//                    case 10:
//                        UTILITY.bank_queueType = "bank";
//                        startActivity(new Intent(SideNavigationMasterActivity.this, BankList.class));
//                        moreMenus.setVisibility(View.GONE);
//                        break;
//                    case 11:
//                        startActivity(new Intent(SideNavigationMasterActivity.this, RestaurantReservationActivity.class));
//                        moreMenus.setVisibility(View.GONE);
//                        break;
//                    case 12:
//                        startActivity(new Intent(SideNavigationMasterActivity.this, LotteryActivity.class));
//                        moreMenus.setVisibility(View.GONE);
//                        break;
//                    case 13:
//                        startActivity(new Intent(SideNavigationMasterActivity.this, LotteryActivity.class));
//                        moreMenus.setVisibility(View.GONE);
//                        break;
//                    case 16:
//                        startActivity(new Intent(SideNavigationMasterActivity.this, MuseumListActivity.class));
//                        moreMenus.setVisibility(View.GONE);
//                        break;
//                    case 17:
//                        startActivity(new Intent(SideNavigationMasterActivity.this, CinemaListActivity.class));
//                        moreMenus.setVisibility(View.GONE);
//                        break;
//                    case 18:
//                        startActivity(new Intent(SideNavigationMasterActivity.this, TheaterListActivity.class));
//                        moreMenus.setVisibility(View.GONE);
//                        break;
//                    case 19:
//                        startActivity(new Intent(SideNavigationMasterActivity.this, ConcertListActivity.class));
//                        moreMenus.setVisibility(View.GONE);
//                        break;
//                    case 20:
//                        startActivity(new Intent(SideNavigationMasterActivity.this, EventsListActivity.class));
//                        moreMenus.setVisibility(View.GONE);
//                        break;
//                    case 14:
//                        intent = new Intent(activity, HotelReservation.class);
//                        startActivity(intent);
//                        break;
//                    case 15:
//                        intent = new Intent(activity, ParkingListActivity.class);
//                        startActivity(intent);
//                        break;
//
//                    case 21:
//
//                        CloseMoreMenusLL(v.getParent());
//
//                        break;
//                    default:
//                        CloseMoreMenus(v.getParent());
//                        break;
//                }
//            }
//        });

    }

//    public class showSubMenus extends AsyncTask {
//
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            final GridLayout gridLLayout = (GridLayout) objects[0];
//            //Declare the timer
//            final Timer t = new Timer();
//            i = 0;
////Set the schedule function and rate
//            t.scheduleAtFixedRate(new TimerTask() {
//
//                                      @Override
//                                      public void run() {
//                                          //Called each time when 1000 milliseconds (1 second) (the period parameter)
//                                          if (i < gridLLayout.getChildCount()) {
//                                              final View child = gridLLayout.getChildAt(i);
//                                              runOnUiThread(new Runnable() {
//
//                                                  @Override
//                                                  public void run() {
//                                                      if (child.getId() == R.id.merchantLottery) {
//                                                          if (Boolean.parseBoolean(SharedData.isMerchant(activity))) {
//                                                              child.setVisibility(View.VISIBLE);
//                                                          }
//                                                      } else {
//
//                                                          child.setVisibility(View.VISIBLE);
//                                                      }
//                                                      // Stuff that updates the UI
//
//                                                  }
//                                              });
//
//                                              i++;
//                                          } else {
//                                              t.cancel();
//                                              t.purge();
//                                          }
//                                      }
//
//                                  },
////Set how long before to start calling the TimerTask (in milliseconds)
//                    0,
////Set the amount of time between each execution (in milliseconds)
//                    200);
//
//            return null;
//        }
//
//    }

    public void handleToolbarClick(View v) {
        int tag = Integer.valueOf(v.getTag().toString());
        switch (tag) {

            case 0:
                startActivity(new Intent(SideNavigationMasterActivity.this, ConversationListActivity.class));
                break;
            case 1:
                startActivity(new Intent(SideNavigationMasterActivity.this, AboutUsActivity.class));
                break;
            case 2:
                //serach
                tabLayout.setVisibility(v.GONE);
                container.setVisibility(View.GONE);
                ll_advSearch.setVisibility(v.GONE);
                ll_main_search.setVisibility(v.VISIBLE);
                ll_Search.setVisibility(v.VISIBLE);
                //startActivity(new Intent(SideNavigationMasterActivity.this, SearchActivity.class));
                break;
            case 3:
                //  startActivity(new Intent(SideNavigationMasterActivity.this, BarcodeScannerActivityZBar.class));

                startActivity(new Intent(SideNavigationMasterActivity.this, BarcodeScannerActivityXZing.class));


            default:

                break;
        }
    }

    @Override
    public void onBackPressed() {


        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (moreMenus != null && moreMenus.getVisibility() == View.VISIBLE) {
            moreMenus.setVisibility(View.GONE);
            mainGrid.setVisibility(View.GONE);
            bankGrid.setVisibility(View.GONE);
            ll_bankGrid.setVisibility(GONE);

            iv_ll_bankGrid.setVisibility(GONE);
//            iv_ll_lotteryGrid.setVisibility(GONE);
            iv_ll_reservationGrid.setVisibility(GONE);
            iv_ll_ticketGrid.setVisibility(GONE);
        }/* else
            super.onBackPressed();*/

        if (count == 0) {
            count++;
            startActivity(new Intent(activity, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .putExtra("count", count)
            );

            if (SideNavigationMasterActivity.tabLayout != null) {
                SideNavigationMasterActivity.tabLayout.setVisibility(View.VISIBLE);
            }
            if (SideNavigationMasterActivity.container != null) {
                SideNavigationMasterActivity.container.setVisibility(View.VISIBLE);
            }


            // Toast.makeText(activity, getResources().getString(R.string.Press_Back_again_to_Exit), Toast.LENGTH_SHORT).show();

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(2000);

                        count = 0;

                    } catch (Exception e) {
                         Toast.makeText(activity, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        //CustomToast.toast(activity, "" + e.getMessage(), 0);
                    }
                }
            };
            thread.start();
        } else if (count == 3) {//testing


            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            a.setFlags(FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(a);
            super.onBackPressed();
            System.exit(0);
           /* android.os.Process.killProcess(android.os.Process.myPid());
            super.onDestroy();*/
        } else {
            count++;
        }


    }

    public void ShowDiglog(View v) {

        final CharSequence[] items = {getResources().getString(R.string.Camera),
                getResources().getString(R.string.Gallery),getResources().getString(R.string.cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getResources().getString(R.string.Select_profile_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals(getResources().getString(R.string.Camera))) {
                    ClickImageFromCamera();
                } else if (items[item].equals(getResources().getString(R.string.Gallery))) {
                    GetImageFromGallery();
                } else if (items[item].equals(getResources().getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void ClickImageFromCamera() {

        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
            uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", f);
            //uri = Uri.fromFile(f);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, CAMERA_CODE);
        } catch (Exception ex) {
             Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_SHORT).show();
           // CustomToast.toast(this, ex.getMessage(), 0);
        }
    }

    public void GetImageFromGallery() {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {
            uri = data.getData();

            System.out.println("Gallery Image URI : " + uri);
            CropingIMG();
        } else if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            System.out.println("Camera Image URI : " + uri);
            CropingIMG();
        } else if (requestCode == CROPING_CODE) {
            try {
                if (outPutFile.exists()) {
                    Bitmap photo = decodeFile(outPutFile);
                    profileImage.setImageBitmap(photo);
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        //UpdateProfileImage(byteArray);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    //Toast.makeText(getApplicationContext(), "Error while saving image", Toast.LENGTH_SHORT).show();
                    Toast toast = Toast.makeText(activity, getResources().getString(R.string.Error_saving_image), Toast.LENGTH_LONG);
                    View toastView = toast.getView(); // This'll return the default View of the Toast.
                    TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                    //  toastMessage.setTypeface(typeface);
                    toast.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap decodeFile(File f) {

        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            //CustomToast.toast(this, e.getMessage(), 0);
        }
        return null;
    }

    private void CropingIMG() {

        final ArrayList<CropingOption> cropOptions = new ArrayList<CropingOption>();
//        grantUriPermission("com.android.camera", uri,
//                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            //Toast.makeText(this, "Can't find image cropping app", Toast.LENGTH_SHORT).show();
            Toast toast = Toast.makeText(activity, getResources().getString(R.string.Can_not_find_image_capture_program), Toast.LENGTH_LONG);
            View toastView = toast.getView(); // This'll return the default View of the Toast.
            TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
            // toastMessage.setTypeface(typeface);
            toast.show();
            return;
        } else {


            intent.setData(uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            //TODO: don't use return-data tag because it's not return large image data and crash not given any message
            //intent.putExtra("return-data", true);
            //Create output file here
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));
            try {
                if (size == 1) {
                    Intent i = new Intent(intent);
                    ResolveInfo res = (ResolveInfo) list.get(0);
                    i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    startActivityForResult(i, CROPING_CODE);
                } else {
                    for (ResolveInfo res : list) {
                        final CropingOption co = new CropingOption();
                        co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                        co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                        co.appIntent = new Intent(intent);
                        co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                        cropOptions.add(co);
                    }
                    CropingOptionAdapter adapter = new CropingOptionAdapter(getApplicationContext(), cropOptions);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Business");
                    builder.setCancelable(false);
                    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            startActivityForResult(cropOptions.get(item).appIntent, CROPING_CODE);
                        }
                    });
                    builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {

                            if (uri != null) {
                                getContentResolver().delete(uri, null, null);
                                uri = null;
                            }
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } catch (Exception ex) {
                 Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_SHORT).show();
                //CustomToast.toast(this, ex.getMessage(), 0);
            }
        }
    }

//    public void UpdateProfileImage(byte[] imageData) {
//        ApiService apiService = ApiClient.getClient(activity)
//                .create(ApiService.class);
//
//        UpdateImage updateImage = new UpdateImage();
//        updateImage.customerId = SharedData.User_Id(activity);
//        updateImage.profileImageAsByteArray = imageData;
//// Fetching all notifications
//        apiService.UpdateProfileImage(updateImage)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<Boolean>() {
//                    @Override
//                    public void onSuccess(Boolean response) {
//                        int a = 10;
//                        int b = a;
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        new SnackMsg(activity, e.getMessage());
//                        // HttpException ex = (HttpException) e;
//                        try {
//                            HttpException ex = (HttpException) e;
//                            if (!TextUtils.isEmpty(ex.response().errorBody().string()) && ex.response().errorBody().string().toLowerCase().contains("access denied")) {
//                                ApiClient.RefreshToken(activity);
//                            } else {
//                                // new SnackMsg(MyAddressesActivity.this, "else called");
//                            }
//                        } catch (Exception ex1) {
//                            ex1.printStackTrace();
//                        }
//                    }
//                });
//    }

    //ravi
    public void search(View view) {
        ll_advSearch.setVisibility(View.VISIBLE);

        String[] state = {"State", "UP", "Pune", "Karnataka", "Chennai"};
        String[] city = {"City", "UP", "Pune", "Karnataka", "Chennai"};
        String[] category = {"Category", "UP", "Pune", "Karnataka", "Chennai"};

        Spinner spinner_state = (Spinner) findViewById(R.id.spinner_state);
        Spinner spinner_city = (Spinner) findViewById(R.id.spinner_city);
        Spinner spinner_category = (Spinner) findViewById(R.id.spinner_category);


        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, state);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_state.setAdapter(aa);

        ArrayAdapter aa2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, city);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_city.setAdapter(aa2);

        ArrayAdapter aa3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(aa3);


    }

    public void adv_btnsClick(final View v) {
        final int tag = Integer.valueOf(v.getTag().toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (tag) {
                    case 0:
                        btn_store.setBackgroundResource(R.drawable.darkblue);
                        btn_store.setTextColor(getResources().getColor(R.color.white));
                        btn_store.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_location_orange, 0);

                        btn_merchant.setBackgroundResource(R.drawable.get_otp_border_white);
                        btn_merchant.setTextColor(getResources().getColor(R.color.black));
                        btn_merchant.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_my_location, 0);

                        btn_restraunt.setBackgroundResource(R.drawable.get_otp_border_white);
                        btn_restraunt.setTextColor(getResources().getColor(R.color.black));
                        btn_restraunt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_my_location, 0);
                        btn_pressed_module = "STORE";

                        break;

                    case 1:
                        btn_store.setBackgroundResource(R.drawable.get_otp_border_white);
                        btn_store.setTextColor(getResources().getColor(R.color.black));
                        btn_store.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_my_location, 0);

                        btn_merchant.setBackgroundResource(R.drawable.darkblue);
                        btn_merchant.setTextColor(getResources().getColor(R.color.white));
                        btn_merchant.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_location_orange, 0);

                        btn_restraunt.setBackgroundResource(R.drawable.get_otp_border_white);
                        btn_restraunt.setTextColor(getResources().getColor(R.color.black));
                        btn_restraunt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_my_location, 0);
                        btn_pressed_module = "MERCHANT";

                        break;

                    case 2:

                        btn_store.setTextColor(getResources().getColor(R.color.black));
                        btn_store.setBackgroundResource(R.drawable.get_otp_border_white);
                        btn_store.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_my_location, 0);

                        btn_merchant.setBackgroundResource(R.drawable.get_otp_border_white);
                        btn_merchant.setTextColor(getResources().getColor(R.color.black));
                        btn_merchant.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_my_location, 0);

                        btn_restraunt.setBackgroundResource(R.drawable.darkblue);
                        btn_restraunt.setTextColor(getResources().getColor(R.color.white));
                        btn_restraunt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_location_orange, 0);
                        btn_pressed_module = "RESTRAUNT";

                        break;


                    default:
                        CloseMoreMenus(v.getParent());
                        break;
                }
            }
        });

    }

    public void AdvSearchSubmit(View view) {

        //Toast.makeText(activity, "Submitted Successfully!", Toast.LENGTH_SHORT).show();

        tabLayout.setVisibility(View.VISIBLE);
        container.setVisibility(View.VISIBLE);
        ll_main_search.setVisibility(View.GONE);
        ll_advSearch.setVisibility(View.GONE);
        ll_Search.setVisibility(View.GONE);
        // initiate a Switch
        Switch switch_freeshipping = (Switch) findViewById(R.id.switch_freeshipping);
        Switch switch_rates = (Switch) findViewById(R.id.switch_rates);
        Switch switch_sales = (Switch) findViewById(R.id.switch_sales);
         EditText et_searchtext = (EditText) findViewById(R.id.et_searchtext);
        // check current state of a Switch (true or false).
        Boolean switchState = switch_freeshipping.isChecked();
        Boolean switchState1 = switch_rates.isChecked();
        Boolean switchState2 = switch_sales.isChecked();

        String customerID = SharedData.User_Id(activity);

        String SearchValue = et_searchtext.getText().toString().trim();

        if (!SearchValue.equalsIgnoreCase("")) {

            //getSreachDataAccToQuery(customerID, clubID, SearchValue);//searchmethod

        } else {
            Toast.makeText(activity, "Not Entered Anything", Toast.LENGTH_SHORT).show();
        }

    }

    public void getSreachDataAccToQuery(String customerId, String clubId, String searchValue) {

//        ApiService apiService = ApiClient.getClient(activity).create(ApiService.class);
//
//        final ProgressDialog dialog = AppProgress.showProgress(activity);
//// Fetching all notes
//        apiService.GetMerchantsBySearchV2(customerId, clubId, searchValue, "0", "25")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<Merchant>>() {
//                    @Override
//                    public void onSuccess(List<Merchant> merchant) {
//                        LstmerchantList.clear();
//
//                        try {
//                            merchantList = merchant;
//
//                            if (merchant.size() == 0) {//here i need to change
//
//                            } else {
//                                for (Merchant merchant1 : merchantList) {
//                                    LstmerchantList.add(merchant1);
//                                }
//
//                                Intent intent = new Intent(activity, SearchActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.putExtra("module", btn_pressed_module);
//                                activity.startActivity(intent);
//                            }
//
//                            if (merchantList.isEmpty()) {
//                                new SnackMsg(activity, getResources().getString(R.string.no_data_found));
//                            } else {
//                                //merchantHomePageAdapter = new HomePageFragment.MerchantHomePageAdapter(context, merchantList);
//                                //homeList.setAdapter(merchantHomePageAdapter);
//                                //  homeList.setSelection(skipnum);
//                            }
//                           /* if (swipeRefreshLayout.isRefreshing()) {
//                                swipeRefreshLayout.setRefreshing(false);
//                            }*/
//                        } catch (Exception ex) {
//                            ExceptionLog.catchException(ex);
//                        } finally {
//                            //AppProgress.hideProgress();
//                        }
//                        AppProgress.hideProgress(dialog);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        // Network error
//                        new SnackMsg(activity, e.getMessage());
//                        AppProgress.hideProgress(dialog);
//
//                        try {
//                            HttpException ex = (HttpException) e;
//
//                            if (!TextUtils.isEmpty(ex.response().errorBody().string()) && ex.response().errorBody().string().toLowerCase().contains("access denied")) {
//                                ApiClient.RefreshToken(activity);
//                            } else {
//                                // new SnackMsg(MyAddressesActivity.this, "else called");
//                            }
//                        } catch (Exception ex1) {
//                            ex1.printStackTrace();
//                        }
//                    }
//                });

    }


}
