package com.myapplicationdev.android.a3sticks_hawker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.*;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;


import java.util.ArrayList;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.*;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigation;
    Toolbar toolbar;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.top_toolbar);
        tvTitle = findViewById(R.id.toolbar_title1);

        loadFragment(new HomeFragment());

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

    }
    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        tvTitle = findViewById(R.id.toolbar_title1);

        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                tvTitle.setText("Home");
                break;
            case R.id.nav_menu:
                fragment = new MenuFragment();
                tvTitle.setText("Menu");
                break;
            case R.id.nav_account:
                fragment = new ProfileFragment();
                tvTitle.setText("Profile");
                break;
        }

        return loadFragment(fragment);
    }

}