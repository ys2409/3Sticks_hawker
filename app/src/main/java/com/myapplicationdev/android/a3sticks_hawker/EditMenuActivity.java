package com.myapplicationdev.android.a3sticks_hawker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        TextView tb = findViewById(R.id.toolbar_title1);
        tb.setText("Edit Food Item");


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


        if (foodId == -1){
            tvName.setVisibility(View.GONE);
        }
        else{
            tvName.setText(String.valueOf(name));
        }

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
                        client.post("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/deleteFoodItems.php", new JsonHttpResponseHandler(){
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    try {
                                        String result = response.getString("result");
                                        Toast.makeText(EditMenuActivity.this, result, Toast.LENGTH_SHORT).show();

                                        if (result.contains("Success")){
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
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(getApplicationContext(), MenuFragment.class);
        startActivityForResult(intent, 0);
        return true;
    }
}
