package com.myapplicationdev.android.a3sticks_hawker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AddFoodItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);

        TextView tb = findViewById(R.id.toolbar_title1);
        tb.setText("Add Food Item");
    }
}