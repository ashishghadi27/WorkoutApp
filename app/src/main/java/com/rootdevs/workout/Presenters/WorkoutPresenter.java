package com.rootdevs.workout.Presenters;

import android.content.Context;

import com.android.volley.VolleyError;
import com.rootdevs.workout.Interfaces.ApiHandler;
import com.rootdevs.workout.Interfaces.WorkoutView;
import com.rootdevs.workout.utils.APICaller;
import com.rootdevs.workout.utils.Constants;

import org.json.JSONObject;

public class WorkoutPresenter implements ApiHandler {

    private WorkoutView workoutView;
    private APICaller apiCaller;

    public WorkoutPresenter(Context context, WorkoutView workoutView) {
        this.workoutView = workoutView;
        apiCaller = new APICaller(context, this);
    }

    public void getWorkoutsByMonth(String userId, String month){
        workoutView.showProgress();
        apiCaller.getCall(String.format(Constants.getWorkoutsByMonthGetApi, userId, month), Constants.getWorkoutsByMonthRequestId);
    }

    public void getWorkoutsByDate(String userId, String date){
        workoutView.showProgress();
        apiCaller.getCall(String.format(Constants.getWorkoutsApi, userId, date), Constants.getWorkoutsRequestId);
    }

    @Override
    public void success(JSONObject object, int requestId) {
        switch (requestId){
            case Constants.getWorkoutsByMonthRequestId:
                workoutView.workoutsByMonthSuccess(object);
                workoutView.hideProgress();
                break;
            case Constants.getWorkoutsRequestId:
                workoutView.hideProgress();
                workoutView.workoutByDateSuccess(object);
                break;
        }
    }

    @Override
    public void failure(VolleyError e, int requestId) {
        switch (requestId){
            case Constants.getWorkoutsByMonthRequestId:
                workoutView.workoutsByMonthFailed(e);
                workoutView.hideProgress();
                break;
            case Constants.getWorkoutsRequestId:
                workoutView.workoutByDateFailed(e);
                workoutView.hideProgress();
                break;
        }
    }
}
