package com.myapplicationdev.android.a3sticks_hawker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EditMenuActivity extends AppCompatActivity {

    Button btnSold, btnDel;
    TextView tvName;
    ImageView imgFood;
    EditText etPrice;
    AsyncHttpClient client;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        toolbar = findViewById(R.id.top_toolbar);
        TextView tb = findViewById(R.id.toolbar_title1);
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.getNavigationIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

        tvName = findViewById(R.id.tvfoodName);
        btnSold = findViewById(R.id.btnSold);
        btnDel = findViewById(R.id.btnDel);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        client = new AsyncHttpClient();


        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        int foodId = prefs.getInt("foodId", 1);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String name = pref.getString("name", "");


        if (foodId == -1) {
            tvName.setVisibility(View.GONE);
        } else {
            tvName.setText(String.valueOf(name));
            tb.setText(name);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditMenuActivity.this.finish();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditMenuActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Are you sure you want to delete this food item?");
                builder.setNeutralButton("No", null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestParams params = new RequestParams();
                        params.add("foodId", String.valueOf(foodId));

                        AsyncHttpClient client = new AsyncHttpClient();
                        client.post("https://3stickscustomer.000webhostapp.com/Hawker/deleteFoodItems.php", new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                try {
                                    String result = response.getString("result");
                                    Toast.makeText(EditMenuActivity.this, result, Toast.LENGTH_SHORT).show();

                                    if (result.contains("Success")) {
                                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(EditMenuActivity.this);
                                        SharedPreferences.Editor prefEdit = prefs.edit();
                                        prefEdit.remove("foodId");
                                        prefEdit.commit();

                                        getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.container, new MenuFragment())
                                                .commit();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });

                builder.show();
            }
        });

        btnSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), MenuFragment.class);
        startActivityForResult(intent, 0);
        return true;
    }
}
