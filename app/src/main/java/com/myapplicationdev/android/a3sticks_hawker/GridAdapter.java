package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<FoodItem> alFoods;

    public GridAdapter(Context context, int resource, ArrayList<FoodItem> alFoods) {
        super(context, resource, alFoods);
        parent_context = context;
        layout_id = resource;
        this.alFoods = alFoods;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout_id, parent, false);

        ImageView foodImg = view.findViewById(R.id.foodImg);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvPrice = view.findViewById(R.id.tvPrice);

        FoodItem curr = alFoods.get(position);

        tvTitle.setText(curr.getName());
        String price = String.format("%.2f", curr.getPrice());

        tvPrice.setText("$"+price);

        return view;
    }
}
