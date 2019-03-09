package com.cloudrider.semicolon;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                    if(CloudriderApp.getInstance().getPrefs().getBoolean("isRegistered", false)) {
                        startActivity(new Intent(SplashActivity.this, OrgSelectActivity.class));
                    }
                    else {
                        startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
                    }
                    finish();
            }
        }, 4000);
    }
}
