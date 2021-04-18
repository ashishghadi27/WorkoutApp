package com.rootdevs.workout.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.BaseBundle;
import android.os.Bundle;

import com.rootdevs.workout.Fragments.SignIn;
import com.rootdevs.workout.R;
import com.rootdevs.workout.utils.BaseActivity;

public class AuthActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragmentAct(new SignIn(), "Signin");
    }
}