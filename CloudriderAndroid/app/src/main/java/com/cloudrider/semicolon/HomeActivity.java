package com.cloudrider.semicolon;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cloudrider.semicolon.parse.ChDatum;
import com.cloudrider.semicolon.parse.Channels;
import com.cloudrider.semicolon.parse.Consortium;
import com.cloudrider.semicolon.parse.Org;
import com.cloudrider.semicolon.parse.Peer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnDeployCodeChainInterface{

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
    List<ChDatum> channels = new ArrayList<>();

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
                                startActivity(new Intent(HomeActivity.this, OrgSelectActivity.class));
                                finish();
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



//        initPeerData();
//        initChannelData();

        fetchData();


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
        peersAdapter = new PeersAdapter(HomeActivity.this, peers,this);
        peerRecyclerView.setAdapter(peersAdapter);
    }

    public void initChannelData() {
        channelsAdapter = new ChannelAdapter(channels);
        channelRecyclerView.setAdapter(channelsAdapter);
    }

    public void fetchData() {

        showProgress();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.44.14.143:3000/hyperverse/organization/"+selectedOrg,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            Gson gson = new Gson();
                            Org cons = gson.fromJson(response, Org.class);
                            peers = cons.getPeers();
                            initPeerData();
                            fetchChannels();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HEMANT", error.toString());
            }
        });

        CloudriderApp.getInstance().getVolleyRequestQueue().add(stringRequest);
    }


    public void fetchChannels() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://10.44.14.143:3000/hyperverse/allChannels/" + selectedOrg,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            Gson gson = new Gson();
                            Channels cons = gson.fromJson(response, Channels.class);
                            channels = cons.getData();
                            initChannelData();
                            hideProgress();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgress();
                Log.e("HomeActivity", error.toString());
            }
        });

        CloudriderApp.getInstance().getVolleyRequestQueue().add(stringRequest);
    }

    ProgressDialog pd;
    public void showProgress() {
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();
    }

    public void hideProgress() {
        pd.dismiss();
    }

    @Override
    public void onSubscribe(String peer) {
        Intent showDeployFormIntent = new Intent(this,DeployCodeChainActivity.class);
        showDeployFormIntent.putExtra("peer",peer);
        startActivity(showDeployFormIntent);
    }
}
