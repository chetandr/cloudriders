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
import com.cloudrider.semicolon.dto.RegisterDTO;
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

        peersList.add(new PeerDTO("Peer 1", "peer1.org1.example.com", 10, 12));
        peersList.add(new PeerDTO("Peer 2", "peer2.org1.example.com", 12, 14));


        channelList = new ArrayList();
        channelList.add(new ChannelDTO("My Channel 1", "My Channel 1 Sub title"));
        channelList.add(new ChannelDTO("My Channel 2", "My Channel 2 Sub title"));
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

    public void saveRegisterDTO(RegisterDTO dto){

        SharedPreferences.Editor edt = prefs.edit();
        edt.putString("orgName", dto.getOrgName());
        edt.putString("peers", dto.getPeers());
        edt.putString("domain", dto.getDomain());
        edt.putString("channel", dto.getChannel());
        edt.putString("orderer", dto.getOrderer());
        edt.putString("ordererHost", dto.getOrdererHost());
        edt.putString("certType", dto.getCertType());
        edt.commit();
    }

    public RegisterDTO getRegisterDTO() {
        RegisterDTO dto = new RegisterDTO();
        dto.setPeers(prefs.getString("peers", "0"));
        dto.setOrgName(prefs.getString("orgName", ""));
        dto.setDomain(prefs.getString("domain", ""));
        dto.setChannel(prefs.getString("channel", ""));
        dto.setOrderer(prefs.getString("orderer", ""));
        dto.setOrdererHost(prefs.getString("ordererHost", ""));
        dto.setCertType(prefs.getString("certType", "System generated"));
        return dto;
    }
}
