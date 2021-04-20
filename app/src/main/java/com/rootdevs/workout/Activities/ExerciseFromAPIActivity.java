package com.rootdevs.workout.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rootdevs.workout.Adapters.ExerciseAdapter;
import com.rootdevs.workout.Interfaces.SessionsView;
import com.rootdevs.workout.Models.Exercise;
import com.rootdevs.workout.Presenters.SessionPresenter;
import com.rootdevs.workout.R;
import com.rootdevs.workout.utils.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExerciseFromAPIActivity extends BaseActivity implements SessionsView {

    private List<Exercise> exerciseList;
    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private SessionPresenter presenter;
    private TextView sessionName;
    private String sessionId;
    private LinearLayout noExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_from_a_p_i);
        Intent intent = getIntent();
        String name = intent.getStringExtra("sessionName");
        sessionId = intent.getStringExtra("sessionId");

        presenter = new SessionPresenter(this, this);
        exerciseList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        noExercise = findViewById(R.id.noExercise);
        sessionName = findViewById(R.id.sessionName);
        sessionName.setText(name);
        adapter = new ExerciseAdapter(exerciseList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        Log.v("SessionId:", sessionId);
        presenter.getExercises(sessionId);
    }

    @Override
    public void sessionDataSuccess(JSONObject object) {

    }

    @Override
    public void sessionDataFailure(VolleyError e) {

    }

    @Override
    public void getExercisesSuccess(JSONObject object) {
        try {
            if(object.getString("message").equals("Success")){
                JSONArray jsonArray = object.getJSONArray("response");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    Log.v("OBJECT: ", object1.toString());
                    exerciseList.add(new Exercise(
                            object1.getString("id"),
                            object1.getString("name"),
                            object1.getString("sets"),
                            object1.getString("reps"),
                            object1.getString("weight"),
                            object1.getString("comment"),
                            object1.getString("sessionId")
                    ));
                }
                adapter.notifyDataSetChanged();
            }
            else enableNoExercise();
        } catch (JSONException e) {
            enableNoExercise();
            e.printStackTrace();
        }
    }

    @Override
    public void getExercisesFailure(VolleyError e) {
        getAlertDialog("No Data", "No exercises found for particular session", this).show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private void enableNoExercise(){
        noExercise.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}