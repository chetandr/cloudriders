package com.cloudrider.semicolon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cloudrider.semicolon.parse.Channels;
import com.cloudrider.semicolon.parse.Consortium;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SplashActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                   /* if(CloudriderApp.getInstance().getPrefs().getBoolean("isRegistered", false)) {
                        startActivity(new Intent(SplashActivity.this, OrgSelectActivity.class));
                    }
                    else {
                        startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
                    }*/
                   /*startActivity(new Intent(SplashActivity.this, OrgSelectActivity.class));
                    finish();*/
                   fetchData();
            }
        }, 2000);
    }

    public void fetchData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.44.14.143:3000/hyperverse/consortium",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            Gson gson = new Gson();
                            Consortium cons = gson.fromJson(response, Consortium.class);
                            CloudriderApp.getInstance().setConsortium(cons);
                            startActivity(new Intent(SplashActivity.this, OrgSelectActivity.class));
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HEMANT", error.toString());
            }
        });

        CloudriderApp.getInstance().getVolleyRequestQueue().add(stringRequest);
    }

    /*public void fetchChannels() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.44.14.143:3000/hyperverse/consortium",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            Gson gson = new Gson();
                            Channels cons = gson.fromJson(response, Channels.class);
                            CloudriderApp.getInstance().setChannels(cons);
                            startActivity(new Intent(SplashActivity.this, OrgSelectActivity.class));
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HEMANT", error.toString());
            }
        });

        CloudriderApp.getInstance().getVolleyRequestQueue().add(stringRequest);
    }*/
}
