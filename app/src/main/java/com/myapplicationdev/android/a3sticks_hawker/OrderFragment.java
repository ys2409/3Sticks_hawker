package com.myapplicationdev.android.a3sticks_hawker;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class OrderFragment extends Fragment {
    Toolbar toolbar;
    ListView listviewOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
//            @Override
//            public void onRefresh() {
//
//            }
//        });

        toolbar = view.findViewById(R.id.top_toolbar);
        TextView tb = view.findViewById(R.id.toolbar_title1);
        tb.setText("Order");

        listviewOrder = view.findViewById(R.id.listviewOrder);

        ArrayList<Order> items = new ArrayList<Order>();
        ArrayAdapter<Order> aaItems = new ArrayAdapter<Order>(getContext(), android.R.layout.simple_list_item_1, items);

        listviewOrder.setAdapter(aaItems);

        Links link = new Links();
        String url = link.getOrders;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //called when response HTTP status is "200 OK"
                Log.i("Order success", response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject m = (JSONObject) response.get(i);
                        Order item = new Order(m.getInt("order_id"), new String[]{m.getString("food_items")}, m.getDouble("total_price"), m.getString("special"));
                        items.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aaItems.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.i("order error", responseString);
            }
        });

        return view;
    }
}