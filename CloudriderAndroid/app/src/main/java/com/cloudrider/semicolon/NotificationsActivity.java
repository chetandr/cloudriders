package com.cloudrider.semicolon;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView rvNotificationsList;
    private NotificationsAdapter notificationsAdapter;
    private ArrayList<NotificationDTO> notificationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);


        initUI();
        initData();
    }
    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setTitle(getString(R.string.title_activity_notifications_list));
        rvNotificationsList = findViewById(R.id.rvNotification);
    }

    private void initData(){
        notificationList = new ArrayList<>();
        notificationsAdapter = new NotificationsAdapter(this,notificationList);
        rvNotificationsList.setAdapter(notificationsAdapter);
    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return super.onNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
