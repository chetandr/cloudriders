package com.cloudrider.semicolon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinnerOrganization;
    private String organization;
    private Button btnAdvanced;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        spinnerOrganization = findViewById(R.id.spinnerOrganization);
        view = findViewById(R.id.view);
        btnAdvanced = findViewById(R.id.btnAdvanced);
        setOrganizationData();


    }

    private void setOrganizationData(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.organizations, R.layout.spinner_item);
        spinnerOrganization.setAdapter(adapter);
        spinnerOrganization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
           CloudriderApp.getInstance().getPrefs().edit().putBoolean("isRegistered", true).commit();
           startActivity(new Intent(RegistrationActivity.this, HomeActivity.class));
           finish();
        }
    }
}
