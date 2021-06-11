package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

class OrderAdapter extends ArrayAdapter<Order> {
    private ArrayList<Order> order;
    private Context context;
    private TextView tvOrder;
    private Button btnView;

    public OrderAdapter(Context context, int resource, ArrayList<Order> objects){
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        order = objects;
        // Store Context object as we would need to use it later
        this.context = context;
    }

    // getView() is the method ListView will call to get the
    // View object every time ListView needs a row


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The usual way to get the LayoutInflater object to
        // "inflate" the XML file into a View object
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // "Inflate" the row.xml as the layout for the View object
        View rowView = inflater.inflate(R.layout.row, parent, false);

        // Get the TextView object
        tvOrder = (TextView) rowView.findViewById(R.id.tvOrder);
        // Get the ImageView object
        btnView = (Button) rowView.findViewById(R.id.btnView);


        // The parameter "position" is the index of the
        // Row ListView is requesting.
        // We get back the food at the same index.
        Order currentOrder = order.get(position);
        // Set the TextView to show the food

        tvOrder.setText("Order " + currentOrder.getNum());
        // Set the image to star or nonstar accordingly
        if(currentOrder.isNewOrder()){
            btnView.setText("New");
        }
        else if (currentOrder.isReady()){
            btnView.setText("Ready");
        }
        // Return the nicely done up View to the ListView
        return rowView;
    }
}
