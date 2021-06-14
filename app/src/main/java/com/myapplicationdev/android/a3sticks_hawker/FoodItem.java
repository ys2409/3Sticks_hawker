package com.myapplicationdev.android.a3sticks_hawker;

public class FoodItem {
    private int id;
    private String name;
    private Double price;
    private String special;

    public FoodItem(int id, String name, Double price, String special) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.special = special;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }
}
