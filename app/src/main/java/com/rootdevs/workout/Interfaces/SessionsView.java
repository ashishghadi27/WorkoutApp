package com.rootdevs.workout.Interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface SessionsView {
    void sessionDataSuccess(JSONObject object);
    void sessionDataFailure(VolleyError e);
    void showProgress();
    void hideProgress();
}
