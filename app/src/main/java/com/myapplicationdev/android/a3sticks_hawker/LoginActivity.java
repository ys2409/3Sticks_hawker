package com.myapplicationdev.android.a3sticks_hawker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Button showHideBtn;
    EditText etPassword;

    int view = R.layout.activity_main;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        showHideBtn = findViewById(R.id.showHideBtn);
        etPassword = findViewById(R.id.etPassword);

        showHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(showHideBtn.getText().toString().equals("Show")){
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHideBtn.setText("Hide");
                } else{
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHideBtn.setText("Show");
                }
            }
        });

    }
}