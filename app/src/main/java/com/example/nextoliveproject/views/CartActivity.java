package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nextoliveproject.R;
import com.example.nextoliveproject.utility.AppSharedData;
import com.example.nextoliveproject.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    public static String EXTRAS_NO_OF_ITEMS = "NO_OF_ITEMS";
    public static String EXTRAS_TOTAL_AMOUNT = "Total_Amount";
    View view;
    RecyclerView rv_food_add;
    RecyclerView.Adapter adapter;
    TextView tvtotal,tvdelParFee,tvDelTip,tvTax,tvToPay,tvDetailTotal,tvdiscount;
    LinearLayout llApplyCoupon,llTotalDiscount;

    ImageView main_back;

    //List<CartItems> cartItems= new ArrayList<>();

    //List<Coupon> coupons= new ArrayList<>();
    TextView tvAddAddress;

    double total,delCharges,DelTip,tax,toPay,discount;
    float amount;
    int count;
    //DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        findViewByIds();
        amount = AppSharedData.Total;
        count = 1;
        adapter=new FoodAdapter(amount,count);
        rv_food_add.setAdapter(adapter);
        rv_food_add.setLayoutManager(new LinearLayoutManager(this));

        main_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void findViewByIds(){
        main_back = findViewById(R.id.main_back);
        rv_food_add = findViewById(R.id.rv_food_add);
    }

    // rv rvCartFoods adapter
    public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

        float amount;
        int count;
        //private List<CartItems> localDataSet ;
        public FoodAdapter(float amount,int count) {
              this.amount =amount;
              this.count =count;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvPrice,tvQty,tvName,tvSub,tvAdd;
            public ViewHolder(View view) {
                super(view);
                tvPrice = view.findViewById(R.id.tvPrice);
                tvQty = view.findViewById(R.id.tvQty);
                tvName = view.findViewById(R.id.tvName);
                tvSub = view.findViewById(R.id.tvSub);
                tvAdd = view.findViewById(R.id.tvAdd);

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

            holder.tvName.setText("Burger");
            holder.tvQty.setText(""+count);
            holder.tvPrice.setText("₹"+amount);
            holder.tvSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count == 1){
                        Utility.dialogbox(CartActivity.this,"Remove Item","Item Removed successfully\n Now we move back to select items.");
                        onBackPressed();
                    }else{
                        count--;
                        holder.tvQty.setText(""+count);
                        total = count * amount;
                        holder.tvPrice.setText("₹"+total);
                    }
                }
            });
            holder.tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     count++;
                    holder.tvQty.setText(""+count);
                    total = count * amount;
                    holder.tvPrice.setText("₹"+total);
                }
            });

        }


        @Override
        public int getItemCount() {
            return 1;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}