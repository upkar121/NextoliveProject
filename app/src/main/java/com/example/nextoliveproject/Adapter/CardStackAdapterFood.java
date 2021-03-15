package com.example.nextoliveproject.Adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nextoliveproject.Helper.SharedData;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.database.Data.Cart;
import com.example.nextoliveproject.database.FoodItemsDatabase;
import com.example.nextoliveproject.models.CartItems;
import com.example.nextoliveproject.models.FoodItem;
import com.example.nextoliveproject.utility.AppSharedData;
import com.example.nextoliveproject.utility.Utility;
import com.example.nextoliveproject.views.Food_Detail_Activity;
import com.example.nextoliveproject.views.ViewsCommentsActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;


public class CardStackAdapterFood extends RecyclerView.Adapter<CardStackAdapterFood.ViewHolder>{

    ArrayList<FoodItem> data;
    Context context;
    int position = 0;
    private LayoutInflater inflater;
    private ArrayList<CartItems> arrayCartItems = AppSharedData.ArrayCartItems;
    Cart cartdatabase;

    public CardStackAdapterFood(Context context, ArrayList<FoodItem> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(inflater.inflate(R.layout.layout_userhome, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int pos) {
        position = pos;
        cartdatabase = new Cart(context);
        cartdatabase.openDatabase();
        FoodItem Item = data.get(position);

        String foodName = Item.getName();

        ViewHolder.foodName.setText(foodName);
        Picasso.with(context).load(Item.getImage()).error(R.drawable.food_one).into(ViewHolder.foodImage);
        ViewHolder.price.setText("₹"+Item.getPrice());
        ViewHolder.descriptions.setText(Item.getDescriptions());
        ViewHolder.hastag.setText("#"+Item.getAvailability());

        TextView qty = holder.itemView.findViewById(R.id.tvQty);
        TextView add =  holder.itemView.findViewById(R.id.tvAdd);
        TextView subtract =  holder.itemView.findViewById(R.id.tvSub);

        Food_Detail_Activity.number_item_added.setText(AppSharedData.ITEMCOUNT+" Items"+" | "+"₹"+AppSharedData.Total);

        LinearProgressIndicator progressIndicator = holder.itemView.findViewById(R.id.progressShow);
        progressIndicator.setIndicatorColor(context.getResources().getColor(R.color.skyBlue));
        progressIndicator.show();
           FoodItem item1 = data.get(holder.getAdapterPosition());

           if(cartdatabase.getItemsCount(FoodItemsDatabase.Cart_Table)>0){
               int quantity = cartdatabase.getQuantity(item1.getMenuId(),FoodItemsDatabase.Cart_Table);
               if(quantity>0){
                   qty.setText(""+quantity);
                   holder.itemView.findViewById(R.id.tv_add_item).setVisibility(View.GONE);
                   holder.itemView.findViewById(R.id.add_number).setVisibility(View.VISIBLE);
                   AppSharedData.ITEMCOUNT = cartdatabase.totalQuantity(FoodItemsDatabase.Cart_Table);
                   AppSharedData.Total = Float.parseFloat(SharedData.totalAmount(context));
                   Food_Detail_Activity.number_item_added.setText(AppSharedData.ITEMCOUNT+" Items"+" | "+"₹"+AppSharedData.Total);
                   Food_Detail_Activity.move_to_cart.setVisibility(View.VISIBLE);
               }else{
                   holder.itemView.findViewById(R.id.tv_add_item).setVisibility(View.VISIBLE);
                   holder.itemView.findViewById(R.id.add_number).setVisibility(View.GONE);
               }
           }else{
               holder.itemView.findViewById(R.id.tv_add_item).setVisibility(View.VISIBLE);
               holder.itemView.findViewById(R.id.add_number).setVisibility(View.GONE);
               Food_Detail_Activity.move_to_cart.setVisibility(View.GONE);
           }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressIndicator.show();

                int quantity = cartdatabase.getQuantity(item1.getMenuId(),FoodItemsDatabase.Cart_Table);
                float price = cartdatabase.getPrice(item1.getMenuId(),FoodItemsDatabase.Cart_Table);
                quantity++;
                AppSharedData.ITEMCOUNT = cartdatabase.totalQuantity(FoodItemsDatabase.Cart_Table);
                AppSharedData.ITEMCOUNT++;
                qty.setText(""+ quantity);
                AppSharedData.Total = Float.parseFloat(SharedData.totalAmount(context));
                AppSharedData.Total+= price;
                SharedData.totalAmount(context,""+AppSharedData.Total);
                Food_Detail_Activity.number_item_added.setText(AppSharedData.ITEMCOUNT+" Items"+" | "+"₹"+AppSharedData.Total);
                SharedData.totalAmount(context,""+AppSharedData.Total);
                CartItems cartItems = new CartItems();
                cartItems.setMenuId(Item.getMenuId());
                cartItems.setUserId(Item.getUserId());
                cartItems.setName(Item.getName());
                cartItems.setPrice(Item.getPrice());
                cartItems.setQuantity(quantity);
                long time = System.currentTimeMillis();
                String currentDate = Utility.ParseMillisTotDate(time);
                cartdatabase.updateRecord(""+item1.getMenuId(),cartItems,FoodItemsDatabase.Cart_Table,currentDate);

                progressIndicator.hide();

            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressIndicator.show();
                int quantity = cartdatabase.getQuantity(item1.getMenuId(),FoodItemsDatabase.Cart_Table);
                float price = cartdatabase.getPrice(item1.getMenuId(),FoodItemsDatabase.Cart_Table);
                quantity--;
                AppSharedData.ITEMCOUNT = cartdatabase.totalQuantity(FoodItemsDatabase.Cart_Table);
                AppSharedData.ITEMCOUNT--;
                if(quantity <= 0){
                    cartdatabase.deleteRecord(item1.getMenuId(),FoodItemsDatabase.Cart_Table);
                    holder.itemView.findViewById(R.id.add_number).setVisibility(View.GONE);
                    holder.itemView.findViewById(R.id.tv_add_item).setVisibility(View.VISIBLE);
                }else{
                    qty.setText(""+ quantity);
                    CartItems cartItems = new CartItems();
                    cartItems.setMenuId(Item.getMenuId());
                    cartItems.setUserId(Item.getUserId());
                    cartItems.setName(Item.getName());
                    cartItems.setPrice(Item.getPrice());
                    cartItems.setQuantity(quantity);
                    long time = System.currentTimeMillis();
                    String currentDate = Utility.ParseMillisTotDate(time);
                    cartdatabase.updateRecord(""+item1.getMenuId(),cartItems,FoodItemsDatabase.Cart_Table,currentDate);
                    if(quantity>1){
                        subtract.setTextColor(context.getResources().getColor(R.color.green));
                    }
                }

                if(AppSharedData.ITEMCOUNT <=0){
                    SharedData.totalAmount(context,""+0);
                    Food_Detail_Activity.move_to_cart.setVisibility(View.GONE);
                }else {
                    AppSharedData.Total = Float.parseFloat(SharedData.totalAmount(context));
                    AppSharedData.Total-= price;
                    SharedData.totalAmount(context,""+AppSharedData.Total);
                    Food_Detail_Activity.number_item_added.setText(AppSharedData.ITEMCOUNT+" Items"+" | "+"₹"+AppSharedData.Total);
                    SharedData.totalAmount(context,""+AppSharedData.Total);
                }

                progressIndicator.hide();
            }
        });

