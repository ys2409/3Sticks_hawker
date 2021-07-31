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
    private int qNumber;
    private String verificationCode;
    private int estWaitingTime;
    private String order_date;

    public Order(int id, String cart_item_id, int qNumber, String verificationCode,
                 int estWaitingTime, String date) {
        this.id = id;
        this.cart_item_id = cart_item_id;
        this.qNumber = qNumber;
        this.verificationCode = verificationCode;
        this.estWaitingTime = estWaitingTime;
        this.order_date = date;
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

    public int getqNumber() {
        return qNumber;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public int getEstWaitingTime() {
        return estWaitingTime;
    }


    public String getOrderDate(){
        return order_date;
    }
}
