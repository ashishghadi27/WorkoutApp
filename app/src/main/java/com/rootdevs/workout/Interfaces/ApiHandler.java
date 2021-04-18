package com.rootdevs.workout.Interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ApiHandler {

    void success(JSONObject object, int requestId);
    void failure(VolleyError e, int requestId);

}
