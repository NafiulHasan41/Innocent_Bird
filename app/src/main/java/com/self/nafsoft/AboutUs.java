package com.self.nafsoft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AboutUs extends AppCompatActivity {


    private TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        name = findViewById(R.id.aboutUserName1);
        email = findViewById(R.id.aboutUserEmail1);
        getUser();
    }

    public void getUser(){
        RequestQueue req = Volley.newRequestQueue(this);
        String url = "https://api.myjson.online/v1/records/2e1e1a1f-3026-43a7-a490-d9ce64726f3f" ;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJson(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AboutUs.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
        req.add(request);
    }

    private void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            String userName,userEmail;

                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                userName = jsonObject1.getString("name");
                userEmail = jsonObject1.getString("email");
                name.setText(userName);
                email.setText(userEmail);




        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }


}