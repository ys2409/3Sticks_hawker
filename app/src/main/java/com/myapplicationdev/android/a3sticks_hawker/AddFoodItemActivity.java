package com.myapplicationdev.android.a3sticks_hawker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AddFoodItemActivity extends AppCompatActivity {

    Button btnAddItem;
    AsyncHttpClient client;
    EditText etName, etPrice;
    ArrayList<FoodItem> alItems;
    ListView lvItems;
    ArrayList<String> alIncluded;
    TextView tvAddIncluded;
    SetItemAdapter itemAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);

        TextView tb = findViewById(R.id.toolbar_title1);
        tb.setText("Add Food Item");

        toolbar = findViewById(R.id.top_toolbar);
        client = new AsyncHttpClient();
        lvItems = findViewById(R.id.lvItems);
        alIncluded = new ArrayList<String>();
        etName = findViewById(R.id.editTextTextPersonName3);
        etPrice = findViewById(R.id.etPrice2);

        btnAddItem = findViewById(R.id.btnAdd);
        tvAddIncluded = findViewById(R.id.tvAddIncluded);
        tvAddIncluded.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.getNavigationIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

        itemAdapter = new SetItemAdapter(getApplicationContext(), R.layout.set_includes_row, alIncluded);
        lvItems.setAdapter(itemAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Adding items included in set
        tvAddIncluded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddFoodItemActivity.this);
                View layout = getLayoutInflater().inflate(R.layout.dialog_set_included, null);

                EditText etName = layout.findViewById(R.id.name);
                EditText etQty = layout.findViewById(R.id.quantity);

                builder.setView(layout);
                builder.setCancelable(false);
                builder.setNeutralButton("Cancel", null);
                builder.setPositiveButton("Confirm", null);

                AlertDialog dialog = builder.create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button btnPositive = ((AlertDialog) dialogInterface).getButton(AlertDialog.BUTTON_POSITIVE);

                        btnPositive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Boolean cName = isBlank(etName);
                                Boolean cQty = isBlank(etQty);

                                if (!cName && !cQty) {
                                    String name = etName.getText().toString();
                                    int qty = Integer.parseInt(etQty.getText().toString());

                                    if (qty > 0) {
                                        String items = qty + " x " + name;

                                        alIncluded.add(items);
                                        itemAdapter.notifyDataSetChanged();

                                        dialogInterface.dismiss();
                                    } else {
                                        etQty.setError("Please enter more than 0");
                                    }
                                }
                            }
                        });
                    }
                });

                dialog.show();
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();

                String name = etName.getText().toString();
                String price = etPrice.getText().toString();

                if (name.isEmpty()) {
                    etName.setError("Please enter name");
                }
                if (price.isEmpty()) {
                    etPrice.setError("Please enter price");
                }

                if (!name.isEmpty() && !price.isEmpty()) {
                    addItem();
                }

//                int lastId = alItems.size() - 1;
//                Bundle bundle = new Bundle();
//                FoodItem food = new FoodItem(lastId + 1, etName.getText().toString(), Double.parseDouble(etPrice.getText().toString()));
//                bundle.putSerializable("foodItem", food);
//
//                client.post("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/addFoodItem.php", new JsonHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                        //called when response HTTP status is "200 OK"
//                        try {
//                            String result = response.getString("inserted");
//                            if (result.contains("Success")) {
//                                String msg = "Added to order";
//                                Toast.makeText(AddFoodItemActivity.this, msg, Toast.LENGTH_SHORT).show();
//                                getSupportFragmentManager()
//                                        .beginTransaction()
//                                        .replace(R.id.container, new MenuFragment())
//                                        .commit();
//                            } else {
//                                String msg = "Failed to add to order";
//                                Toast.makeText(AddFoodItemActivity.this, msg, Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

            }
        });
    }

    // Alicia's edited addItem function
    public void addItem() {
        AsyncHttpClient client = new AsyncHttpClient();
        double price = Double.parseDouble(etPrice.getText().toString());

        RequestParams params = new RequestParams();
        params.put("name", etName.getText().toString());
        params.put("price", String.format("%.2f", price));
        params.put("included", alIncluded);

        String url = "https://3stickscustomer.000webhostapp.com/Customer/addFoodItem.php";

        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    boolean added = response.getBoolean("added");
                    String msg = response.getString("msg");
                    Toast.makeText(AddFoodItemActivity.this, msg, Toast.LENGTH_SHORT).show();

                    if (added) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("AddItem error", responseString);
            }
        });
    }

    public boolean isBlank(EditText editText) {
        // If user enters space, use .trim() to remove empty spaces.
        String text = editText.getText().toString().trim();

        Boolean check = false;

        if (text.isEmpty()) {
            editText.setError("please fill in " + editText.getHint());
            check = true;
        }
        return check;
    }
}