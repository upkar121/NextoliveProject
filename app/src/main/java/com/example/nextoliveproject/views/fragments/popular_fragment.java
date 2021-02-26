package com.example.nextoliveproject.views.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nextoliveproject.Adapter.CardStackAdapterFood;
import com.example.nextoliveproject.Adapter.CardStackAdapterPopularFood;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.utility.AppSharedData;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.ArrayList;
import java.util.HashMap;

public class popular_fragment extends Fragment implements CardStackListener {

    View root;
    CardStackView cardStackView1;
    public static CardStackAdapterPopularFood adapterPopular;
    public static RelativeLayout capture1;
    Context context;
    private CardStackLayoutManager managerPopular;
//    ArrayList<HashMap<String, String>> Popular = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_popular_fragment, container, false);
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
        cardStackView1 = (CardStackView) root.findViewById(R.id.card_stack_view1);
        capture1 = (RelativeLayout) root.findViewById(R.id.capture1);
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

        int pos = managerPopular.getTopPosition();
        if (AppSharedData.FoodItems.size() == pos) {
            setupCardStackView();
        }
    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }


public void setupCardStackView(){
    managerPopular = new CardStackLayoutManager(context, this);
    managerPopular.setStackFrom(StackFrom.None);
    //  manager.setVisibleCount(2);
    managerPopular.setTranslationInterval(8.0f);
    managerPopular.setScaleInterval(0.95f);
    managerPopular.setSwipeThreshold(0.30f);
    managerPopular.setMaxDegree(20.0f);
    managerPopular.setDirections(Direction.HORIZONTAL);
    managerPopular.setCanScrollHorizontal(true);
    managerPopular.setCanScrollVertical(false);

//    adapterPopular = new CardStackAdapterPopularFood(context,AppSharedData.FoodItems);
//    cardStackView1.setLayoutManager(managerPopular);
//    cardStackView1.setAdapter(adapterPopular);

}
}