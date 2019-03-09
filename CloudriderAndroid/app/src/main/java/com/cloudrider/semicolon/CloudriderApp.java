package com.cloudrider.semicolon;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.cloudrider.semicolon.dto.ChannelDTO;
import com.cloudrider.semicolon.dto.PeerDTO;
import com.cloudrider.semicolon.utils.AppSSLSocketFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class CloudriderApp extends Application {

    static CloudriderApp instance;
    private RequestQueue mRequestQueue;

    ArrayList peersList;
    ArrayList channelList;

    SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initData();
        prefs = getSharedPreferences("CloudPref", Context.MODE_PRIVATE);
    }

    public static CloudriderApp getInstance() {
        return instance;
    }

    /**
     * Get Volley Request Queue.
     *
     * @return RequestQueue
     */
    @NonNull
    public RequestQueue getVolleyRequestQueue() {
        if (mRequestQueue == null) {
            HurlStack hurlStack = new HurlStack() {
                @Override
                protected HttpURLConnection createConnection(URL url) throws IOException {
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
                    try {
                        httpsURLConnection.setSSLSocketFactory(new AppSSLSocketFactory(httpsURLConnection.getSSLSocketFactory()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return httpsURLConnection;
                }
            };
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), hurlStack);
        }

        return mRequestQueue;
    }

    public void initData() {
        peersList = new ArrayList();

        peersList.add(new PeerDTO("Peer 1", "Peer 1 Sub title", 10, 12));
        peersList.add(new PeerDTO("Peer 2", "Peer 2 Sub title", 12, 14));


        channelList = new ArrayList();
        channelList.add(new ChannelDTO("Channel 1", "Channe 1 Sub title"));
        channelList.add(new ChannelDTO("Channel 2", "Channe 2 Sub title"));
    }

    public ArrayList getPeersList() {
        return peersList;
    }

    public void setPeersList(ArrayList peersList) {
        this.peersList = peersList;
    }

    public ArrayList getChannelList() {
        return channelList;
    }

    public void setChannelList(ArrayList channelList) {
        this.channelList = channelList;
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }
}
