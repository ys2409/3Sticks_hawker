package com.myapplicationdev.android.a3sticks_hawker;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        toolbar = view.findViewById(R.id.top_toolbar);
        TextView tb = view.findViewById(R.id.toolbar_title1);
        tb.setText("Order");

        ArrayList<Order> items = new ArrayList<Order>();
        ArrayAdapter<Order> aaItems = null;



        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/getOrderById.php", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //called when response HTTP status is "200 OK"
                try {
                    for(int i = 0; i<response.length(); i++){
                        JSONObject m = (JSONObject)response.get(i);
                        Order item = new Order(m.getInt("order_id"), new String[]{m.getString("food_items")}, m.getDouble("total_price"), m.getString("special"));
                        items.add(item);
                    }
                } catch(JSONException e){

                }
                aaItems.notifyDataSetChanged();
            }
        });
        return view;
    }
}