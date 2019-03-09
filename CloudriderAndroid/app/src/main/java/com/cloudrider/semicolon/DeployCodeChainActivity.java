package com.cloudrider.semicolon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cloudrider.semicolon.parse.Consortium;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class DeployCodeChainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etCode;
    private Button btnDeployCode;
    String peer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deploy_code_chain);
        peer = getIntent().getStringExtra("peer");
        etCode = findViewById(R.id.etCode);
        btnDeployCode = findViewById(R.id.btnDeployCode);

    }

    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.btnDeployCode){
           deployCodeChainRequest(peer);
       }
    }

    private void deployCodeChainRequest(String peer){
        JSONObject reqObject = new JSONObject();
        try {
            reqObject.put("peer", peer);
            reqObject.put("code",etCode.getText().toString());
        }catch (JSONException e){

        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://10.44.14.143:3000/hyperverse/deployChaincode",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(DeployCodeChainActivity.this,"Your Code Chain is Deployed",Toast.LENGTH_LONG).show();
                        finish();
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
