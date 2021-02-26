package com.example.nextoliveproject.models;

public class FoodItem {

    String productid;
    String name;
    String image;
    int price;
    String availability;
    String descriptions;
    String[] tags;
    int menuId;

    public FoodItem() {
    }

    public FoodItem(String productid, String name, String image, int price, String availability, String descriptions, String[] tags, int menuId) {
        this.productid = productid;
        this.name = name;
        this.image = image;
        this.price = price;
        this.availability = availability;
        this.descriptions = descriptions;
        this.tags = tags;
        this.menuId = menuId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
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




}
