package com.myapplicationdev.android.a3sticks_hawker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    Button showHideBtn;
    EditText etPassword;
    Button btnRegister;
    Button btnLogin;
    Button btnForgetPassword;
    EditText etNumber;
    private AsyncHttpClient client;
    CheckBox checkBox;

    int view = R.layout.activity_main;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegisterLogin);
        btnLogin = findViewById(R.id.btnLogin);
        etNumber = findViewById(R.id.etNum);
        checkBox = findViewById(R.id.checkBox);
        btnForgetPassword = findViewById(R.id.btnForgot);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        String ownerID = prefs.getString("ownerID", "");

        if (!ownerID.equals("")) {
            Intent a = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(a);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked() == true) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(b);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etNumber.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Login failed. Please enter username.", Toast.LENGTH_LONG).show();

                } else if (password.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Login failed. Please enter password.", Toast.LENGTH_LONG).show();

                } else {
                    //proceed to authenticate user
                    OnLogin(view);
                }
            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(LoginActivity.this, ChangePassword.class);
                startActivity(x);
            }
        });

    }

    private void OnLogin(View v) {
        client = new AsyncHttpClient();
        // Point X - TODO: call doLogin web service to authenticate user

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        Links links = new Links();
        String url = links.doLogin;

        RequestParams params = new RequestParams();
        params.add("number", etNumber.getText().toString());
        params.add("password", etPassword.getText().toString());
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //called when response HTTP status is "200 OK"

                try {
                    if (response.get("authenticated").toString().equals("false")) {
                        Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("ownerID", response.get("owner_id").toString());
                        editor.putString("name", response.get("name").toString());
                        editor.putString("password", response.get("password").toString());
                        editor.putString("number", response.get("phone_num").toString());
                        editor.putString("stallID", response.get("stall_id").toString());
                        editor.commit();
                        Intent a = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(a);
                        Log.d("TAG", response.get("owner_id").toString());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", responseString);
            }
        });


    }


}