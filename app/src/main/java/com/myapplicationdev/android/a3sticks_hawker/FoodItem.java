package com.myapplicationdev.android.a3sticks_hawker;

public class FoodItem {
    private int id;
    private String name;
    private String price;
    private String additional;

    public FoodItem(int id, String name, String price, String additional) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.additional = additional;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }
}
