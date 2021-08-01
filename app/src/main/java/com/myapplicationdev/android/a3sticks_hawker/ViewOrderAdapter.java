package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ViewOrderAdapter extends ArrayAdapter {

    private Context context;
    ArrayList<Order> alOrders;

    public ViewOrderAdapter(Context context, int resource, ArrayList<Order> alOrders) {
        super(context, resource, alOrders);
        this.context = context;
        this.alOrders = alOrders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row2, parent, false);

        TextView tvqty = rowView.findViewById(R.id.tvqty);
        TextView tvitem = rowView.findViewById(R.id.tvitem);
        TextView tvprice = rowView.findViewById(R.id.tvprice);
        TextView tvSpecial = rowView.findViewById(R.id.tvSpecial);

        Order curr = alOrders.get(position);
        String name = curr.getName();
        int qty = curr.getQty();
        double price = curr.getTotal_price();
        String includes = curr.getIncludes();

        tvitem.setText(name);
        tvqty.setText(qty + " x ");

        tvprice.setText(String.format("$%.2f", price));

        tvSpecial.setText(includes);

        return rowView;
    }
}
