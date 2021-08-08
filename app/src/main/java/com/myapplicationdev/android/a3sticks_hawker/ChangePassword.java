package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ChangePassword extends AppCompatActivity {
    TextView tvCurrPass1;
    private AsyncHttpClient client;
    EditText etPassword;
    Button btnConfirm;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        client = new AsyncHttpClient();

        etPassword = findViewById(R.id.etPassword);
        btnConfirm = findViewById(R.id.button);
        toolbar = findViewById(R.id.top_toolbar);
        TextView tt = toolbar.findViewById(R.id.toolbar_title1);
        tt.setText("Change Password");
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.getNavigationIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ChangePassword.this);
        String ownerID = prefs.getString("ownerID", "");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword.this.finish();
            }
        });

        Links link = new Links();
        String url = link.editPassword;

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etPassword.getText().toString().equals("")) {
                    RequestParams params = new RequestParams();
                    params.add("ownerID", ownerID);
                    params.add("password", etPassword.getText().toString());
                    Log.d("TAG", params.toString());

                    client.post(url, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            //called when response HTTP status is "200 OK"
                            try {
                                Toast.makeText(getApplicationContext(), "Password successfully updated", Toast.LENGTH_SHORT).show();
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //aaProfile.notifyDataSetChanged();
                        }

                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            Log.d("Failed: ", responseString);
                        }

                    });
                    Log.d("TAG", "onCreate: ");
                    Intent y = new Intent(ChangePassword.this, LoginActivity.class);
                    startActivity(y);
                }else{
                    etPassword.setError("Please enter new password");
                }
            }

        });
    }


}
