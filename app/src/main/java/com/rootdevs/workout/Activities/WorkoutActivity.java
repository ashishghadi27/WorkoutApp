package com.rootdevs.workout.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.VolleyError;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rootdevs.workout.Fragments.CalendarFragment;
import com.rootdevs.workout.Fragments.WorkoutFragment;
import com.rootdevs.workout.Interfaces.DataAccessor;
import com.rootdevs.workout.Interfaces.SaveAllDataView;
import com.rootdevs.workout.Interfaces.WorkoutView;
import com.rootdevs.workout.Models.Exercise;
import com.rootdevs.workout.Models.Session;
import com.rootdevs.workout.Models.Workout;
import com.rootdevs.workout.Presenters.SaveDataPresenter;
import com.rootdevs.workout.Presenters.WorkoutPresenter;
import com.rootdevs.workout.R;
import com.rootdevs.workout.utils.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WorkoutActivity extends BaseActivity implements WorkoutView, DataAccessor, SaveAllDataView {

    private WorkoutPresenter presenter;
    private SaveDataPresenter saveDataPresenter;
    private ProgressDialog dialog;
    private Session session;
    private List<Exercise> excerciseList = new ArrayList<>();
    private Workout workout;
    private BottomNavigationView navView;
    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        presenter = new WorkoutPresenter(this, this);
        saveDataPresenter = new SaveDataPresenter(this, this);
        navView = findViewById(R.id.nav_view);
        replaceFragment(new CalendarFragment(this), "Calendar");
        dialog = getProgressDialog("Calendar", "Fetching Workout Data", false, this);
        navView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) menuItem -> {

            this.item = menuItem;
            int choice  = menuItem.getItemId();

            switch (choice){
                case R.id.calendar:
                    //navView.setSelectedItemId();
                    replaceFragment(new CalendarFragment(this), "Calendar");
                    return true;

                case R.id.workouts:
                    presenter.getWorkoutsByDate(getUserId(), getCurrentDate());
                    //replaceFragment(new WorkoutFragment(), "Calendar");
                    return true;
            }

            return false;
        });
    }

    @Override
    public void workoutsByMonthSuccess(JSONObject jsonObject) {

    }

    @Override
    public void workoutsByMonthFailed(VolleyError e) {

    }

    @Override
    public void workoutByDateSuccess(JSONObject jsonObject) {
        Log.v("OBJECT", jsonObject.toString());
        WorkoutFragment fragment = new WorkoutFragment(this);
        Bundle bundle = new Bundle();
        bundle.putString("clickedDate", getCurrentDate());
        try {
            if(jsonObject.getString("message").equals("Success")){
                JSONObject jsonObject1 = jsonObject.getJSONArray("response").getJSONObject(0);
                bundle.putString("id", jsonObject1.getString("id"));
                bundle.putString("name", jsonObject1.getString("name"));
                bundle.putString("date", jsonObject1.getString("date"));
                bundle.putString("startTime", jsonObject1.getString("startTime"));
                bundle.putString("endTime", jsonObject1.getString("endTime"));

                workout = new Workout(jsonObject1.getString("id"),
                        jsonObject1.getString("name"),
                        jsonObject1.getString("date"),
                        jsonObject1.getString("startTime"),
                        jsonObject1.getString("endTime"));
            }
            fragment.setArguments(bundle);
            addFragmentAct(fragment,"workout");
        } catch (JSONException e) {
            e.printStackTrace();
            getAlertDialog("Error", "Server Error", this).show();
        }
    }

    @Override
    public void workoutByDateFailed(VolleyError e) {
        getAlertDialog("Error", "Server Error", this).show();
    }

    @Override
    public void saveWorkoutSuccess(JSONObject object) {
        try {
            if(object.getString("message").equals("Success")){
                session.setWorkOutId(object.getString("response"));
                workout.setId(object.getString("response"));
                if(session.getId() == null)
                    saveDataPresenter.addSession(session);
            }
            else getAlertDialog("Data Save failure", "Server Error", this).show();
        } catch (JSONException e) {
            e.printStackTrace();
            getAlertDialog("Data Save failure", "Server Error", this).show();
        }
    }

    @Override
    public void saveWorkoutFailure(VolleyError e) {
        getAlertDialog("Data Save failure", "Server Error", this).show();
    }

    @Override
    public void saveSessionSuccess(JSONObject object) {
        try {
            if(object.getString("message").equals("Insert Success")){
                session.setId(object.getString("response"));
                saveDataPresenter.addExercises(excerciseList, object.getString("response"));
            }
            else getAlertDialog( "Saving Session failed", "Data Parse Error. PLease Try again after some time", this).show();
        } catch (JSONException e) {
            e.printStackTrace();
            getAlertDialog( "Saving Session failed", "Server Error", this).show();
        }
    }

    @Override
    public void saveSessionFailure(VolleyError e) {
        getAlertDialog( "Saving Session failed", "Server Error", this).show();
    }

    @Override
    public void saveExercisesSuccess(JSONObject object) {
        try {
            if(object.getString("message").equals("Insert Success")){
                session = null;
                workout = null;
                excerciseList.clear();
                Log.v("Here", "Clicking");
                if(!navView.findViewById(R.id.calendar).performClick()){
                    replaceFragment(new CalendarFragment(this), "Calendar");
                }
            }
            else getAlertDialog( "Saving Exercises failed", "Data Parse Error. PLease Try again after some time", this).show();
        } catch (JSONException e) {
            e.printStackTrace();
            getAlertDialog( "Saving Session failed", "Server Error", this).show();
        }
    }

    @Override
    public void saveExercisesFailure(VolleyError e) {

    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public List<Exercise> getExerciseList() {
        return excerciseList;
    }

    @Override
    public Workout getWorkoutData() {
        return workout;
    }

    @Override
    public void setExerciseList(List<Exercise> exerciseList) {
        this.excerciseList = exerciseList;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void setWorkoutData(Workout workoutData) {
        this.workout = workoutData;
    }

    @Override
    public void setSelected() {
        navView.getMenu().getItem(1).setChecked(true);
    }

    @Override
    public void saveData() {
        workout.setEndTime(getCompleteCurrentDateWithTimeStampForDB());
        if(workout.getId() == null){
            saveDataPresenter.addWorkout(workout, getUserId());
        }
        else if(session.getId() == null) {
            session.setWorkOutId(workout.getId());
            saveDataPresenter.addSession(session);
        }
        else {
            saveDataPresenter.addExercises(excerciseList, session.getId());
        }

    }
}