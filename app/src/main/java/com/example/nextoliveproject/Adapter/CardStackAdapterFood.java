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
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.models.CartItems;
import com.example.nextoliveproject.models.FoodItem;
import com.example.nextoliveproject.utility.AppSharedData;
import com.example.nextoliveproject.views.Food_Detail_Activity;
import com.example.nextoliveproject.views.ViewsCommentsActivity;
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
    CartItems cart = null;

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
        int price;

        FoodItem Item = data.get(position);
        int menuId = Item.getMenuId();

        String foodName = Item.getName();
         price = Item.getPrice();

        ViewHolder.foodName.setText(foodName);
        Picasso.with(context).load(Item.getImage()).error(R.drawable.food_one).into(ViewHolder.foodImage);
        ViewHolder.price.setText("₹"+price);
        ViewHolder.descriptions.setText(Item.getDescriptions());
        ViewHolder.hastag.setText("#"+Item.getAvailability());

        TextView qty = holder.itemView.findViewById(R.id.tvQty);
        TextView add =  holder.itemView.findViewById(R.id.tvAdd);
        TextView subtract =  holder.itemView.findViewById(R.id.tvSub);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cart.getQuantity();
                quantity++;
                qty.setText(""+ quantity);
                AppSharedData.Total= (float) quantity * Float.parseFloat(""+price);
                Food_Detail_Activity.number_item_added.setText(quantity+" Items"+" | "+"₹"+AppSharedData.Total);
                cart.setQuantity(quantity);
            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = cart.getQuantity();
                quantity--;
                if(quantity == 0){
                    Food_Detail_Activity.move_to_cart.setVisibility(View.GONE);
                    holder.itemView.findViewById(R.id.add_number).setVisibility(View.GONE);
                    holder.itemView.findViewById(R.id.tv_add_item).setVisibility(View.VISIBLE);
                }else{
                    qty.setText(""+ quantity);
                    AppSharedData.Total= (float)quantity * Float.parseFloat(""+price);
                    Food_Detail_Activity.move_to_cart.setVisibility(View.VISIBLE);
                    Food_Detail_Activity.number_item_added.setText(quantity+" Items"+" | "+AppSharedData.Total);
                }

                cart.setQuantity(quantity);
            }
        });

        holder.itemView.findViewById(R.id.tv_add_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!arrayCartItems.isEmpty()){
                    for(int i =0;i<arrayCartItems.size();i++){
                        if(arrayCartItems.get(i).getMenuId() == menuId){
                            cart = arrayCartItems.get(i);
                        }
                    }
                    if(cart == null){
                        cart = new CartItems();
                        cart.setMenuId(menuId);
                        cart.setPrice(Item.getPrice());
                        cart.setName(Item.getName());
                        cart.setQuantity(0);
                        AppSharedData.ArrayCartItems.add(cart);
                    }
                }else{
                    cart = new CartItems();
                    cart.setMenuId(menuId);
                    cart.setPrice(Item.getPrice());
                    cart.setName(Item.getName());
                    cart.setQuantity(Integer.parseInt("0"));
                    AppSharedData.ArrayCartItems.add(cart);

                }
                holder.itemView.findViewById(R.id.add_number).setVisibility(View.VISIBLE);
                holder.itemView.findViewById(R.id.tv_add_item).setVisibility(View.GONE);
                Food_Detail_Activity.move_to_cart.setVisibility(View.VISIBLE);

            }
        });

        ViewHolder.expand_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ViewsCommentsActivity.class));
            }
        });

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

