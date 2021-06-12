package com.myapplicationdev.android.a3sticks_hawker;

public class Profile {
    private int id;
    private String name;
    private String stall_name;

    public Profile(int id, String name, String stall_name) {
        this.id = id;
        this.name = name;
        this.stall_name = stall_name;
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

    public String getStall_name() {
        return stall_name;
    }

    public void setStall_name(String stall_name) {
        this.stall_name = stall_name;
    }
}
