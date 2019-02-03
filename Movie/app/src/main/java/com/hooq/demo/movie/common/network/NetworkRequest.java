package com.hooq.demo.movie.common.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.hooq.demo.movie.common.data.entity.MovieResults;
import com.hooq.demo.movie.common.util.Util;


import java.util.List;
import java.util.Map;

public class NetworkRequest {

    private static String TAG = NetworkRequest.class.getCanonicalName();
    private ResourceManager resourceManager = ResourceManager.getInstance();

    public NetworkRequest(Context context) {
        VolleyHelper.init(context);
    }


    public void getMovieResults(int page,final ResponseHandler<MovieResults> rListener) {
        String url = resourceManager.getMovieResults(page+1);
        JsonResponseListener<JsonObject> listener = new JsonResponseListener<JsonObject>() {
            @Override
            public void onResponse(JsonObject response, JsonObject pJsonObject, int responseCode, Map<String, String> responseHeaders) {
                MovieResults movies = Util.getInstance().getGson().fromJson(response,MovieResults.class);
                sendMessage(movies, responseCode, rListener);
            }
        };

        JsonRequest<JsonObject> jsonRequest = new JsonRequest<>(Request.Method.GET, url, null, listener,
                sendError(rListener), null, JsonObject.class);

        VolleyHelper.exec(jsonRequest);
    }

    public void getSimilarMovie(int movieId,int page,final ResponseHandler<MovieResults> rListener) {
        String url = resourceManager.getSimilarMovies(movieId,page+1);
        JsonResponseListener<JsonObject> listener = new JsonResponseListener<JsonObject>() {
            @Override
            public void onResponse(JsonObject response, JsonObject pJsonObject, int responseCode, Map<String, String> responseHeaders) {
                MovieResults movies = Util.getInstance().getGson().fromJson(response,MovieResults.class);
                sendMessage(movies, responseCode, rListener);
            }
        };

        JsonRequest<JsonObject> jsonRequest = new JsonRequest<>(Request.Method.GET, url, null, listener,
                sendError(rListener), null, JsonObject.class);

        VolleyHelper.exec(jsonRequest);
    }

    private <T> void sendMessage(T object, int responseCode, ResponseHandler<T>
            listener) {

        Response response = new Response();
        response.setData(object);
        response.setResponseCode(responseCode);
        listener.onReceive(response);
    }

    private com.android.volley.Response.ErrorListener sendError(final ResponseHandler listener) {
        return new com.android.volley.Response.ErrorListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener == null) {
                    Log.e(TAG, "Unknown error - Application callback listener is null!");
                } else {

                    Response response = new Response();
                    if (error != null && error.networkResponse != null) {
                        response.setResponseCode(error.networkResponse.statusCode);
                        response.setErrorDescription(error.getMessage());
                    }

                    listener.onReceive(response);
                }
            }
        };
    }
}
