package com.cloudrider.semicolon;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    RecyclerView peerRecyclerView;
    RecyclerView channelRecyclerView;
    private RecyclerView.Adapter peersAdapter;
    private RecyclerView.LayoutManager peersLayoutManager;

    private RecyclerView.Adapter channelsAdapter;
    private RecyclerView.LayoutManager channelsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.nav_notifications:
                                startActivity(new Intent(HomeActivity.this, NotificationsActivity.class));
                                break;
                            case R.id.nav_transactions:
                                startActivity(new Intent(HomeActivity.this, TransactionListActivity.class));
                                break;
                        }
                        return true;
                    }
                });

        peerRecyclerView = findViewById(R.id.peers_recycler_view);
        channelRecyclerView = findViewById(R.id.channels_recycler_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        peerRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        peersLayoutManager = new LinearLayoutManager(this);
        peerRecyclerView.setLayoutManager(peersLayoutManager);

        channelRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        channelsLayoutManager = new LinearLayoutManager(this);
        channelRecyclerView.setLayoutManager(channelsLayoutManager);

        peersAdapter = new PeersAdapter(new ArrayList());
        peerRecyclerView.setAdapter(peersAdapter);

        channelsAdapter = new ChannelAdapter(new ArrayList());
        channelRecyclerView.setAdapter(channelsAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
