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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
//    ViewOrderAdapter adapter;

    // for notification
    int customerID = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        tvOrderId = view.findViewById(R.id.tvOrderNum);
        tvTotal = view.findViewById(R.id.tvTotal);
        lvDetails = view.findViewById(R.id.listviewOrder);
        btnReady = view.findViewById(R.id.btnReady);
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

        alOrder = new ArrayList<Order>();

        getOrderById();

        btnReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // codes to notify customer when food is ready to collect
                if (customerID != 0) {
                    String TOPIC = "/topics/order" + customerID;

                    String title = "Order Ready";
                    String content = "Your order is ready for collection";

                    Notification message = new Notification(title, content);
                    PushNotification data = new PushNotification(message, TOPIC);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    NotificationAPI apiService =
                            retrofit.create(NotificationAPI.class);
                    Call<Response> call = apiService.postNotification(data);
                    call.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if (!response.isSuccessful()) {
                                Log.d("TAG", String.valueOf(response.code()));
                                return;
                            }

                            Toast.makeText(getContext(), "Notification Sent", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Log.d("TAG", t.getMessage());
                        }
                    });
                } else {
                    Log.d("notification error", "customerID is empty");
                }
            }
        });

        return view;
    }

    public void getOrderById() {
        // setting to adapter


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
                    // for order
                    int id = response.getInt("order_id");
                    double total = response.getDouble("total_price");
                    customerID = response.getInt("customer_id");
                    tvTotal.setText(String.format("$%.2f", total));

                    // for items in order
                    JSONArray cartItems = response.getJSONArray("cartItems");
                    for (int i = 0; i < cartItems.length(); i++) {
                        JSONObject curr = cartItems.getJSONObject(i);
                        String allItems = "";

                        Log.i(String.valueOf(i), curr.toString());

                        String name = curr.getString("name");
                        String items = curr.getString("items");
                        String additional = curr.getString("additional");
                        int qty = curr.getInt("quantity");
                        String instructions = curr.getString("instructions");
                        double price = curr.getDouble("total");

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
                            allItems += String.format("\n%5s", currAdd);
                        }

                        if (!instructions.equals("")) {
                            allItems += String.format("%1s *** %5s",
                                    "", instructions);
                        }

                        Order order = new Order(id, name, qty, price, allItems);
                        alOrder.add(order);
                    }
                    ViewOrderAdapter adapter = new ViewOrderAdapter(getContext(), R.layout.row2, alOrder);
                    lvDetails.setAdapter(adapter);
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