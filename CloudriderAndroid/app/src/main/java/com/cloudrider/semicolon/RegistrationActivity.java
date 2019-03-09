package com.cloudrider.semicolon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudrider.semicolon.dto.RegisterDTO;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    TextInputEditText etOrgName;
    TextInputEditText etPeers;
    TextInputEditText etDomain;
    TextInputEditText etChannel;
    TextInputEditText etOrderer;
    TextInputEditText etOrdererHost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initUI();
    }

    public void initUI() {
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
                CloudriderApp.getInstance().saveRegisterDTO(dto);
                CloudriderApp.getInstance().getPrefs().edit().putBoolean("isRegistered", true).commit();
                startActivity(new Intent(RegistrationActivity.this, OrgSelectActivity.class));
                finish();
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
}
