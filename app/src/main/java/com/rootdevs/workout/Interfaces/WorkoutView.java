package com.rootdevs.workout.Interfaces;

import com.android.volley.VolleyError;
import org.json.JSONObject;

public interface WorkoutView {
    void workoutsByMonthSuccess(JSONObject jsonObject);
    void workoutsByMonthFailed(VolleyError e);
    void workoutByDateSuccess(JSONObject jsonObject);
    void workoutByDateFailed(VolleyError e);
    void showProgress();
    void hideProgress();
}
