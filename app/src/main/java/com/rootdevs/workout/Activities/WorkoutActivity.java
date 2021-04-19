package com.rootdevs.workout.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rootdevs.workout.Fragments.CalendarFragment;
import com.rootdevs.workout.Fragments.WorkoutFragment;
import com.rootdevs.workout.R;
import com.rootdevs.workout.utils.BaseActivity;

public class WorkoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        replaceFragment(new CalendarFragment(), "Calendar");
        navView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) menuItem -> {

            int choice  = menuItem.getItemId();

            switch (choice){
                case R.id.calendar:
                    //navView.setSelectedItemId();
                    replaceFragment(new CalendarFragment(), "Calendar");
                    return true;

                case R.id.workouts:
                    replaceFragment(new WorkoutFragment(), "Calendar");
                    return true;
            }

            return false;
        });
    }
}