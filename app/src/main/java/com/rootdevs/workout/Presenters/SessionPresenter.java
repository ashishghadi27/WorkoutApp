package com.rootdevs.workout.Presenters;

import android.content.Context;

import com.android.volley.VolleyError;
import com.rootdevs.workout.Interfaces.ApiHandler;
import com.rootdevs.workout.Interfaces.SessionsView;
import com.rootdevs.workout.Interfaces.WorkoutView;
import com.rootdevs.workout.utils.APICaller;
import com.rootdevs.workout.utils.Constants;

import org.json.JSONObject;

public class SessionPresenter implements ApiHandler {

    private SessionsView sessionsView;
    private APICaller apiCaller;

    public SessionPresenter(Context context, SessionsView sessionsView) {
        this.sessionsView = sessionsView;
        apiCaller = new APICaller(context, this);
    }

    public void getSessions(String workOutId){
        sessionsView.showProgress();
        apiCaller.getCall(Constants.getSessionsApi + workOutId, Constants.getSessionsRequestId);
    }

    public void getExercises(String sessionId){
        sessionsView.showProgress();
        apiCaller.getCall(Constants.getExcercisesApi + sessionId, Constants.getExcercisesRequestId);
    }

    @Override
    public void success(JSONObject object, int requestId) {
        switch (requestId){
            case Constants.getSessionsRequestId:
                sessionsView.sessionDataSuccess(object);
                sessionsView.hideProgress();
                break;
            case Constants.getExcercisesRequestId:
                sessionsView.getExercisesSuccess(object);
                sessionsView.hideProgress();
                break;
        }
    }

    @Override
    public void failure(VolleyError e, int requestId) {
        switch (requestId){
            case Constants.getSessionsRequestId:
                sessionsView.sessionDataFailure(e);
                sessionsView.hideProgress();
                break;
            case Constants.getExcercisesRequestId:
                sessionsView.getExercisesFailure(e);
                sessionsView.hideProgress();
                break;
        }
    }
}
