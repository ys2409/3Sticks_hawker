package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


public class HomeFragment extends Fragment {
    Toolbar toolbar;
    ListView lvOrder;
    ArrayList<Order> orders;
    ArrayAdapter aa;
    SwipeRefreshLayout swipeRefresh;

    public HomeFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lvOrder = (ListView) view.findViewById(R.id.listview);
        swipeRefresh = view.findViewById(R.id.swiperefresh);
        toolbar = view.findViewById(R.id.top_toolbar);
        TextView tb = view.findViewById(R.id.toolbar_title1);
        tb.setText("Orders");

        orders = new ArrayList<Order>();
        ArrayAdapter<Order> aaOrders = null;
        aa = new OrdersAdapter(getActivity(), R.layout.row1, orders);

        lvOrder.setAdapter(aa);

        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new OrderFragment())
                        .commit();

            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        Links links = new Links();
        String url = links.getOrders;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //called when response HTTP status is "200 OK"
                try {
                    for(int i = 0; i<response.length(); i++){
                        JSONObject o = (JSONObject)response.get(i);
                        Order order = new Order(o.getInt("order_id"), new String[]{o.getString("food_items")}, o.getDouble("total_amount"), o.getString("special"));
                        orders.add(order);
                    }
                    aa.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
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