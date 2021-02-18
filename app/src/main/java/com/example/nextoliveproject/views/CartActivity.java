package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nextoliveproject.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    View view;
    RecyclerView rvCartFoods;
    RecyclerView.Adapter adapter;
    TextView tvtotal,tvdelParFee,tvDelTip,tvTax,tvToPay,tvDetailTotal,tvdiscount;
    LinearLayout llApplyCoupon,llTotalDiscount;

    //List<CartItems> cartItems= new ArrayList<>();

    //List<Coupon> coupons= new ArrayList<>();
    TextView tvAddAddress;

    double total,delCharges,DelTip,tax,toPay,discount;

    //DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }


}