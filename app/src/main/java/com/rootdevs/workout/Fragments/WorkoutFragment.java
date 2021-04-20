package com.rootdevs.workout.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rootdevs.workout.Activities.ExerciseFromAPIActivity;
import com.rootdevs.workout.Adapters.ExerciseAdapter;
import com.rootdevs.workout.Adapters.SessionAdapter;
import com.rootdevs.workout.Interfaces.DataAccessor;
import com.rootdevs.workout.Interfaces.SessionCardClickListener;
import com.rootdevs.workout.Interfaces.SessionsView;
import com.rootdevs.workout.Models.Session;
import com.rootdevs.workout.Models.Workout;
import com.rootdevs.workout.Presenters.SessionPresenter;
import com.rootdevs.workout.R;
import com.rootdevs.workout.utils.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkoutFragment extends BaseFragment implements SessionsView, SessionCardClickListener {

    private boolean hasSessions = false;
    private RelativeLayout workoutDataLay, noSessionsLay;
    private Workout workout = null;
    private RecyclerView recyclerView;
    private TextView day, startTime, endTime, workoutName;
    private DataAccessor accessor;
    private SessionPresenter presenter;
    private SessionAdapter adapter;
    private LinearLayout addSession;
    private String clickedDate = null;

    public WorkoutFragment(DataAccessor accessor) {
        this.accessor = accessor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null ){
            if(bundle.containsKey("id")){
                hasSessions = true;
                workout = new Workout(bundle.getString("id"),
                        bundle.getString("name"),
                        bundle.getString("date"),
                        bundle.getString("startTime"),
                        bundle.getString("endTime"));
            }
            if(bundle.containsKey("clickedDate")){
                clickedDate = bundle.getString("clickedDate");
            }
        }
        presenter = new SessionPresenter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        workoutDataLay = view.findViewById(R.id.workoutDataLay);
        noSessionsLay = view.findViewById(R.id.noSessionsLay);
        recyclerView = view.findViewById(R.id.recyclerView);
        workoutName = view.findViewById(R.id.workoutName);
        day = view.findViewById(R.id.day);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);
        addSession = view.findViewById(R.id.addSession);
        accessor.setWorkoutData(workout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //Log.v("Click in workout", clickedDate);
        if(!dateMatches(clickedDate, getCompleteCurrentDate())){
            addSession.setVisibility(View.GONE);
        }

        addSession.setOnClickListener(view1 -> {
            createSession();
        });

        try {
            initialize();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws ParseException {
        if(hasSessions){
            setWorkoutData();
        }
        else {
            workoutDataLay.setVisibility(View.GONE);
            noSessionsLay.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            workoutName.setText(parseDate(clickedDate));
        }
    }

    private void setWorkoutData() throws ParseException {
        day.setText(getDayOfWeek(workout.getDate()).toLowerCase());
        startTime.setText(timeIn12HrFormat(workout.getStartTime()));
        endTime.setText(timeIn12HrFormat(workout.getEndTime()));
        workoutName.setText(parseDate(workout.getDate()));
        getSessions(workout.getId());
    }

    private void getSessions(String id){
        presenter.getSessions(id);
    }

    private void createSession(){
        final BottomSheetDialog bottom_sheet_dialog = new BottomSheetDialog(Objects.requireNonNull(getContext()));
        View dialog = View.inflate(getContext(), R.layout.session_name_lay, null);
        bottom_sheet_dialog.setContentView(dialog);
        bottom_sheet_dialog.show();

        EditText sessionName = dialog.findViewById(R.id.sessionName);
        RelativeLayout createSession = dialog.findViewById(R.id.createSession);

        createSession.setOnClickListener(view -> {
            String sessName = sessionName.getText().toString().trim();
            if(!TextUtils.isEmpty(sessName)){
                try {
                    if(accessor.getWorkoutData() == null)
                        accessor.setWorkoutData(new Workout(null,
                                parseDate(getCompleteCurrentDate()),
                                getCompleteCurrentDate(),
                                getCompleteCurrentDateWithTimeStamp(),
                                null));
                    accessor.setSession(new Session(null, sessName, null));
                    ExerciseFragment fragment = new ExerciseFragment(accessor);
                    Bundle bundle = new Bundle();
                    bundle.putString("sessionName", sessName);
                    fragment.setArguments(bundle);
                    bottom_sheet_dialog.dismiss();
                    addFragment(fragment, "exercise");
                } catch (ParseException e) {
                    e.printStackTrace();
                    bottom_sheet_dialog.dismiss();
                }
            }
            else sessionName.setError("Field Cannot be empty");
        });

    }

    @Override
    public void sessionDataSuccess(JSONObject object) {
        List<Session> sessionList = new ArrayList<>();
        try {
            if(object.getString("message").equals("Success")){
                JSONArray jsonArray = object.getJSONArray("response");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject object1 = jsonArray.getJSONObject(i);
                    sessionList.add(new Session(object1.getString("id"),
                            object1.getString("name"),
                            object1.getString("workoutId")));
                }
                adapter = new SessionAdapter(sessionList, getContext(), this);
                recyclerView.setAdapter(adapter);
            }
            else getAlertDialog("No Data", "No Sessions Found", getContext()).show();
        } catch (JSONException e) {
            e.printStackTrace();
            getAlertDialog("No Data", "No Sessions Found", getContext()).show();
        }
    }

    @Override
    public void sessionDataFailure(VolleyError e) {
        getAlertDialog("ERROR", "Server error Occurred", getContext()).show();
    }

    @Override
    public void getExercisesSuccess(JSONObject object) {

    }

    @Override
    public void getExercisesFailure(VolleyError e) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void toExerciseActivity(String sessionName, String sessionId) {
        Intent intent = new Intent(getContext(), ExerciseFromAPIActivity.class);
        intent.putExtra("sessionName", sessionName);
        intent.putExtra("sessionId", sessionId);
        startActivity(intent);
    }
}