package com.cloudrider.semicolon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.cloudrider.semicolon.dto.RegisterDTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    TextInputEditText etOrgName;
    TextInputEditText etPeers;
    TextInputEditText etDomain;
    TextInputEditText etChannel;
    TextInputEditText etOrderer;
    TextInputEditText etOrdererHost;
    TextView txtCons;
    String cons = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        cons = getIntent().getStringExtra("cons");
        initUI();
    }

    public void initUI() {
        txtCons = findViewById(R.id.tvHeaderCons);
        txtCons.setText(cons);
        view = findViewById(R.id.view);
        etOrgName = findViewById(R.id.etorgname);
        etPeers = findViewById(R.id.etPeers);
        etDomain = findViewById(R.id.etDomain);
        etChannel = findViewById(R.id.etChannel);
        etOrderer = findViewById(R.id.etOrderer);
        etOrdererHost = findViewById(R.id.etOrdererHost);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnAdvanced) {
            if(view.getVisibility()==View.GONE)
                view.setVisibility(View.VISIBLE);
            else
                view.setVisibility(View.GONE);
        }
        else if(v.getId() == R.id.btnRegister) {

            RegisterDTO dto = fetchData();
            if(!TextUtils.isEmpty(dto.getOrgName()) && !TextUtils.isEmpty(dto.getPeers())) {
                createOrg(dto);
                CloudriderApp.getInstance().saveRegisterDTO(dto);
                CloudriderApp.getInstance().getPrefs().edit().putBoolean("isRegistered", true).commit();
                //Toast.makeText(this, "Your organization creation is in progress. Please check again some time later.", Toast.LENGTH_LONG).show();
                //finish();
            }
            else {
                Toast.makeText(this, "Please enter valid data", Toast.LENGTH_LONG).show();
            }
        }
    }

    public RegisterDTO fetchData() {
        RegisterDTO dto = new RegisterDTO();
        dto.setCertType("System generated");
        dto.setChannel(etChannel.getText().toString());
        dto.setDomain(etDomain.getText().toString());
        dto.setOrderer(etOrderer.getText().toString());
        dto.setOrdererHost(etOrdererHost.getText().toString());
        dto.setOrgName(etOrgName.getText().toString());
        dto.setPeers(etPeers.getText().toString());
        return dto;
    }

    ProgressDialog pd;
    public void showProgress() {
        pd = new ProgressDialog(this);
        pd.setMessage("Oranization creation in progress. Please wait...");
        pd.show();
    }

    public void hideProgress() {
        if( pd != null && pd.isShowing()) {
            try {
                pd.dismiss();
            }
            catch (Exception e) {

            }
        }
    }
    public void createOrg(RegisterDTO dto) {
        try {
            showProgress();
            String URL = "http://10.44.14.143:3000/hyperverse/organization";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("orgName", dto.getOrgName());
            jsonBody.put("peers", dto.getPeers());
            jsonBody.put("domain", dto.getDomain());
            jsonBody.put("channel", dto.getChannel());
            jsonBody.put("orderer", dto.getOrderer());
            jsonBody.put("ordererHost", dto.getOrdererHost());
            jsonBody.put("certType", dto.getCertType());


            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    hideProgress();
                    postCreate();
                    Log.i("VOLLEY onResponse", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideProgress();
                    Log.e("VOLLEY onErrorResponse", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };

            CloudriderApp.getInstance().getVolleyRequestQueue().add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void postCreate() {
        Toast.makeText(this, "Your organization creation is in progress. Please check again some time later.", Toast.LENGTH_LONG).show();
        finish();
    }
}
