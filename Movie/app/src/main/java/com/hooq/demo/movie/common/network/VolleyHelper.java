package com.hooq.demo.movie.common.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

public class VolleyHelper {

    private static RequestQueue sRequestQueue;
    private static final int REQUEST_TIMEOUT = 60;


    public static void init(final Context context) {
        sRequestQueue = Volley.newRequestQueue(context, null);
    }


    public static void exec(final Request<?> request) {
        request.setRetryPolicy(createCustomRetryPolicy(REQUEST_TIMEOUT));
        sRequestQueue.add(request);
    }


    public static void exec(final Request<?> request, Integer timeout) {
        if (timeout == null) timeout = REQUEST_TIMEOUT;

        request.setRetryPolicy(createCustomRetryPolicy(timeout));

        sRequestQueue.add(request);
    }

    public static void cancelAll(final Object tag) {
        sRequestQueue.cancelAll(tag);
    }


    private static RetryPolicy createCustomRetryPolicy(int timeout) {
        return new DefaultRetryPolicy(timeout * 1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }
}
