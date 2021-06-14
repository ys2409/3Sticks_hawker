package com.myapplicationdev.android.a3sticks_hawker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.*;


public class RegisterActivity extends AppCompatActivity {

    ArrayList<Profile> alProfile = new ArrayList<Profile>();
    ArrayAdapter<Profile> aaProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
    }


}