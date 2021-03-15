package com.example.nextoliveproject.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nextoliveproject.Adapter.CardStackAdapterFood;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.database.Data.Food;
import com.example.nextoliveproject.database.FoodItemsDatabase;
import com.example.nextoliveproject.models.FoodItem;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewsCommentsActivity extends AppCompatActivity implements View.OnClickListener {
    public static String EXTRA_MENUID ="MenuId";
    int menuId;
    private ImageView main_back,food_image,send;
    private TextView foodName,title;
    private CircleImageView userimage;
    private EditText review;
    private RecyclerView comment_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(getResources().getColor(R.color.dark_blue_800));
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        menuId = getIntent().getIntExtra(EXTRA_MENUID,0);
        Food food = new Food(ViewsCommentsActivity.this);
        food.openDatabase();
        FoodItem foodItem = food.getData(menuId, FoodItemsDatabase.Food_Table);
        setContentView(R.layout.activity_views_comments);
        findViewByIds();
        Picasso.with(this).load(foodItem.getImage()).error(R.drawable.food_one).into(food_image);
        foodName.setText(foodItem.getName());
        title.setText(foodItem.getTitle());

        main_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_back:
                onBackPressed();
                break;
            case R.id.send:
                break;

        }
    }

    public void findViewByIds(){
        main_back = (ImageView) findViewById(R.id.main_back);
        food_image = (ImageView) findViewById(R.id.food_image);
        send = (ImageView) findViewById(R.id.send);
        foodName = findViewById(R.id.foodName);
        title = findViewById(R.id.foodTitle);
        userimage = findViewById(R.id.userimage);
        review = findViewById(R.id.review);
        comment_list = findViewById(R.id.comment_list);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}