        holder.itemView.findViewById(R.id.tv_add_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressIndicator.show();
                CartItems cartItems = new CartItems();
                cartItems.setMenuId(Item.getMenuId());
                cartItems.setUserId(Item.getUserId());
                cartItems.setName(Item.getName());
                cartItems.setPrice(Item.getPrice());
                cartItems.setQuantity(1);

                cartdatabase.insertRecordCart(cartItems, FoodItemsDatabase.Cart_Table);

                holder.itemView.findViewById(R.id.add_number).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.tv_add_item).setVisibility(View.GONE);
                AppSharedData.ITEMCOUNT = cartdatabase.totalQuantity(FoodItemsDatabase.Cart_Table);

                if(AppSharedData.ITEMCOUNT >1){
                    AppSharedData.Total += Item.getPrice();
                    SharedData.totalAmount(context,""+AppSharedData.Total);
                    Food_Detail_Activity.number_item_added.setText(AppSharedData.ITEMCOUNT+" Items"+" | "+"₹"+AppSharedData.Total);
                }else{
                    AppSharedData.Total = 0.0f ;
                    SharedData.totalAmount(context,""+AppSharedData.Total);
                    AppSharedData.Total += Item.getPrice();
                    SharedData.totalAmount(context,""+AppSharedData.Total);
                    Food_Detail_Activity.number_item_added.setText(AppSharedData.ITEMCOUNT+" Items"+" | "+"₹"+AppSharedData.Total);
                }
                Food_Detail_Activity.move_to_cart.setVisibility(View.VISIBLE);
                progressIndicator.hide();
            }
        });

        holder.itemView.findViewById(R.id.expand_image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ViewsCommentsActivity.class);
                i.putExtra(ViewsCommentsActivity.EXTRA_MENUID,item1.getMenuId());
                context.startActivity(i);
            }
        });
        progressIndicator.hide();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public static CardView cardView;
        public static CircleImageView circle_image;
        public static TextView foodName;
        public static TextView price;
        public static ImageView foodImage;
        public static TextView hastag;
        public static TextView descriptions;
        public static ImageView expand_image;
        public static RelativeLayout review_relative_layout;


        ViewHolder(View view) {

            super(view);
            cardView = view.findViewById(R.id.cardView);
            circle_image = view.findViewById(R.id.circle_image);
            foodName = view.findViewById(R.id.foodName);
            price = view.findViewById(R.id.price);
            foodImage = view.findViewById(R.id.foodImage);
            hastag = view.findViewById(R.id.hastag);
            descriptions = view.findViewById(R.id.descriptions);
            expand_image = view.findViewById(R.id.expand_image1);
            review_relative_layout = view.findViewById(R.id.review_relative_layout);
        }
    }


}

