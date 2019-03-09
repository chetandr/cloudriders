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
import android.widget.TextView;

import com.cloudrider.semicolon.parse.Peer;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    RecyclerView peerRecyclerView;
    RecyclerView channelRecyclerView;
    private RecyclerView.Adapter peersAdapter;
    private RecyclerView.LayoutManager peersLayoutManager;

    private RecyclerView.Adapter channelsAdapter;
    private RecyclerView.LayoutManager channelsLayoutManager;
    TextView txtTitle;

    String selectedOrg = "";
    String selectedCons = "";

    List<Peer> peers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedOrg = CloudriderApp.getInstance().getPrefs().getString("selectedOrg", "");
        selectedCons = CloudriderApp.getInstance().getPrefs().getString("selectedCons", "");
        initUI();
    }

    private void initUI() {
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(selectedOrg);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        //menuItem.setChecked(true);
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
        //actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        actionbar.setDisplayHomeAsUpEnabled(true);

        peerRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        peersLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        peerRecyclerView.setLayoutManager(peersLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.size_6dip);
        peerRecyclerView.addItemDecoration(itemDecoration);

        channelRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        channelsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        channelRecyclerView.setLayoutManager(channelsLayoutManager);
        channelRecyclerView.addItemDecoration(itemDecoration);



        initPeerData();

        channelsAdapter = new ChannelAdapter(CloudriderApp.getInstance().getChannelList());
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

    public void initPeerData() {

        for(int i = 0; i < CloudriderApp.getInstance().getConsortium().getData().size(); i++) {
            if(CloudriderApp.getInstance().getConsortium().getData().get(i).getConsortiumname().equalsIgnoreCase(selectedCons)) {
                for( int j = 0; j < CloudriderApp.getInstance().getConsortium().getData().get(i).getOrgs().size(); j++) {
                    if(CloudriderApp.getInstance().getConsortium().getData().get(i).getOrgs().get(j).getOrgname().equalsIgnoreCase(selectedOrg)) {
                        peers = CloudriderApp.getInstance().getConsortium().getData().get(i).getOrgs().get(j).getPeers();
                        break;
                    }
                }
                break;
            }
        }

        peersAdapter = new PeersAdapter(this, peers);
        peerRecyclerView.setAdapter(peersAdapter);
    }
}
