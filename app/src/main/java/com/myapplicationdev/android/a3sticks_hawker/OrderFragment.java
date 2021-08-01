package com.myapplicationdev.android.a3sticks_hawker;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class OrderFragment extends Fragment {
    Toolbar toolbar;
    TextView tvOrderId, tvTotal;
    ListView lvDetails;
    Button btnReady;
    Order order;
    int orderId;
    String total;
    String[] details;
    ArrayList<Order> alOrder;
    ArrayAdapter aa;
    SwipeRefreshLayout swipeRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        tvOrderId = view.findViewById(R.id.tvOrderNum);
        tvTotal = view.findViewById(R.id.tvTotal);
        lvDetails = view.findViewById(R.id.listviewOrder);
        btnReady = view.findViewById(R.id.button2);
        toolbar = view.findViewById(R.id.top_toolbar);
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.getNavigationIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

        Bundle bundle = this.getArguments();
        order = (Order) bundle.getSerializable("order");
        orderId = getArguments().getInt("id", -1);
        tvOrderId.setText(String.valueOf(orderId));

        toolbar = view.findViewById(R.id.top_toolbar);
        TextView tb = view.findViewById(R.id.toolbar_title1);
        tb.setText("Order Details");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new HomeFragment())
                        .commit();
            }
        });

        ArrayList<Order> items = new ArrayList<Order>();
        ArrayAdapter<Order> aaItems = null;

        Links links = new Links();

        getOrderById();

//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get("https://3stickscustomer.000webhostapp.com/Hawker/getOrderById.php", new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                //called when response HTTP status is "200 OK"
//                try {
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject m = (JSONObject) response.get(i);
//                        Order item = new Order(m.getInt("order_id"), new String[]{String.valueOf(m.getJSONArray("food_items"))}, m.getDouble("total_price"), m.getString("special"));
//                        alOrder.add(item);
//                        if (String.valueOf(item.getId()) == String.valueOf(orderId)) {
//                            tvTotal.setText(String.valueOf(item.getTotal_price()));
//                            aa.add(item.getItems());
//                            lvDetails.setAdapter(aa);
//                        }
//                    }
//                } catch (JSONException e) {
//
//                }
//                aaItems.notifyDataSetChanged();
//            }
//        });

        return view;
    }

    public void getOrderById() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("orderId", String.valueOf(orderId));
        String url = "https://3stickscustomer.000webhostapp.com/Customer/getOrderDetailsById_Alicia.php";

        Log.i("orderId", String.valueOf(orderId));
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.i("order", response.toString());
                    double total = response.getDouble("total_price");
                    tvTotal.setText(String.format("$%.2f", total));
                    JSONArray cartItems = response.getJSONArray("cartItems");
                    String allItems = "";
                    for (int i = 0; i < cartItems.length(); i++) {
                        JSONObject curr = cartItems.getJSONObject(i);

                        Log.i(String.valueOf(i), curr.toString());

                        String name = curr.getString("name");
                        String items = curr.getString("items");
                        String additional = curr.getString("additional");
                        String qty = curr.getString("quantity");
                        String instructions = curr.getString("instructions");


                        String[] includes = items.split(", ");
                        String[] additionals = additional.split(", ");

                        String currAdd = "";

                        for (int it = 0; it < includes.length; it++) {
                            if (!includes[it].equals("")) {
                                currAdd += String.format("%1s - %s\n",
                                        "", includes[it]);
                            }
                        }

                        for (int a = 0; a < additionals.length; a++) {
                            if (!additionals[a].equals("")) {
                                currAdd += String.format("%3s + %s\n",
                                        "", additionals[a]);
                            }
                        }

                        if (!currAdd.equals("")) {
                            allItems += String.format("\n%s x %s \n%5s",
                                    qty, name, currAdd);
                        } else {
                            allItems += String.format("\n%s x %s",
                                    qty, name);
                        }

                        if (!instructions.equals("")) {
                            allItems += String.format("%1s *** %5s",
                                    "", instructions);
                        }

                        Log.i(String.valueOf(i), allItems);
                    }

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable
                    throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failure", responseString);
            }
        });
    }
}