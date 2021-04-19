package com.rootdevs.workout.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.BaseBundle;
import android.os.Bundle;

import com.rootdevs.workout.Fragments.SignIn;
import com.rootdevs.workout.R;
import com.rootdevs.workout.utils.BaseActivity;

import java.util.Objects;

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragmentAct(new SignIn(), "Signin");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if(getUserId() != null){
            Intent intent = new Intent(this, WorkoutActivity.class);
            startActivity(intent);
            finish();
        }
    }
}