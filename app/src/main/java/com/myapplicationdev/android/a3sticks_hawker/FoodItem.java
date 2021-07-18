package com.myapplicationdev.android.a3sticks_hawker;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private int foodId;
    private String name;
    private Double price;
    private String image;

    public FoodItem(int foodId, String name, Double price) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
    }

    public FoodItem(int foodId, String name, Double price, String image){
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

}
