package com.rootdevs.workout.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rootdevs.workout.Activities.AuthActivity;
import com.rootdevs.workout.Activities.WorkoutActivity;
import com.rootdevs.workout.Interfaces.AuthView;
import com.rootdevs.workout.Presenters.AuthPresenter;
import com.rootdevs.workout.R;
import com.rootdevs.workout.utils.BaseFragment;
import com.rootdevs.workout.utils.EmailValidation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class SignIn extends BaseFragment implements AuthView {

    private EditText email, pass;
    private RelativeLayout login;
    private TextView signUp;
    private AuthPresenter presenter;
    private ProgressDialog dialog;

    public SignIn() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AuthPresenter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login);
        signUp = view.findViewById(R.id.signUp);
        dialog = getProgressDialog("Authenticating", "Verifying Credentials", false, getContext());
        signUp.setOnClickListener(view1 ->{
            addFragment(new SignUp(), "Signup");
        });

        login.setOnClickListener(view1 -> {
            loginIn(email.getText().toString().trim(), pass.getText().toString().trim());
        });
    }

    private void loginIn(String emailId, String password){
        if(!TextUtils.isEmpty(emailId) && !TextUtils.isEmpty(password)) {
            try {
                if(EmailValidation.validateEmail(emailId))
                    presenter.login(emailId, password);
                else email.setError("Invalid Email Address");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else getAlertDialog("Invalid Input", "Fields Cannot be empty", getContext()).show();
    }

    @Override
    public void signInSuccess(JSONObject jsonObject) {
        Log.v("SUCCESS RESP", jsonObject.toString());
        try {
            if(jsonObject.getString("message").equals("Valid User")){
                Intent intent = new Intent(getActivity(), WorkoutActivity.class);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finish();
            }
            else getAlertDialog("Sign In Failed", "Invalid Email or Password", getContext()).show();
        } catch (JSONException e) {
            getAlertDialog("Sign In Failed", "Some Error Occurred", getContext()).show();
            e.printStackTrace();
        }
    }

    @Override
    public void signInFailure(VolleyError e) {
        Log.v("Failure: ", e.getCause() + "   " + e.getMessage());
        getAlertDialog("Sign In Failed", "Some Error Occurred", getContext()).show();
    }

    @Override
    public void signUpSuccess(JSONObject jsonObject) {

    }

    @Override
    public void signUpFailure(VolleyError e) {

    }

    @Override
    public void showProgress() {
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }
}