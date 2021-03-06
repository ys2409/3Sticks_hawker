package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import java.io.Serializable;
import java.util.ArrayList;

class OrdersAdapter extends ArrayAdapter<Order> {
    private ArrayList<Order> alOrder;
    private Context context;
    private TextView tvOrder;
    private Button btnView;

    public OrdersAdapter(Context context, int resource, ArrayList<Order> objects){
        super(context, resource, objects);
        // Store the food that is passed to this adapter
        alOrder = objects;
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
        View rowView = inflater.inflate(R.layout.row1, parent, false);

        // Get the TextView object
        tvOrder = (TextView) rowView.findViewById(R.id.tvOrder);
        // Get the ImageView object
        btnView = (Button) rowView.findViewById(R.id.btnView);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnView.setText("View");

                Bundle bundle = new Bundle();
                Order order = alOrder.get(position);
                bundle.putSerializable("order", order);
                bundle.putInt("id", order.getId());
                OrderFragment orderDetails = new OrderFragment();
                orderDetails.setArguments(bundle);

                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                fm.beginTransaction()
                .replace(R.id.container, orderDetails)
                .commit();

            }
        });

        // The parameter "position" is the index of the
        // Row ListView is requesting.
        // We get back the food at the same index.
        Order currentOrder = alOrder.get(position);
        // Set the TextView to show the food

        tvOrder.setText("Order " + currentOrder.getId());
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
