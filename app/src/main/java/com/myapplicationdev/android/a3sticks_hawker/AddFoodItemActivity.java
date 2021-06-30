package com.myapplicationdev.android.a3sticks_hawker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddFoodItemActivity extends AppCompatActivity {

    Button btnAddItem;
    AsyncHttpClient client;
    EditText etName, etPrice;
    ArrayList<FoodItem> alItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);

        TextView tb = findViewById(R.id.toolbar_title1);
        tb.setText("Add Food Item");

        client = new AsyncHttpClient();


        btnAddItem = findViewById(R.id.btnAdd);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();

                int lastId = alItems.size() - 1;
                Bundle bundle = new Bundle();
                FoodItem food = new FoodItem(lastId + 1, etName.getText().toString(), Double.parseDouble(etPrice.getText().toString()));
                bundle.putSerializable("foodItem", food);

                client.post("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/addFoodItem.php", new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //called when response HTTP status is "200 OK"
                        try {
                            String result = response.getString("inserted");
                            if (result.contains("Success")) {
                                String msg = "Added to order";
                                Toast.makeText(AddFoodItemActivity.this, msg, Toast.LENGTH_SHORT).show();
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.container, new MenuFragment())
                                        .commit();
                            } else {
                                String msg = "Failed to add to order";
                                Toast.makeText(AddFoodItemActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
}