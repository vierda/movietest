package com.hooq.demo.movie.common.network;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hooq.demo.movie.common.util.Util;


import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class JsonRequest<T> extends Request<T> {

    private static final String PROTOCOL_CHARSET = "utf-8";
    private JsonObject jsonResponse;
    private int responseCode;
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private Response.Listener<T> mListener;
    private JsonResponseListener<T> mJsonResponseListener;
    private final String mRequestBody;

    private final Class<T> mType;
    private String mContentType;
    private String mJSessionId;
    private Map<String, String> requestHeaders;

    private NetworkResponse nwResponse;

    private Gson gson = Util.getInstance().getGson();


    public JsonRequest(String url, String requestBody, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.DEPRECATED_GET_OR_POST, url, requestBody, listener, errorListener, null, null);
    }


    public JsonRequest(int method, String url, String requestBody, Response.Listener<T> listener,
                       Response.ErrorListener errorListener, String pJSessionId, Class<T> type) {
        super(method, url, errorListener);

        mListener = listener;
        mRequestBody = requestBody;
        mJSessionId = pJSessionId;
        mType = type;
    }


    public JsonRequest(int method, String url, String requestBody, JsonResponseListener<T> listener,
                       Response.ErrorListener errorListener, String pJSessionId, Class<T> type) {
        super(method, url, errorListener);

        mJsonResponseListener = listener;
        mRequestBody = requestBody;
        mJSessionId = pJSessionId;
        mType = type;
    }

    /**
     * Deliver received response to specified listener.
     *
     * @param response
     *         T response object.
     */
    @Override
    protected void deliverResponse(T response) {
        do {
            if (mListener != null) {
                mListener.onResponse(response);
                break;
            }

            if (mJsonResponseListener != null) {
                mJsonResponseListener.onResponse(response, jsonResponse, responseCode,
                        nwResponse != null ? nwResponse.headers : null);
                break;
            }
        } while (false);
    }


    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            nwResponse = response;

            String resultString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            Log.d("RESULT STRING: ", resultString);

            responseCode = response.statusCode;

            jsonResponse = new JsonObject();

            JsonParser jsonParser = new JsonParser();
            JsonElement element = jsonParser.parse(new StringReader(resultString));
            if (element == null)
                element = jsonParser.parse(resultString);

            if (element != null)
                jsonResponse.add("response", element);

            T result = gson.fromJson(resultString, mType);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Throwable e) {
            e.printStackTrace();

            return Response.error(new ParseError(response));
        }
    }


    @Override
    public String getBodyContentType() {
        return TextUtils.isEmpty(mContentType) ? PROTOCOL_CONTENT_TYPE : mContentType;
    }


    public void setBodyContentType(String pContentType) {
        mContentType = pContentType;
    }

    @Override
    public byte[] getBody() {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();

        do {

            headers.putAll(super.getHeaders());

            if (requestHeaders != null && !requestHeaders.isEmpty()) {
                headers.putAll(requestHeaders);
                break;
            }

            if (TextUtils.isEmpty(mJSessionId)) break;

            //headers.put("Cookie", Constants.JSESSIONID + "=" + mJSessionId);
        } while (false);

        return headers;
    }

    /**
     * Sets map header.
     *
     * @param headers
     *         the map header
     */
    public void setRequestHeaders(Map<String, String> headers) {
        requestHeaders = headers;
    }
}