package com.myapplicationdev.android.a3sticks_hawker;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<Profile> alProfile = new ArrayList<Profile>();
        ArrayAdapter<Profile> aaProfile = null;
        TextView tvStallName;
        TextView tvOwnerName;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        toolbar = view.findViewById(R.id.top_toolbar);
        TextView tb = view.findViewById(R.id.toolbar_title1);
        tb.setText("Profile");


        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/c302_sakila/getProfile.php", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //called when response HTTP status is "200 OK"
                try {
                    for(int i = 0; i<response.length(); i++){
                        JSONObject profile = (JSONObject)response.get(i);
                        Profile p = new Profile(profile.getInt("owner_id"), profile.getString("name"));
                        alProfile.add(p);
                    }
                } catch(JSONException e){

                }
                aaProfile.notifyDataSetChanged();
            }
        });

        return view;
    }
}