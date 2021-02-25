package com.example.nextoliveproject.Adapter;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.views.Food_Detail_Activity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class CardStackAdapterFood extends RecyclerView.Adapter<CardStackAdapterFood.ViewHolder>{

    //public static LinearLayout ll_like, ll_dislike, ll_comment;
    ArrayList<HashMap<String, String>> data;
    Context context;
    TextView likeCount, dislikeCount, tv_comment;
    SharedPreferences sharedPreferences;
    int position = 0;
    private LayoutInflater inflater;

    int count =0;

    public CardStackAdapterFood(Context context, ArrayList<HashMap<String, String>> data) {
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
        HashMap<String, String> Item = data.get(position);

        ViewHolder.foodName.setText(Item.get("name"));

        Picasso.with(context).load(Item.get("productimage")).error(R.drawable.food_one).into(ViewHolder.foodImage);

        ViewHolder.price.setText("₹"+Item.get("price"));

        ViewHolder.descriptions.setText(Item.get("description"));

        ViewHolder.hastag.setText("#"+Item.get("availability"));

        ViewHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                tv_quantity.setText(""+ count_number[0]);
//                total = count_number[0] *amount;
//                number_item_added.setText(""+ count_number[0] +" Item | "+"₹"+total);
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

    public void addSpots(ArrayList<HashMap<String, String>> data) {

        this.data.addAll(data);
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
        public static TextView tvSub,tvQty,tvAdd;
        public static RelativeLayout add_number,review_relative_layout;

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
            add_number = view.findViewById(R.id.add_number);
            review_relative_layout = view.findViewById(R.id.review_relative_layout);
            tvAdd = view.findViewById(R.id.tvAdd);
            tvSub = view.findViewById(R.id.tvSub);
            tvQty = view.findViewById(R.id.tvQty);
        }
    }


}

