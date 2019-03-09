package com.cloudrider.semicolon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudrider.semicolon.parse.Consortium;

import java.util.ArrayList;

public class OrgSelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerOrganization;
    private Spinner spinnerCons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_select);
        spinnerOrganization = findViewById(R.id.spinnerOrganization);
        spinnerCons = findViewById(R.id.spinnerCons);
        //setOrganizationData();
        setConsData();
    }

    private void setOrganizationData(String selected){

        ArrayList<String> orgs = new ArrayList<>();

        for(int i = 0; i < CloudriderApp.getInstance().getConsortium().getData().size(); i++) {
            if(CloudriderApp.getInstance().getConsortium().getData().get(i).getConsortiumname().equalsIgnoreCase(selected)) {
                for( int j = 0; j < CloudriderApp.getInstance().getConsortium().getData().get(i).getOrgs().size(); j++) {
                    orgs.add(CloudriderApp.getInstance().getConsortium().getData().get(i).getOrgs().get(j).getOrgname());
                }
                break;
            }
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, R.layout.spinner_item, orgs);

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

    private void setConsData(){
        ArrayList<String> strs = new ArrayList<>();
        for(int i = 0; i < CloudriderApp.getInstance().getConsortium().getData().size(); i++) {
            strs.add(CloudriderApp.getInstance().getConsortium().getData().get(i).getConsortiumname());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, strs);
        spinnerCons.setAdapter(adapter);
        spinnerCons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setOrganizationData(spinnerCons.getSelectedItem().toString());
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
                CloudriderApp.getInstance().getPrefs().edit().putString("selectedCons", spinnerCons.getSelectedItem().toString()).commit();
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
            else {
                Toast.makeText(this, "Please select organization", Toast.LENGTH_LONG).show();
            }
        }
    }
}
