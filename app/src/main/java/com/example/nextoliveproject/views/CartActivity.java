package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nextoliveproject.Adapter.CardStackAdapterFood;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.database.Data.Cart;
import com.example.nextoliveproject.database.FoodItemsDatabase;
import com.example.nextoliveproject.models.CartItems;
import com.example.nextoliveproject.utility.AppSharedData;
import com.example.nextoliveproject.utility.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    View view;
    RecyclerView rv_food_add;
    FoodAdapter adapter;
    private TextView tvtotal,tvdelParFee,tvDelTip,tvTax,tvToPay,tvDetailTotal,tvdiscount;
    LinearLayout llApplyCoupon,llTotalDiscount;

    private ImageView main_back,rest_image;
    private TextView resturant_name,resturant_location;

    float total,delCharges,DelTip,tax,toPay,discount;

    //DBHelper db;


    HashMap<String,String> restaurantDetail = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        findViewByIds();
        restaurantDetail = AppSharedData.ChoosedRestaurant.get(0);
        AppSharedData.ArrayCartItems.clear();
        Cart cart = new Cart(this);
        cart.openDatabase();
        AppSharedData.ArrayCartItems = cart.getAllData(FoodItemsDatabase.Cart_Table);
        Picasso.with(this).load(restaurantDetail.get("image")).error(R.drawable.food2).into(rest_image);
        resturant_name.setText(restaurantDetail.get("name"));
        resturant_location.setText(restaurantDetail.get("landmark"));
        adapter=new FoodAdapter(CartActivity.this);
        rv_food_add.setLayoutManager(new LinearLayoutManager(this));
        rv_food_add.setHasFixedSize(true);
        rv_food_add.setAnimation(null);
        rv_food_add.setAdapter(adapter);
        main_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


      cart.close();
    }

    public void findViewByIds(){
        main_back = findViewById(R.id.main_back);
        rv_food_add = findViewById(R.id.rv_food_add);
        rest_image = findViewById(R.id.rest_image);
        resturant_name = findViewById(R.id.resturant_name);
        resturant_location = findViewById(R.id.resturant_location);
        tvDetailTotal = findViewById(R.id.tvDetailTotal);

    }


    @Override
    protected void onResume() {
        super.onResume();
        total = AppSharedData.Total;
        tvDetailTotal.setText(String.format("₹%s", total));
    }

    // rv rvCartFoods adapter
    public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
        Context context;
        public FoodAdapter(Context context) {
            this.context = context;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvPrice,tvQty,tvName;
            public ViewHolder(View view) {
                super(view);
                tvPrice = view.findViewById(R.id.tvPrice);
                tvQty = view.findViewById(R.id.tvQty);
                tvName = view.findViewById(R.id.tvName);

            }
        }
//
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.rv_item_cart_food, viewGroup, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            CartItems cart =  AppSharedData.ArrayCartItems.get(position);
            holder.tvName.setText(cart.getName());
            holder.tvQty.setText(""+cart.getQuantity());
            float price = (float)cart.getQuantity() * (float)cart.getPrice();
            holder.tvPrice.setText("₹"+price);

            holder.itemView.findViewById(R.id.tvSub).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItems Item = AppSharedData.ArrayCartItems.get(holder.getAdapterPosition());
                    int quantity = Item.getQuantity();
                    float itemprice = (float) Item.getPrice();
                    if(quantity<=0){
                        AppSharedData.ArrayCartItems.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        Utility.dialogbox(CartActivity.this,"Remove Item","Item Removed successfully\n Now we move back to select items.");
                    }else{

                        quantity--;
                        holder.tvQty.setText(""+quantity);
                        float amt = (float)quantity * itemprice;
                        holder.tvPrice.setText("₹"+amt);
                        AppSharedData.Total -= itemprice;

                        CartItems cartItems = new CartItems();
                        cartItems.setMenuId(Item.getMenuId());
                        cartItems.setUserId(Item.getUserId());
                        cartItems.setName(Item.getName());
                        cartItems.setPrice(Item.getPrice());
                        cartItems.setQuantity(quantity);
                        long time = System.currentTimeMillis();
                        String currentDate = Utility.ParseMillisTotDate(time);
                        Cart cart1 = new Cart(context);
                        cart1.openDatabase();
                        cart1.updateRecord(""+Item.getMenuId(),cartItems,FoodItemsDatabase.Cart_Table,currentDate);
                        cart1.close();
                    }


                }
            });
            holder.itemView.findViewById(R.id.tvAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartItems Item = AppSharedData.ArrayCartItems.get(holder.getAdapterPosition());
                    int quantity = Item.getQuantity();
                    float itemprice = (float)Item.getPrice();
                    quantity++;
                    holder.tvQty.setText(""+quantity);
                    float amt = (float)quantity * itemprice;
                    holder.tvPrice.setText("₹"+amt);
                    AppSharedData.Total += itemprice;

                    CartItems cartItems = new CartItems();
                    cartItems.setMenuId(Item.getMenuId());
                    cartItems.setUserId(Item.getUserId());
                    cartItems.setName(Item.getName());
                    cartItems.setPrice(Item.getPrice());
                    cartItems.setQuantity(quantity);
                    long time = System.currentTimeMillis();
                    String currentDate = Utility.ParseMillisTotDate(time);
                    Cart cart1 = new Cart(context);
                    cart1.openDatabase();
                    cart1.updateRecord(""+Item.getMenuId(),cartItems,FoodItemsDatabase.Cart_Table,currentDate);
                    cart1.close();
                }
            });

        }


        @Override
        public int getItemCount() {
            return AppSharedData.ArrayCartItems.size();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}