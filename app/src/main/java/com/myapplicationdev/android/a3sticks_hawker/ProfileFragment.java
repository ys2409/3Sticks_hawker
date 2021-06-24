package com.myapplicationdev.android.a3sticks_hawker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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

        toolbar = view.findViewById(R.id.top_toolbar);
        TextView tb = view.findViewById(R.id.toolbar_title1);
        tb.setText("Profile");

        RequestParams params = new RequestParams();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/getProfile.php", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //called when response HTTP status is "200 OK"
                try {
                    for(int i = 0; i<response.length(); i++){
                        JSONObject profile = (JSONObject)response.get(i);
                        String p = profile.getString("name");
                        tvOwnerName.setText(p.toString());
                        //alProfile.add(p);
                    }
                } catch(JSONException e){

                }
                //aaProfile.notifyDataSetChanged();
            }
        });

        AsyncHttpClient client1 = new AsyncHttpClient();
        client1.get("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/getStallInfo.php", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //called when response HTTP status is "200 OK"
                try {
                    for(int i = 0; i<response.length(); i++){
                        JSONObject stall = (JSONObject)response.get(i);
                        String s = stall.getString("name");
                        tvStallName.setText(s.toString());
                        //alProfile.add(p);
                    }
                } catch(JSONException e){

                }
                //aaProfile.notifyDataSetChanged();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}