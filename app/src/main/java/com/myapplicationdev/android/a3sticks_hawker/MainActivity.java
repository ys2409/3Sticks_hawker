package com.myapplicationdev.android.a3sticks_hawker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView lvOrder;
    ArrayList<Order> orders;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvOrder = (ListView) this.findViewById(R.id.listview);
        toolbar = findViewById(R.id.top_toolbar);
        TextView tb = findViewById(R.id.toolbar_title);
        tb.setText("Orders");

        orders = new ArrayList<Order>();
        aa = new OrderAdapter(this, R.layout.row, orders);
        lvOrder.setAdapter(aa);

        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}