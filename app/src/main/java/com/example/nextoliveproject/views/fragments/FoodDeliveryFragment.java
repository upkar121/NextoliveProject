package com.example.nextoliveproject.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nextoliveproject.Helper.LogoProgressDialog;
import com.example.nextoliveproject.Helper.SharedData;
import com.example.nextoliveproject.R;
import com.example.nextoliveproject.network.Server_URL;
import com.example.nextoliveproject.utility.AppSharedData;
import com.example.nextoliveproject.utility.ConstantVariable;
import com.example.nextoliveproject.utility.Utility;
import com.example.nextoliveproject.views.Food_Detail_Activity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodDeliveryFragment extends Fragment {
    View view;
    RecyclerView topPicks,rvOffers,rvCoupons,rvPopularCuisines,rvHighlight,rvSpotlight,rvPopularBrands,rvBestInsafty;
    public static LogoProgressDialog pdialog;

//    ArrayList<String> arrayListTopPick=new ArrayList<>();
    ArrayList<String> arrayListOffer=new ArrayList<>();
    ArrayList<String> arrayListCoupons=new ArrayList<>();
    ArrayList<String> arrayPopularCuisines=new ArrayList<>();
    ArrayList<String> arrayListHighlight=new ArrayList<>();
//    ArrayList<String> arrayListSpotLight=new ArrayList<>();

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_food_delivery, container, false);
        findViewByIds();
        resturantApi(context);

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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

    public void resturantApi(Context context){
        try {
            if (Utility.isInternetAvailable(context)){
                try {
                    pdialog = new LogoProgressDialog(context);
                    pdialog.setProgress("Please Wait...");

                    String lat = SharedData.latitude(context);
                    String lang = SharedData.longitude(context);
                    String locality = SharedData.locality(context);

                    Log.d(ConstantVariable.Latitude,lat);
                    Log.d(ConstantVariable.Longitude,lang);
                    Log.d(ConstantVariable.Locality,locality);

                    Map<String, String> params = new HashMap();
                    params.put("zipcode", "209827");
                    params.put("locality", "aliganjjj");
                    params.put("lat", "26.8936994");
                    params.put("lng", "80.9576938");
                    JSONObject parameters = new JSONObject(params);
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.getCache().clear();
                    JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, Server_URL.resturant_URL, parameters, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                if(response.getBoolean("success")){

                                    JSONArray jsonArray = response.getJSONArray("data");
                                    AppSharedData.Restaurant.clear();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject json = jsonArray.getJSONObject(i);
                                        HashMap<String, String> dash1 = new HashMap<>();
                                        dash1.put("restaurantid", String.valueOf(json.getInt("restaurantid")));
                                        dash1.put("name", json.getString("name"));
                                        dash1.put("image", json.getString("image"));
                                        dash1.put("lat", json.getString("lat"));
                                        dash1.put("lng", json.getString("lng"));
                                        dash1.put("landmark",json.getString("landmark"));
                                        AppSharedData.Restaurant.add(dash1);
                                    }

                                    addItems();
                                }

                            } catch (Exception ex) {
                                Log.d("Api Error",""+ex);
                            }finally {
                                if (pdialog.getDialog().isShowing()) {
                                    pdialog.getDialog().dismiss();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (pdialog.getDialog().isShowing()) {
                                pdialog.getDialog().dismiss();
                            }
                            Log.d("Api Error 1",""+error);
                        }
                    });
                    sr.setRetryPolicy(new DefaultRetryPolicy(3000, 20, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(sr);

                } catch (Exception ex) {
                    Log.d("Api Error 2",""+ex);
                }
            } else {
                Toast.makeText(context, "Internet Required", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Log.d("Api Error",""+ex);
        }
    }



    private void addItems() {

        RecyclerView.Adapter adapter=new CustomAdapter(AppSharedData.Restaurant,"TopPicks");
        topPicks.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        topPicks.setAdapter(adapter);

        addOfferRv();
        addCouponRv();
        addBestInSafetyRv();
        addHighlightRv();
        addPopularBrandsRv();
        addRvPopularCuisines();
        addSpotLightRv();

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
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvOffers);

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
        final int pixelsToMove = 100;
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

        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvHighlight);

    }
    private void addSpotLightRv() {


        RecyclerView.Adapter adapter=new SpotlightAdapter(AppSharedData.Restaurant);
        GridLayoutManager layoutManager= new GridLayoutManager(getActivity(),2,GridLayoutManager.HORIZONTAL,false);
        rvBestInsafty.setLayoutManager(layoutManager);
        rvBestInsafty.setAdapter(adapter);
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvBestInsafty);

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

        RecyclerView.Adapter adapter=new SpotlightAdapter(AppSharedData.Restaurant);
        GridLayoutManager layoutManager= new GridLayoutManager(getActivity(),2,GridLayoutManager.HORIZONTAL,false);
        rvSpotlight.setLayoutManager(layoutManager);
        rvSpotlight.setAdapter(adapter);
        final SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvSpotlight);
    }



    // recycler Top pick
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<HashMap<String,String>> localDataSet ;
        String type;
         int pos = 0;
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvRestaurent;
            private LinearLayout pick_item;
            private TextView tvTime;
            private ImageView back_image;

            public ViewHolder(View view) {
                super(view);
                tvRestaurent = view.findViewById(R.id.tvRestaurent);
                pick_item = view.findViewById(R.id.pick_item);
                tvTime = view.findViewById(R.id.tvTime);
                back_image = view.findViewById(R.id.back_image);
            }
        }
        public CustomAdapter(ArrayList<HashMap<String,String>> dataSet,String type) {
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
            pos = position;
            HashMap<String, String> Item = localDataSet.get(pos);
            viewHolder.tvRestaurent.setText(Item.get("name"));

            double lat = Double.parseDouble(SharedData.latitude(getContext()));
            double lng = Double.parseDouble(SharedData.longitude(getContext()));
            double lat1 = Double.parseDouble(Item.get("lat"));
            double lng1 = Double.parseDouble(Item.get("lng"));
            float time = Utility.calculateTime(lat,lng,lat1,lng1);
            viewHolder.tvTime.setText(String.format("%s", (int)time)+"min");
            Picasso.with(context).load(Item.get("image")).error(R.drawable.food_one).into(viewHolder.back_image);
            viewHolder.pick_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppSharedData.ChoosedRestaurant.clear();
                    AppSharedData.ChoosedRestaurant.add(Item);
                    startActivity(new Intent(context, Food_Detail_Activity.class));
                }
            });

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

        private ArrayList<HashMap<String,String>> localDataSet ;
         int pos=0;
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvRestaurent,location_distance;
            private ImageView image;
            public ViewHolder(View view) {
                super(view);
                tvRestaurent = view.findViewById(R.id.tvType);
                image = view.findViewById(R.id.image);
                location_distance = view.findViewById(R.id.location_distance);
            }
        }
        public SpotlightAdapter(ArrayList<HashMap<String,String>> dataSet) {
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
            pos = position;
            HashMap<String, String> Item = localDataSet.get(pos);
            viewHolder.tvRestaurent.setText(Item.get("name"));

            double lat = Double.parseDouble(SharedData.latitude(getContext()));
            double lng = Double.parseDouble(SharedData.longitude(getContext()));
            double lat1 = Double.parseDouble(Item.get("lat"));
            double lng1 = Double.parseDouble(Item.get("lng"));
            double time = Utility.distance(lat,lng,lat1,lng1);

            viewHolder.location_distance.setText(Item.get("landmark")+" | "+String.format("%s", (int)Math.round(time))+"km");
            Picasso.with(context).load(Item.get("image")).error(R.drawable.food_one).into(viewHolder.image);

        }
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}