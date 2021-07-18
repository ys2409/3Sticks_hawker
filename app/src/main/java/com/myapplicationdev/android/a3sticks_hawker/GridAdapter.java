package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

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

        tvTitle.setEllipsize(TextUtils.TruncateAt.END);
        tvTitle.setMaxLines(1);

        FoodItem curr = alFoods.get(position);

        String image = curr.getImage();

        if (image.isEmpty()) {
            foodImg.setImageResource(R.drawable.no_image);
        } else {
            Picasso.get()
                    .load(image)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.no_image)
                    .into(foodImg);
        }

        tvTitle.setText(curr.getName());
        String price = String.format("%.2f", curr.getPrice());

        tvPrice.setText("$" + price);

        return view;
    }
}
