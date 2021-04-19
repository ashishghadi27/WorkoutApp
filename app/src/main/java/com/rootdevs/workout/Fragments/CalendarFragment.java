package com.rootdevs.workout.Fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;

import com.android.volley.VolleyError;
import com.rootdevs.workout.Interfaces.WorkoutView;
import com.rootdevs.workout.Models.Workout;
import com.rootdevs.workout.Presenters.WorkoutPresenter;
import com.rootdevs.workout.R;
import com.rootdevs.workout.utils.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.vo.DateData;

public class CalendarFragment extends BaseFragment implements WorkoutView {

    private MCalendarView mCalendarView;
    private WorkoutPresenter presenter;
    private List<DateData> list;
    private List<Workout> workoutList;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WorkoutPresenter(getContext(), this);
        list = new ArrayList<>();
        workoutList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendarView = ((MCalendarView) view.findViewById(R.id.calendar));
        initialize();
        mCalendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                Log.v("Month change", month + "");
                Log.v("Year change", year + "");
                presenter.getWorkoutsByMonth(getUserId(), month + "-" + year);
            }
        });

        mCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                Log.v("Day: ",date.getDay() + "");
                Log.v("Month: ",date.getMonth() + "");
                Log.v("Year: ",date.getYear() + "");
            }
        });
    }

    private void initialize(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy ");
        Date date = new Date();
        String apiMon = formatter.format(date);
        presenter.getWorkoutsByMonth(getUserId(), apiMon);
    }

    private void markDates(List<DateData> dates){
        for(int i=0;i<dates.size();i++) {
            mCalendarView.markDate(dates.get(i).getYear(),dates.get(i).getMonth(),dates.get(i).getDay());//mark multiple dates with this code.
        }
    }

    @Override
    public void workoutsByMonthSuccess(JSONObject jsonObject) {
        try {
            if(jsonObject.getString("message").equals("Success")){
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                list.clear();
                workoutList.clear();
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    Workout workout = new Workout(object.getString("id"),
                            object.getString("name"),
                            object.getString("date"),
                            object.getString("startTime"),
                            object.getString("endTime"));
                    workoutList.add(workout);
                    String[] arr = object.getString("date").split("-");
                    list.add(new DateData(Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[0])));
                }
                markDates(list);
            }
            else getAlertDialog("Calendar", "No Workouts in this month", getContext()).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void workoutsByMonthFailed(VolleyError e) {

    }

    @Override
    public void workoutByDateSuccess(JSONObject jsonObject) {

    }

    @Override
    public void workoutByDateFailed(VolleyError e) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}