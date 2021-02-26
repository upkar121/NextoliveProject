package com.example.nextoliveproject.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nextoliveproject.Adapter.CardStackAdapterFood;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.utility.AppSharedData;
import com.example.nextoliveproject.views.Food_Detail_Activity;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.ArrayList;
import java.util.HashMap;

public class food_card_show_fragment extends Fragment implements CardStackListener {
    View root;
    CardStackView cardStackView;
    public static CardStackAdapterFood adapterRecommend;
    public static RelativeLayout capture;
    Context context;
    private CardStackLayoutManager managerRecommend;
//    ArrayList<HashMap<String, String>> Recommend = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_food_card_show_fragment, container, false);
         findViewByIds();
        listDataRecommend();
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public void findViewByIds(){
        cardStackView = (CardStackView) root.findViewById(R.id.card_stack_view);
        capture = (RelativeLayout) root.findViewById(R.id.capture);
    }

    public void listDataRecommend(){

        if (!AppSharedData.FoodItems.isEmpty()) {
            setupCardStackView();
        } else {
            Toast.makeText(context, "Records not found...", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public void onCardDragging(Direction direction, float v) {
        Log.d("CardStackView", direction.name()+" "+v);
        switch (direction) {

            case Top:
                break;

            case Bottom:
                break;

            case Left:
                break;

            case Right:
                break;

        }
    }


    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        int pos = managerRecommend.getTopPosition();
        if (AppSharedData.FoodItems.size() == pos) {
            setupCardStackView();
        }
    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {
        if(!AppSharedData.ArrayCartItems.isEmpty()){
            for(int i=0;i<AppSharedData.ArrayCartItems.size();i++){
                if(AppSharedData.ArrayCartItems.get(i).getMenuId() == AppSharedData.FoodItems.get(position).getMenuId()){
                    view.findViewById(R.id.add_number).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.tv_add_item).setVisibility(View.GONE);
                }else{
                    view.findViewById(R.id.add_number).setVisibility(View.GONE);
                    view.findViewById(R.id.tv_add_item).setVisibility(View.VISIBLE);
                }
            }

        }else{
            view.findViewById(R.id.add_number).setVisibility(View.GONE);
            view.findViewById(R.id.tv_add_item).setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }



    private void setupCardStackView() {

        managerRecommend = new CardStackLayoutManager(context, this);
        managerRecommend.setStackFrom(StackFrom.None);
        //  manager.setVisibleCount(2);
        managerRecommend.setTranslationInterval(8.0f);
        managerRecommend.setScaleInterval(0.95f);
        managerRecommend.setSwipeThreshold(0.30f);
        managerRecommend.setMaxDegree(20.0f);
        managerRecommend.setDirections(Direction.HORIZONTAL);
        managerRecommend.setCanScrollHorizontal(true);
        managerRecommend.setCanScrollVertical(false);

        adapterRecommend = new CardStackAdapterFood(context, AppSharedData.FoodItems);
        cardStackView.setLayoutManager(managerRecommend);
        cardStackView.setAdapter(adapterRecommend);



    }

}