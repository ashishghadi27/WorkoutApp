package com.rootdevs.workout.Presenters;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.rootdevs.workout.Interfaces.ApiHandler;
import com.rootdevs.workout.Interfaces.SaveAllDataView;
import com.rootdevs.workout.Models.Exercise;
import com.rootdevs.workout.Models.Session;
import com.rootdevs.workout.Models.Workout;
import com.rootdevs.workout.utils.APICaller;
import com.rootdevs.workout.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SaveDataPresenter implements ApiHandler {

    private APICaller apiCaller;
    private SaveAllDataView view;

    public SaveDataPresenter(Context context, SaveAllDataView view){
        this.view = view;
        apiCaller = new APICaller(context, this);
    }

    public void addWorkout(Workout workout, String userId){
        JSONObject jsonObject = new JSONObject();
        view.showProgress();
        try {
            jsonObject.put("name",workout.getName());
            jsonObject.put("startTime",workout.getStartTime());
            jsonObject.put("endTime",workout.getEndTime());
            jsonObject.put("userId",userId);
            apiCaller.postCall(Constants.addWorkoutPostApi, jsonObject, Constants.addWorkoutPostRequestId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addSession(Session session){
        JSONObject jsonObject = new JSONObject();
        view.showProgress();
        try {
            jsonObject.put("name",session.getName());
            jsonObject.put("workoutId",session.getWorkOutId());
            apiCaller.postCall(Constants.addSessionsPostApi, jsonObject, Constants.addSessionsPostRequestId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addExercises(List<Exercise> list, String sessionId){
        JSONObject parentObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            for(Exercise exercise: list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", exercise.getName());
                jsonObject.put("sets", exercise.getSets());
                jsonObject.put("reps", exercise.getReps());
                jsonObject.put("weight", exercise.getWeight());
                jsonObject.put("comment", exercise.getComment());
                jsonObject.put("sessionId", sessionId);
                array.put(jsonObject);
            }
            parentObject.put("excercises", array);
            Log.v("EXCERCISE OBJECT", parentObject.toString());
            apiCaller.postCall(Constants.addExcercisesPostApi, parentObject, Constants.addExcercisesPostRequestId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void success(JSONObject object, int requestId) {
        switch (requestId){
            case Constants.addWorkoutPostRequestId:
                view.hideProgress();
                view.saveWorkoutSuccess(object);
                break;
            case Constants.addSessionsPostRequestId:
                view.hideProgress();
                view.saveSessionSuccess(object);
                break;
            case Constants.addExcercisesPostRequestId:
                view.hideProgress();
                view.saveExercisesSuccess(object);
                break;
        }
    }

    @Override
    public void failure(VolleyError e, int requestId) {
        switch (requestId){
            case Constants.addWorkoutPostRequestId:
                view.hideProgress();
                view.saveWorkoutFailure(e);
                break;
            case Constants.addSessionsPostRequestId:
                view.hideProgress();
                view.saveSessionFailure(e);
                break;
            case Constants.addExcercisesPostRequestId:
                view.hideProgress();
                view.saveExercisesFailure(e);
                break;
        }
    }
}
