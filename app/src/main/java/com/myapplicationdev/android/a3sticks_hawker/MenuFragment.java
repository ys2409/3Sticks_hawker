package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
import cz.msebera.android.httpclient.conn.params.ConnManagerParams;
import cz.msebera.android.httpclient.params.HttpConnectionParams;
import cz.msebera.android.httpclient.params.HttpParams;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    Toolbar toolbar;
    GridView gvItems;
    ArrayList<FoodItem> alItems;
    GridAdapter gaItems;
    AsyncHttpClient client;
    Button btnAdd;
    FoodItem foodItem;
    Integer itemId;
    String itemName;
    Double price;
    Boolean soldOut = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);


        gvItems = view.findViewById(R.id.gridView);
        alItems = new ArrayList<FoodItem>();
        client = new AsyncHttpClient();

        toolbar = view.findViewById(R.id.top_toolbar);
        btnAdd = view.findViewById(R.id.btnAddItem);
        TextView tb = view.findViewById(R.id.toolbar_title1);
        tb.setText("Menu");

        gaItems = new GridAdapter(getContext(), R.layout.grid, alItems);
        gvItems.setAdapter(gaItems);

        Bundle bundle = this.getArguments();
        // getSerializable() not sure
        if (foodItem != null) {
            foodItem = (FoodItem) bundle.getSerializable("foodItem");
            itemId = foodItem.getFoodId();
            itemName = foodItem.getName();
            price = foodItem.getPrice();
            soldOut = foodItem.isSoldOut();
        }


        MenuFragment foodItem = new MenuFragment();
        foodItem.setArguments(bundle);

        RequestParams params = new RequestParams();
        params.add("food_item_id", String.valueOf(itemId));
        params.add("name", String.valueOf(itemName));
        params.add("price", String.valueOf(price));
        params.add("soldOut", String.valueOf(soldOut));

//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/getFoodItem.php", new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                //called when response HTTP status is "200 OK"
//                try {
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject food = (JSONObject) response.get(i);
//                        int id = food.getInt("food_item_id");
//                        String name = food.getString("name");
//                        double price = food.getDouble("price");
//
//                        FoodItem foodItem = new FoodItem(id, name, price);
//
//                        alItems.add(foodItem);
//                    }
//
//                    gaItems.notifyDataSetChanged();
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        gvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                FoodItem food = alItems.get(position);
                bundle.putSerializable("food", food);

                Intent b = new Intent(MenuFragment.super.getContext(), EditMenuActivity.class);
                b.putExtra("food", food);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("name", food.getName());
                editor.commit();
                startActivity(b);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(MenuFragment.super.getContext(), AddFoodItemActivity.class);
                startActivity(c);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Can do codes to reload listview in this function
        alItems.clear();
        loadFoods();
    }

    public void loadFoods() {
        Links link = new Links();
        String url = link.getAllFoods;
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //called when response HTTP status is "200 OK"
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject food = (JSONObject) response.get(i);
                        int id = food.getInt("food_item_id");
                        String soldOut = food.getString("soldOut");
                        if (soldOut == "1"){
                            String name = food.getString("name");
                            double price = food.getDouble("price");
                            String image = food.getString("image");

                            FoodItem foodItem = new FoodItem(id, name, price, image , true);
                            alItems.add(foodItem);
                        }
                        else {
                            String name = food.getString("name");
                            double price = food.getDouble("price");
                            String image = food.getString("image");

                            FoodItem foodItem = new FoodItem(id, name, price, image , false);

                            alItems.add(foodItem);
                        }
                    }

                    gaItems.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.i("menu fail", responseString);
            }
        });
    }
}