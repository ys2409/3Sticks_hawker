package com.myapplicationdev.android.a3sticks_hawker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.*;


public class RegisterActivity extends AppCompatActivity {

    EditText etNum;
    EditText etPassword;
    EditText etEmail;
    private AsyncHttpClient client;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNum = findViewById(R.id.etNumberRegister);
        etPassword = findViewById(R.id.etPasswordRegister);
        etEmail = findViewById(R.id.etEmailRegister);
        btnRegister = findViewById(R.id.btnRegister);

        client = new AsyncHttpClient();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.add("number", etNum.getText().toString());
                params.add("password", etPassword.getText().toString());
                params.add("email_address", etEmail.getText().toString());
                client.post("https://3stickscustomer.000webhostapp.com/Hawker/doRegister.php", params, new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), "New user successfully created", Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

        });


    }


}