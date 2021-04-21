package com.rootdevs.workout.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rootdevs.workout.Interfaces.ApiHandler;

import org.json.JSONObject;

public class APICaller {

    private RequestQueue queue;
    private ApiHandler handler;

    public APICaller(Context context, ApiHandler handler){
        this.queue = Volley.newRequestQueue(context);
        this.handler = handler;
    }

    public void getCall(String api, int requestId){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, api, null,
                response -> {
                    Log.v("Response: ", response.toString());
                    handler.success(response, requestId);
                },
                error -> {
                    Log.v("Response:", error.getCause() + "    " + error.getMessage());
                    handler.failure(error, requestId);
                });
        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.queue.add(request);
    }

    public void postCall(String api, JSONObject jsonObject, int requestId){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, api, jsonObject,
                response -> {
                    Log.v("Response: ", response.toString());
                    handler.success(response, requestId);
                },
                error -> {
                    Log.v("Response:", error.getCause() + "    " + error.getMessage());
                    handler.failure(error, requestId);
                });
        this.queue.add(request);
    }

}
