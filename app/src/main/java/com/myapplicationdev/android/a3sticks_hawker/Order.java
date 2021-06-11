package com.myapplicationdev.android.a3sticks_hawker;

public class Order {
    private int num;
    private String[] items;
    private boolean newOrder;
    private boolean ready;


    public Order(int num, String[] items) {
        this.num = num;
        this.items = items;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
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
