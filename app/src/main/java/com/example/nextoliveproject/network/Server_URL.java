package com.example.nextoliveproject.network;

public class Server_URL {

    public static String Base_URL = "http://38.17.52.106/";

    public static String Type_URL = "restaurant/api/";

    public static String already_register_user_URL = Base_URL+ Type_URL + "alredyexists-customer";

    public static String register_user_URL = Base_URL+ Type_URL + "register-customer";

    public static String login_user_URL = Base_URL+Type_URL+"login-customer";

    public static String resturant_URL = Base_URL + Type_URL +"get-restaurent-filter";

    public static String food_URL = Base_URL + Type_URL +"get-restaurent-food/";

}
