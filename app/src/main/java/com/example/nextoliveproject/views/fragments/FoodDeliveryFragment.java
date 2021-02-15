package com.example.nextoliveproject.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nextoliveproject.R;

import java.util.ArrayList;
public class FoodDeliveryFragment extends Fragment {
    View view;
    RecyclerView topPicks,rvOffers,rvCoupons,rvPopularCuisines,rvHighlight,rvSpotlight,rvPopularBrands,rvBestInsafty;
    ArrayList<String> arrayListTopPick=new ArrayList<>();
    ArrayList<String> arrayListOffer=new ArrayList<>();
    ArrayList<String> arrayListCoupons=new ArrayList<>();
    ArrayList<String> arrayPopularCuisines=new ArrayList<>();
    ArrayList<String> arrayListHighlight=new ArrayList<>();
    ArrayList<String> arrayListSpotLight=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food_delivery, container, false);
        findViewByIds();
        addTopPicRv();
        addOfferRv();
        addCouponRv();
        addRvPopularCuisines();
        addHighlightRv();
        addSpotLightRv();
        addPopularBrandsRv();
        addBestInSafetyRv();
        return view;
    }

    public void findViewByIds(){
        topPicks = view.findViewById(R.id.rvTopPicks);
        rvOffers=view.findViewById(R.id.rvOffers);
        rvCoupons=view.findViewById(R.id.rvCoupons);
        rvPopularCuisines=view.findViewById(R.id.rvPopularCuisines);
        rvHighlight=view.findViewById(R.id.rvHighlight);
        rvSpotlight=view.findViewById(R.id.rvSpotlight);
        rvPopularBrands=view.findViewById(R.id.rvPopularBrands);
        rvBestInsafty=view.findViewById(R.id.rvBestInsafty);
    }

    private void addTopPicRv() {
        arrayListTopPick.add("Punjabi Dhaba");
        arrayListTopPick.add("Burger Point");
        arrayListTopPick.add("Mahalaxmi Sweets");
        arrayListTopPick.add("Punjabi Dhaba");
        arrayListTopPick.add("Burger Point");
        arrayListTopPick.add("Mahalaxmi Sweets");
        arrayListTopPick.add("Punjabi Dhaba");
        arrayListTopPick.add("Burger Point");
        arrayListTopPick.add("Mahalaxmi Sweets");

        RecyclerView.Adapter adapter=new CustomAdapter(arrayListTopPick,"TopPicks");
        topPicks.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        topPicks.setAdapter(adapter);
    }

    private void addOfferRv() {
        arrayListOffer.add("Punjabi Dhaba");
        arrayListOffer.add("Burger Point");
        arrayListOffer.add("Mahalaxmi Sweets");
        arrayListOffer.add("Punjabi Dhaba");
        arrayListOffer.add("Burger Point");
        arrayListOffer.add("Mahalaxmi Sweets");
        arrayListOffer.add("Punjabi Dhaba");
        arrayListOffer.add("Burger Point");
        arrayListOffer.add("Mahalaxmi Sweets");
        arrayListOffer.add("Punjabi Dhaba");
        arrayListOffer.add("Burger Point");
        arrayListOffer.add("Mahalaxmi Sweets");

        RecyclerView.Adapter adapter=new OfferAdapter(arrayListOffer);
        rvOffers.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        rvOffers.setAdapter(adapter);

    }

    private void addCouponRv() {
        arrayListCoupons.add(" UPTO Rs.200 OFF ");
        arrayListCoupons.add(" UPTO 50% OFF ");
        arrayListCoupons.add(" UPTO Rs.300 OFF ");
        arrayListCoupons.add(" UPTO Rs.200 OFF ");
        arrayListCoupons.add(" UPTO 50% OFF ");
        arrayListCoupons.add(" UPTO Rs.300 OFF ");

        RecyclerView.Adapter adapter=new CouponsAdapter(arrayListCoupons);
        rvCoupons.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        rvCoupons.setAdapter(adapter);

    }

    private void addRvPopularCuisines() {

        arrayPopularCuisines.add(" Biryani ");
        arrayPopularCuisines.add(" North ");
        arrayPopularCuisines.add(" Burgers ");
        arrayPopularCuisines.add(" Beverages ");
        arrayPopularCuisines.add(" Rolls ");
        arrayPopularCuisines.add(" Snacks ");

        RecyclerView.Adapter adapter=new popularCuisinesAdapter(arrayPopularCuisines);
        rvPopularCuisines.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        rvPopularCuisines.setAdapter(adapter);
    }

    private void addHighlightRv()  {
        arrayListHighlight.add(" UPTO Rs.200 OFF ");
        arrayListHighlight.add(" UPTO 50% OFF ");
        arrayListHighlight.add(" UPTO Rs.300 OFF ");
        arrayListHighlight.add(" UPTO Rs.200 OFF ");
        arrayListHighlight.add(" UPTO 50% OFF ");
        arrayListHighlight.add(" UPTO Rs.300 OFF ");

        final int duration = 10;
        final int pixelsToMove = 30;
        final Handler mHandler = new Handler(Looper.getMainLooper());
        final Runnable SCROLLING_RUNNABLE = new Runnable() {

            @Override
            public void run() {
                rvHighlight.smoothScrollBy(pixelsToMove, 0);
                mHandler.postDelayed(this, duration);
            }
        };


        RecyclerView.Adapter adapter=new HighlightAdapter(arrayListHighlight);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rvHighlight.setLayoutManager(layoutManager);
        rvHighlight.setAdapter(adapter);


        rvHighlight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItem = layoutManager.findLastCompletelyVisibleItemPosition();
                if(lastItem == layoutManager.getItemCount()-1){
                    mHandler.removeCallbacks(SCROLLING_RUNNABLE);
                    Handler postHandler = new Handler();
                    postHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rvHighlight.setAdapter(null);
                            rvHighlight.setAdapter(adapter);
                            mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);
                        }
                    }, 2000);
                }
            }
        });
        mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);

    }

    private void addSpotLightRv() {

        arrayListSpotLight.add("Mahalaxmy Sweets");
        arrayListSpotLight.add("Kwality walls Frozen Dessert ");
        arrayListSpotLight.add("The Bon Bon Bakers ");
        arrayListSpotLight.add("Mahalaxmy Sweets");
        arrayListSpotLight.add("Kwality walls Frozen Dessert ");
        arrayListSpotLight.add("The Bon Bon Bakers ");
        arrayListSpotLight.add("Mahalaxmy Sweets");
        arrayListSpotLight.add("Kwality walls Frozen Dessert ");
        arrayListSpotLight.add("The Bon Bon Bakers ");


        RecyclerView.Adapter adapter=new SpotlightAdapter(arrayListSpotLight);
        GridLayoutManager layoutManager= new GridLayoutManager(getActivity(),2,GridLayoutManager.HORIZONTAL,false);
        rvBestInsafty.setLayoutManager(layoutManager);
        rvBestInsafty.setAdapter(adapter);

    }

    private void addPopularBrandsRv() {

        arrayPopularCuisines.add(" Biryani ");
        arrayPopularCuisines.add(" North ");
        arrayPopularCuisines.add(" Burgers ");
        arrayPopularCuisines.add(" Beverages ");
        arrayPopularCuisines.add(" Rolls ");
        arrayPopularCuisines.add(" Snacks ");

        RecyclerView.Adapter adapter=new popularCuisinesAdapter(arrayPopularCuisines);
        rvPopularBrands.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        rvPopularBrands.setAdapter(adapter);
    }

    private void addBestInSafetyRv() {

        arrayListSpotLight.add("Mahalaxmy Sweets");
        arrayListSpotLight.add("Kwality walls Frozen Dessert ");
        arrayListSpotLight.add("The Bon Bon Bakers ");
        arrayListSpotLight.add("Mahalaxmy Sweets");
        arrayListSpotLight.add("Kwality walls Frozen Dessert ");
        arrayListSpotLight.add("The Bon Bon Bakers ");
        arrayListSpotLight.add("Mahalaxmy Sweets");
        arrayListSpotLight.add("Kwality walls Frozen Dessert ");
        arrayListSpotLight.add("The Bon Bon Bakers ");


        RecyclerView.Adapter adapter=new SpotlightAdapter(arrayListSpotLight);
        GridLayoutManager layoutManager= new GridLayoutManager(getActivity(),2,GridLayoutManager.HORIZONTAL,false);
        rvSpotlight.setLayoutManager(layoutManager);
        rvSpotlight.setAdapter(adapter);
    }



    // recycler Top pick
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<String> localDataSet ;
        String type;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvRestaurent;

            public ViewHolder(View view) {
                super(view);
                tvRestaurent = view.findViewById(R.id.tvRestaurent);
            }
        }
        public CustomAdapter(ArrayList<String> dataSet,String type) {
            this.localDataSet = dataSet;
            this.type=type;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.rv_item_top_pick, viewGroup, false);

            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.tvRestaurent.setText(localDataSet.get(position));
        }
        @Override
        public int getItemCount() {
            Log.v("dasdasd",String.valueOf(localDataSet.size()));
            return localDataSet.size();
        }
    }

    // recycler Offers
    public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder> {

        private ArrayList<String> localDataSet ;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivOffer;
            public ViewHolder(View view) {
                super(view);
                ivOffer = view.findViewById(R.id.ivOffer);
            }
        }
        public OfferAdapter(ArrayList<String> dataSet) {
            this.localDataSet = dataSet;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.rv_item_offer, viewGroup, false);

            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            //  viewHolder.tvRestaurent.setText(localDataSet.get(position));
        }
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    // recycler Coupons
    public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.ViewHolder> {

        private ArrayList<String> localDataSet ;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvPercentOff;
            public ViewHolder(View view) {
                super(view);
                tvPercentOff = view.findViewById(R.id.tvPercentOff);
            }
        }
        public CouponsAdapter(ArrayList<String> dataSet) {
            this.localDataSet = dataSet;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.rv_item_coupons, viewGroup, false);

            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.tvPercentOff.setText(localDataSet.get(position));
        }
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    // recycler popularCuisines
    public class popularCuisinesAdapter extends RecyclerView.Adapter<popularCuisinesAdapter.ViewHolder> {

        private ArrayList<String> localDataSet ;
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvType;
            public ViewHolder(View view) {
                super(view);
                tvType = view.findViewById(R.id.tvType);
            }
        }

        public popularCuisinesAdapter(ArrayList<String> dataSet) {
            this.localDataSet = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.rv_item_popular_cousines, viewGroup, false);

            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.tvType.setText(localDataSet.get(position));
        }
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    // recycler highlight
    public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {

        private ArrayList<String> localDataSet ;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvHighlight;
            public ViewHolder(View view) {
                super(view);
                tvHighlight = view.findViewById(R.id.tvHighlight);
            }
        }
        public HighlightAdapter(ArrayList<String> dataSet) {
            this.localDataSet = dataSet;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.rv_item_highlight, viewGroup, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            // viewHolder.tvHighlight.setText(localDataSet.get(position));
        }
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    // recycler spotlight
    public class SpotlightAdapter extends RecyclerView.Adapter<SpotlightAdapter.ViewHolder> {

        private ArrayList<String> localDataSet ;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvType;
            public ViewHolder(View view) {
                super(view);
                tvType = view.findViewById(R.id.tvType);
            }
        }
        public SpotlightAdapter(ArrayList<String> dataSet) {
            this.localDataSet = dataSet;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.rv_item_spotlight, viewGroup, false);
            return new ViewHolder(view);
        }
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.tvType.setText(localDataSet.get(position));
        }
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}