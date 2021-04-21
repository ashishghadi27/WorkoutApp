package com.rootdevs.workout.Interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface SaveAllDataView {
    void saveWorkoutSuccess(JSONObject object);
    void saveWorkoutFailure(VolleyError e);
    void saveSessionSuccess(JSONObject object);
    void saveSessionFailure(VolleyError e);
    void saveExercisesSuccess(JSONObject object);
    void saveExercisesFailure(VolleyError e);
    void showProgress();
    void hideProgress();
}
