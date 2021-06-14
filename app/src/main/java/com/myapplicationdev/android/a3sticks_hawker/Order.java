package com.myapplicationdev.android.a3sticks_hawker;

public class Order {
    private int id;
    private String[] items;
    private Double total_price;
    private boolean newOrder;
    private boolean ready;


    public Order(int id, String[] items, Double total_price) {
        this.id = id;
        this.items = items;
        this.total_price = total_price;
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
}
