package com.myapplicationdev.android.a3sticks_hawker;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ChangePassword extends AppCompatActivity {
    TextView tvCurrPass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        tvCurrPass1 = findViewById(R.id.tvCurrPass1);

        RequestParams params = new RequestParams();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/getProfile.php", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                //called when response HTTP status is "200 OK"
                try {
                    for(int i = 0; i<response.length(); i++){
                        JSONObject profile = (JSONObject)response.get(i);
                        String p = profile.getString("password");
                        tvCurrPass1.setText(p.toString());
                        //alProfile.add(p);
                    }
                } catch(JSONException e){

                }
                //aaProfile.notifyDataSetChanged();
            }
        });
    }


}
