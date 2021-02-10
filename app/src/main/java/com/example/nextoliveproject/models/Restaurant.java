package com.example.nextoliveproject.models;

import android.view.ViewDebug;

import java.util.List;

public class Restaurant {

    public String name;
    public String description;
    public boolean hasPlan;
    public boolean hasOpenArea;
    public boolean hasSmockingArea;
    public String distance;
    public String address;
    public String phoneNumber;
    public String rate;

    public List<ResturantImages> resturantImages;
    public String lat;
    public String longitude;

    public String getFirstImage()
    {
        if (resturantImages!=null && resturantImages.size()>0) {
            return resturantImages.get(0).imageCDNUrl;
        }
        else
        {
            return null;
        }
    }
}
