package com.cloudrider.semicolon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class OrgSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerOrganization;
    private Spinner spinnerCons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_select);
        spinnerOrganization = findViewById(R.id.spinnerOrganization);
        spinnerCons = findViewById(R.id.spinnerCons);
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
            if(spinnerOrganization.getSelectedItemPosition() != -1 ) {
                CloudriderApp.getInstance().getPrefs().edit().putString("selectedOrg", spinnerOrganization.getSelectedItem().toString()).commit();
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
            else {
                Toast.makeText(this, "Please select organization", Toast.LENGTH_LONG).show();
            }
        }
    }
}
