package com.cloudrider.semicolon.utils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;

import java.net.HttpURLConnection;

/**
 * This class is implementing Token retry policy for all GsonRequest volley calls in app
 * Retry policy parameters are received via GsonRequest object which has default
 * as that of volley DefaultRetryPolicy.
 * However, for some volley calls which need infinite wait till response is received and dont want
 * volley's default retry mechanism in which 2nd request is sent after 2500 ms,
 * setter is provided in GsonRequest to set retry policy.
 */

class TokenRetryPolicy implements RetryPolicy {

    private int mCurrentRetryCount;
    private int mCurrentTimeoutMs;
    @SuppressWarnings("CanBeFinal")
    private float mBackoffMultiplier;
    @SuppressWarnings("CanBeFinal")
    private GsonRequest mGsonRequest;
    private int mMaxNumRetries;
    private final String strTAG = this.getClass().getSimpleName();


    TokenRetryPolicy(GsonRequest gsonRequest) {
        mMaxNumRetries = 1;
        mCurrentTimeoutMs = 0;
        mGsonRequest = gsonRequest;
        this.mCurrentTimeoutMs = mGsonRequest.mCurrentTimeoutMs;
        this.mMaxNumRetries = mGsonRequest.mMaxNumRetries;
        this.mBackoffMultiplier = mGsonRequest.mBackoffMultiplier;
    }

    @Override
    public int getCurrentTimeout() {
        return mGsonRequest.mCurrentTimeoutMs;
    }

    @Override
    public int getCurrentRetryCount() {
        if (this.mCurrentTimeoutMs == 0) {//case where we want to wait for response to arrive and do not want volley's standard retry mechanism to kick in.
            return 0;
        }
        return this.mCurrentRetryCount;
    }

    @Override
    public void retry(VolleyError error) throws VolleyError {
        mCurrentRetryCount++; //increment our retry counts
        mMaxNumRetries = 1;
        mCurrentTimeoutMs += (mCurrentTimeoutMs * mBackoffMultiplier);
        if (error != null && error.networkResponse != null
                && error.networkResponse.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED && mCurrentRetryCount <= mMaxNumRetries) {
            mCurrentRetryCount = mMaxNumRetries + 1; // Don't retry anymore, it's pointless
            //new RefreshAccessToken().refreshToken(this); // Get new token
        }
        if (!this.hasAttemptRemaining()) {
            Log.v(strTAG, "No attempt remaining, ERROR");
            if (error != null) {
                throw error;
            } else {
                throw new VolleyError();
            }
        }
    }

    private boolean hasAttemptRemaining() {
        return this.mCurrentRetryCount <= this.mMaxNumRetries;
    }

    /**
     * This will be called with either token or failure passed from RefreshAccessToken
     *
     * @param token token
     */

    public void onRefreshTokenUpdate(String token) {

        try {
            if (token.equalsIgnoreCase("Failure")) {
                Log.e(strTAG, "Failure in getting access token/refresh token/login");

                if (mGsonRequest != null) {
                    mGsonRequest.deliverError( new VolleyError("Failure in getting access token/refresh token/login"));
                }
                throw new VolleyError("Failure in getting access token/refresh token/login");

            } else {
                //update the latest token and resend a request
                if (mGsonRequest != null && mGsonRequest.getHeaders() != null) {
                    //noinspection unchecked
                    mGsonRequest.getHeaders().put("Authorization", "OAuth " + token);
                    //App.getInstance().getVolleyRequestQueue().add(mGsonRequest);
                    Log.v(strTAG, "Firing off request for which auth failed");
                }
            }

        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
