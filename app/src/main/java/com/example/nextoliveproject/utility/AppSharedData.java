package com.example.nextoliveproject.utility;

import com.example.nextoliveproject.models.CartItems;
import com.example.nextoliveproject.models.FoodItem;

import java.util.ArrayList;
import java.util.HashMap;

public class AppSharedData {

    public static float Total = 0.0f;

    public static ArrayList<HashMap<String,String>> Restaurant = new ArrayList<>();

    public static ArrayList<FoodItem> FoodItems = new ArrayList<>();

    public static ArrayList<CartItems> ArrayCartItems = new ArrayList<>();

    public static ArrayList<HashMap<String,String>> ChoosedRestaurant = new ArrayList<>();




}
