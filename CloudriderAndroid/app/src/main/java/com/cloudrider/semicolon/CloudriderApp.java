package com.cloudrider.semicolon;

import android.app.Application;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.cloudrider.semicolon.utils.AppSSLSocketFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CloudriderApp extends Application {

    CloudriderApp instance;
    private RequestQueue mRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public CloudriderApp getInstance() {
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
}
