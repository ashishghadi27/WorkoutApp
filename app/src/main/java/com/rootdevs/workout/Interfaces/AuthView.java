package com.rootdevs.workout.Interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface AuthView {
    void signInSuccess(JSONObject jsonObject);
    void signInFailure(VolleyError e);
    void signUpSuccess(JSONObject jsonObject);
    void signUpFailure(VolleyError e);
    void showProgress();
    void hideProgress();
}
