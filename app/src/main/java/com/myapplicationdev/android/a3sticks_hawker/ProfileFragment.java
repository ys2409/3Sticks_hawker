package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.loopj.android.http.*;
import cz.msebera.android.httpclient.*;


import cz.msebera.android.httpclient.Header;

public class ProfileFragment extends Fragment {
    Toolbar toolbar;
    ArrayList<Profile> alProfile = new ArrayList<>();
    ArrayAdapter<Profile> aaProfile;
    Profile profile;
    TextView tvStallName;
    TextView tvOwnerName;
    Button btnChange;
    Button btnTime;
    Button btnLogout;
    private AsyncHttpClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<Profile> alProfile = new ArrayList<Profile>();
        ArrayAdapter<Profile> aaProfile = null;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvOwnerName = view.findViewById(R.id.tvOwner);
        tvStallName = view.findViewById(R.id.tvStall);

        btnChange = view.findViewById(R.id.btnChangePass);
        btnTime = view.findViewById(R.id.btnSetTime);
        btnLogout = view.findViewById(R.id.btnLogout);

        toolbar = view.findViewById(R.id.top_toolbar);
        TextView tb = view.findViewById(R.id.toolbar_title1);
        tb.setText("Profile");

        client = new AsyncHttpClient();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String ownerID = prefs.getString("ownerID", "");
        String stallID = prefs.getString("stallID", "");

        RequestParams params = new RequestParams();
        params.add("ownerID", ownerID);


        client.get("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/getProfile.php", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //called when response HTTP status is "200 OK"
                try {
                    for(int i = 0; i<response.length(); i++){
                        JSONObject profile = (JSONObject)response.get(i);
                        String p = profile.getString("name");
                        String stallName = profile.getString("stall_name");
                        tvOwnerName.setText(p);
                        tvStallName.setText(stallName);
                        Log.d("oooooooooooooo",profile.toString());
                        //alProfile.add(p);
                    }
                } catch(JSONException e){

                }
                //aaProfile.notifyDataSetChanged();
            }
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", responseString);
            }
        });

//        client.get("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/getStallInfo.php", params, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                //called when response HTTP status is "200 OK"
//                try {
//                    for(int i = 0; i<response.length(); i++){
//                        JSONObject stall = (JSONObject)response.get(i);
//                        String s = stall.getString("name");
//                        tvStallName.setText(s.toString());
//                        //alProfile.add(p);
//                    }
//                } catch(JSONException e){
//
//                }
//                //aaProfile.notifyDataSetChanged();
//            }
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
//                Log.d("Failed: ", responseString);
//            }
//        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(ProfileFragment.super.getContext(), ChangePassword.class);
                startActivity(a);
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(ProfileFragment.super.getContext(), TimeActivity.class);
                startActivity(b);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.remove("ownerID");
                prefEdit.remove("name");
                prefEdit.remove("password");
                prefEdit.remove("number");
                prefEdit.remove("stallID");
                prefEdit.commit();
                Intent c = new Intent(ProfileFragment.super.getContext(), LoginActivity.class);
                startActivity(c);
            }
        });

        return view;
    }
}