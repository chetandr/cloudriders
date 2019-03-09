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
import com.cloudrider.semicolon.utils.GsonRequest;
import com.cloudrider.semicolon.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

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
//        Gson gson;
//        gson = new GsonBuilder().serializeNulls()
//                .create();
//            Response.Listener<JsonObject> loginResponseListener = new Response.Listener<JsonObject>() {
//                @Override
//                public void onResponse(JsonObject response) {
//                    //closeProgressDialog();
//                    Log.e("HEMANT", response.toString());
//                    startActivity(new Intent(SplashActivity.this, OrgSelectActivity.class));
//                    finish();
//                    if (response == null) {
//                        return;
//                    }
//                    try {
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            };
//
//
//            Response.ErrorListener errorListener = new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                   //closeProgressDialog();
//                    Log.e("SplashActivity", error.toString());
//                }
//            };
//
//            GsonRequest<JsonObject> gsonRequest = new GsonRequest<>(
//                    Request.Method.GET,
//                    "http://10.44.14.143:3000/hyperverse/organization",
//                    new TypeToken<JsonObject>() {
//                    }.getType(),
//                    gson,
//                    loginResponseListener,
//                    errorListener
//            );
//            Map<String, String> headers = Utils.requestHeader(false);
//
//            gsonRequest.setHeaderParams(headers);
//
//            //gsonRequest.setBodyParams(getBodyParams());
//            gsonRequest.setShouldCache(false);
//            gsonRequest.setTag("Login");
//            gsonRequest.setRequestRetryParams();
//            //gsonRequest.setRetryPolicy(new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            //displayProgressDialog();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.44.14.143:3000/hyperverse/organization",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.e("HEMANT", response.toString());
                        startActivity(new Intent(SplashActivity.this, OrgSelectActivity.class));
                        finish();
                        if (response == null) {
                            return;
                        }
                        try {

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
}
