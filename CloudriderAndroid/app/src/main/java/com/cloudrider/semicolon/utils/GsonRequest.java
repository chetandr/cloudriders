
package com.cloudrider.semicolon.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * This class is responsible for to creating network call.
 *
 * @param <T> Generic param
 */
@SuppressWarnings("RedundantThrows")
public class GsonRequest<T> extends JsonRequest<T> {
    /**
     * Debug log tag.
     */
    private final String strTAG = GsonRequest.class.getSimpleName();

    /**
     * For Json parsing.
     */
    private final Gson gson;

    /**
     * Class type.
     */
    private final Type type;

    /**
     * Success response listener.
     */
    private final Response.Listener<T> listener;

    /**
     * Error response listener.
     */
    private final Response.ErrorListener errorListener;

    /**
     * Request header params.
     */
    private Map<String, String> headerParams;

    /**
     * Request body params.
     */
    private Object bodyParams;

    /**
     * Request retry timeout
     */
    public int mCurrentTimeoutMs = 15000;// 15 sec wait time and 1 retry i.e. 30 sec instead of DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
    public int mMaxNumRetries = DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
    public float mBackoffMultiplier = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;

    /**
     * GsonRequest parameterize Constructor to initialize the class member variables.
     *
     * @param requestMethod Which kind of HTTP request method
     * @param url           Webservice Url
     * @param type          Model class type
     * @param gson          Json parsing element
     * @param listener      Success response listener
     * @param errorListener Error response listener
     */
    public GsonRequest(
            final int requestMethod,
            @NonNull final String url,
            @NonNull final Type type,
            @NonNull final Gson gson,
            @NonNull final Response.Listener<T> listener,
            @NonNull final Response.ErrorListener errorListener
    ) {
        super(requestMethod, url, null, listener, errorListener);
        Log.v(strTAG, "url is " + url);// + "  gson is " + gson.toString());

        this.gson = gson;
        this.type = type;
        this.listener = listener;
        this.errorListener = errorListener;
        setRetryPolicy(new TokenRetryPolicy(this));
    }

    /**
     * setting the Request retry params.
     */
    public void setRequestRetryParams() {
        this.mCurrentTimeoutMs = 0;
        this.mMaxNumRetries = 1;
        this.mBackoffMultiplier = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
    }

    /**
     * setting the Request Header params.
     *
     * @param headerParams Request Header params
     */
    public void setHeaderParams(Map<String, String> headerParams) {
        this.headerParams = headerParams;
    }

    /**
     * setting the Request Body params.
     *
     * @param bodyParams Request Body params
     */
    public void setBodyParams(Object bodyParams) {
        this.bodyParams = bodyParams;
    }


    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {


        try {
            if (error != null && error.networkResponse != null
                    && error.networkResponse.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {

                Log.e(strTAG, "Refreshing the Expired Token Started.");
                //Commented previous refreshtoken mechanism.
                //We will using new TokenRetryPolicy through which we will handle auth token expiry/error handling
                //RefreshAccessToken refreshAccessToken = new RefreshAccessToken(refreshTokenListener);
                //refreshAccessToken.refreshToken();
            } else {
                errorListener.onErrorResponse(error);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorListener.onErrorResponse(new ParseError(e));
        }
    }

    @Override
    protected String getParamsEncoding() {
        return "UTF-8";
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headerParams;
    }

    @Override
    public byte[] getBody() {
        try {
            Log.w(strTAG, "Body params are " + this.bodyParams.toString());
            return this.bodyParams.toString().getBytes(getParamsEncoding());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + e);
        } catch (Exception e) {
            throw new RuntimeException("Exception is: " + e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.w(strTAG, "parseNetworkResponse JSON Response is " + json);
            Class c = getClassType();
            if (c == null) {
                throw new NullPointerException("Type class instance is null");
            }
            return (Response<T>) Response.success
                    (
                            (c.newInstance() instanceof JSONObject)
                                    ? new JSONObject(json) : gson.fromJson(json, type),
                            HttpHeaderParser.parseCacheHeaders(response)
                    );
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (InstantiationException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (NullPointerException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
            // 24/04/17 - Added a catch block for handling OOM errors.
        } catch (OutOfMemoryError e) {
            Log.e(strTAG, "Out of allocatable memory!");
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    /**
     * Extracting class type from Type class.
     *
     * @return Class
     */
    private Class getClassType() {
        try {
            return Class.forName(type.toString().split(" ")[1]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
