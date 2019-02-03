package com.hooq.demo.movie.common.network;


import com.google.gson.JsonObject;

import java.util.Map;

public interface JsonResponseListener<T> {

    void onResponse(T response, JsonObject pJsonObject, int responseCode, Map<String, String> responseHeaders);

}
