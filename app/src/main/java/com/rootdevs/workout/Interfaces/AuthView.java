package com.rootdevs.workout.Interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface AuthView {
    void signInSuccess(JSONObject jsonObject);
    void signInFailure(VolleyError e);
    void signUpSuccess(JSONObject jsonObject);
    void signUpFailure(VolleyError e);
    void otpReceived(JSONObject object);
    void otpError(VolleyError e);
    void passUpdated(JSONObject object);
    void passUpdateFailure(VolleyError e);
    void showProgress();
    void hideProgress();
}
