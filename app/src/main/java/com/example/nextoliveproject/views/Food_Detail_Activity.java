package com.example.nextoliveproject.views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nextoliveproject.Helper.LogoProgressDialog;
import com.example.nextoliveproject.Helper.SharedData;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.database.Data.Cart;
import com.example.nextoliveproject.database.Data.Food;
import com.example.nextoliveproject.database.FoodItemsDatabase;
import com.example.nextoliveproject.models.CartItems;
import com.example.nextoliveproject.models.FoodItem;
import com.example.nextoliveproject.network.Server_URL;
import com.example.nextoliveproject.utility.AppSharedData;
import com.example.nextoliveproject.utility.ConstantVariable;
import com.example.nextoliveproject.utility.Utility;
import com.example.nextoliveproject.views.fragments.food_card_show_fragment;
import com.example.nextoliveproject.views.fragments.popular_fragment;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Food_Detail_Activity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout main_back;
    public static RelativeLayout move_to_cart;
    public static TextView number_item_added;
    Toolbar toolbar;
    ViewPager viewPager,viewPager1;
    TabLayout tabLayout,tabLayout1;
    public ImageView restaurant_img;
    public TextView resturant_name,location_distance;
    int count_number = 1;
    int amount = 40;
    int total;
    HashMap<String,String> restaurantDetail = new HashMap<>();

    private LogoProgressDialog pdialog;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            this.getWindow().setStatusBarColor(getResources().getColor(R.color.dark_blue_800));
//        }
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_food_detail);
        context = this;
        restaurantDetail.clear();
        findViewByIDS();

        restaurantDetail = AppSharedData.ChoosedRestaurant.get(0);

        foodApi(this);
        double lat = Double.parseDouble(SharedData.latitude(Food_Detail_Activity.this));
        double lng = Double.parseDouble(SharedData.longitude(Food_Detail_Activity.this));
        double lat1 = Double.parseDouble(restaurantDetail.get("lat"));
        double lng1 = Double.parseDouble(restaurantDetail.get("lng"));
        double time = Utility.distance(lat,lng,lat1,lng1);


        Picasso.with(Food_Detail_Activity.this).load(restaurantDetail.get("image")).error(R.drawable.food_one).into(restaurant_img);
        resturant_name.setText(restaurantDetail.get("name"));
        location_distance.setText(restaurantDetail.get("landmark")+" | "+String.format("%s", (int)Math.round(time))+"km");


        main_back.setOnClickListener(this);
        move_to_cart.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(AppSharedData.ITEMCOUNT > 0){
            move_to_cart.setVisibility(View.VISIBLE);
        }else{
            move_to_cart.setVisibility(View.GONE);
        }

    }

    private void findViewByIDS(){

         main_back = findViewById(R.id.main_back);
        restaurant_img = findViewById(R.id.restaurant_img);
        resturant_name = findViewById(R.id.resturant_name);
        location_distance = findViewById(R.id.location_distance);
         viewPager = (ViewPager) findViewById(R.id.viewpager);
         tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        viewPager1 = (ViewPager) findViewById(R.id.viewpager1);
        tabLayout1 = (TabLayout) findViewById(R.id.tab_layout1);
        move_to_cart =findViewById(R.id.move_to_cart);
        number_item_added =findViewById(R.id.number_item_added);

    }

    public void setAdapter(){
        viewPager.setAdapter(new TestPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        viewPager1.setAdapter(new TestPagerAdapter2(getSupportFragmentManager()));
        tabLayout1.setupWithViewPager(viewPager1);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_back:
                onBackPressed();
                finish();
                break;

            case R.id.move_to_cart:
                Intent i = new Intent(Food_Detail_Activity.this,CartActivity.class);
                startActivity(i);
                break;


        }
    }

        public void foodApi(Context context){
            try {
                if (Utility.isInternetAvailable(context)){
                    try {
                        pdialog = new LogoProgressDialog(context);
                        pdialog.setProgress("Please Wait...");
                        int restaurantID  = Integer.parseInt(restaurantDetail.get("restaurantid"));
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.getCache().clear();
                        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.GET, Server_URL.food_URL+"13", null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    if(response.getBoolean("success")){
                                        Food food = new Food(Food_Detail_Activity.this);
                                        food.openDatabase();
                                        food.deleteAllRecord(FoodItemsDatabase.Food_Table);
                                        JSONArray jsonArray = response.getJSONArray("data");
                                        AppSharedData.FoodItems.clear();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject json = jsonArray.getJSONObject(i);
                                            FoodItem foodItem = new FoodItem();
                                            foodItem.setProductid(json.getString("productid"));
                                            foodItem.setUserId(json.getInt("user_id"));
                                            foodItem.setName(json.getJSONObject("submenus").getString("name"));
                                            foodItem.setPrice(Integer.parseInt(json.getString("price")));
                                            foodItem.setAvailability(json.getString("availability"));
                                            foodItem.setImage(json.getString("productimage"));
                                            foodItem.setDescriptions(json.getString("description"));
                                            foodItem.setMenuId(json.getJSONObject("submenus").getInt("menuid"));
                                            foodItem.setTitle(json.getString("title"));
                                            AppSharedData.FoodItems.add(foodItem);
                                            food.insertRecordCart(foodItem,FoodItemsDatabase.Food_Table);
                                            if (pdialog.getDialog().isShowing()) {
                                                pdialog.getDialog().dismiss();
                                            }
                                            setAdapter();
                                        }
                                        food.close();
                                    }

                                } catch (Exception ex) {
                                    Log.d("Api Error",""+ex);
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
                    Toast.makeText(context, "Internet Required", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception ex) {
                Log.d("Api Error",""+ex);
            }
        }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    static class TestPagerAdapter extends FragmentPagerAdapter {

        public TestPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
                return new food_card_show_fragment();

        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    static class TestPagerAdapter2 extends FragmentPagerAdapter {

        public TestPagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new popular_fragment();

        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}

