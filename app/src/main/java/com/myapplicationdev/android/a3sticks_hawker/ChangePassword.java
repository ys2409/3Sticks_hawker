package com.myapplicationdev.android.a3sticks_hawker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private AsyncHttpClient client;
    EditText etPassword;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        client = new AsyncHttpClient();

        etPassword = findViewById(R.id.etPassword);
        btnConfirm = findViewById(R.id.button);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ChangePassword.this);
        String ownerID = prefs.getString("ownerID", "");

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.add("ownerID", ownerID);
                params.add("newPass", etPassword.getText().toString());
                Log.d("TAG", params.toString());

                client.post("http://10.0.2.2/3Sticks_hawker/3Sticks_hawker/editPassword.php", params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //called when response HTTP status is "200 OK"
                        try {
                            Toast.makeText(getApplicationContext(), "Password successfully updated", Toast.LENGTH_SHORT).show();
                            finish();
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                        //aaProfile.notifyDataSetChanged();
                    }

                });
                Log.d("TAG", "onCreate: ");
            }
        });
    }


}
