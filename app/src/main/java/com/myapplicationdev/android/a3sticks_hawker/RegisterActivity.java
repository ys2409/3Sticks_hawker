package com.myapplicationdev.android.a3sticks_hawker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNum = findViewById(R.id.etNumberRegister);
        etPassword = findViewById(R.id.etPasswordRegister);
        etEmail = findViewById(R.id.etEmailRegister);

    }


}