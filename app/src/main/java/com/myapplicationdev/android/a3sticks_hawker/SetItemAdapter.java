package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SetItemAdapter extends ArrayAdapter {

    ArrayList<String> alIncluded;
    Context context;
    int resource;

    public SetItemAdapter(Context context, int resource, ArrayList<String> alIncluded) {
        super(context, resource, alIncluded);
        this.context = context;
        this.resource = resource;
        this.alIncluded = alIncluded;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(resource, parent, false);

        String curr = alIncluded.get(position);

//        String[] item = curr.split("x");
//        int quantity = Integer.parseInt(item[0].trim());
//        String name = item[1].trim();

        TextView tvItem = view.findViewById(R.id.tvItem);
        ImageView btnRemove = view.findViewById(R.id.btnRemove);

        tvItem.setText(curr);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete item and update data
                alIncluded.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}
