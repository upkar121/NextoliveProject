package com.example.nextoliveproject.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nextoliveproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CardStackAdapterPopularFood extends RecyclerView.Adapter<CardStackAdapterPopularFood.ViewHolder> {

    //public static LinearLayout ll_like, ll_dislike, ll_comment;
    ArrayList<HashMap<String, String>> data;
    Context context;
    TextView likeCount, dislikeCount, tv_comment;
    SharedPreferences sharedPreferences;
    int position = 0;
    private LayoutInflater inflater;

    int count =0;

    public CardStackAdapterPopularFood(Context context, ArrayList<HashMap<String, String>> data) {
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
    public CardStackAdapterPopularFood.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new CardStackAdapterPopularFood.ViewHolder(inflater.inflate(R.layout.layout_popular_food, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int pos) {
        position = pos;
        HashMap<String, String> Item = data.get(position);
        ViewHolder.foodName.setText(Item.get("name"));

        Picasso.with(context).load(Item.get("productimage")).error(R.drawable.food_one).into(ViewHolder.foodImage);

        ViewHolder.price.setText("₹"+Item.get("price"));

        ViewHolder.descriptions.setText(Item.get("description"));

        ViewHolder.hastag.setText("#"+Item.get("availability"));

        ViewHolder.expand_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count == 0){
                    ViewHolder.expand_image.setImageDrawable(context.getResources().getDrawable(R.drawable.up_arrow));
                    CardStackAdapterPopularFood.ViewHolder.review_relative_layout.setVisibility(View.VISIBLE);
                    count++;
                }else{
                    ViewHolder.expand_image.setImageDrawable(context.getResources().getDrawable(R.drawable.down_arrow));
                    ViewHolder.review_relative_layout.setVisibility(View.GONE);
                    count =0;
                }

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
            expand_image = view.findViewById(R.id.expand_image);
            add_number = view.findViewById(R.id.add_number);
            review_relative_layout = view.findViewById(R.id.review_relative_layout1);
        }
    }


}