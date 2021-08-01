package com.myapplicationdev.android.a3sticks_hawker;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private String[] items;
    private Double total_price;
    private String special;
    private boolean newOrder;
    private boolean ready;

    // Alicia
    private String cart_item_id;
    private String verificationCode;
    private String order_date;
    private String name;
    private int qty;
    private String includes;

    public Order(int id, String cart_item_id, String verificationCode, String date) {
        this.id = id;
        this.cart_item_id = cart_item_id;
        this.verificationCode = verificationCode;
        this.order_date = date;
    }

    public Order(int id, String name, int qty, double price, String includes) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.total_price = price;
        this.includes = includes;
    }

    public Order(int id, String[] items, Double total_price, String special) {
        this.id = id;
        this.items = items;
        this.total_price = total_price;
        this.special = special;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public boolean isNewOrder() {
        return newOrder;
    }

    public void setNewOrder(boolean newOrder) {
        this.newOrder = newOrder;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getCart_item_id() {
        return cart_item_id;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getOrderDate() {
        return order_date;
    }

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    public String getIncludes() {
        return includes;
    }
}
