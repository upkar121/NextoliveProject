package com.example.nextoliveproject.models;

public class FoodItem {

    String productid;
    int userId;
    String name;
    String image;
    int price;
    String availability;
    String descriptions;
    String title;
    int menuId;
    String date;

    public FoodItem() {
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FoodItem(String productid, int userId, String name, String image, int price, String availability, String descriptions, String title, int menuId) {
        this.productid = productid;
        this.userId = userId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.availability = availability;
        this.descriptions = descriptions;
        this.title = title;
        this.menuId = menuId;
    }
}
