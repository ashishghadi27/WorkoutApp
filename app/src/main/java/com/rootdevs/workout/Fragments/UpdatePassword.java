package com.rootdevs.workout.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rootdevs.workout.Interfaces.AuthView;
import com.rootdevs.workout.Presenters.AuthPresenter;
import com.rootdevs.workout.R;
import com.rootdevs.workout.utils.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdatePassword extends BaseFragment implements AuthView {

    private String emailAddr;
    private EditText newPass, confirmNewPass;
    private RelativeLayout updatePass;
    private AuthPresenter presenter;
    private TextView signIn, signUp;

    public UpdatePassword() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        emailAddr = bundle.getString("email");
        presenter = new AuthPresenter(getContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newPass = view.findViewById(R.id.password);
        confirmNewPass = view.findViewById(R.id.confirmPassword);
        updatePass = view.findViewById(R.id.updatePass);
        signIn = view.findViewById(R.id.signIn);
        signUp = view.findViewById(R.id.signUp);
        updatePass.setOnClickListener(view1 -> {
            try {
                updatePass(emailAddr, newPass.getText().toString().trim(), confirmNewPass.getText().toString().trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        signIn.setOnClickListener(view1 -> {
            replaceFragment(new SignIn(), "Signin");
        });

        signUp.setOnClickListener(view1 -> {
            replaceFragment(new SignIn(), "Signup");
        });
    }

    private void updatePass(String email, String pass, String confirmPass) throws JSONException {
        if(!TextUtils.isEmpty(pass)){
            if(!TextUtils.isEmpty(confirmPass)){
                if(pass.equals(confirmPass))
                    presenter.updatePass(email, pass);
                else confirmNewPass.setError("Password Mismatch");
            }
            else confirmNewPass.setError("Field Cannot be Empty");
        }
        else newPass.setError("Field Cannot be Empty");
    }

    @Override
    public void signInSuccess(JSONObject jsonObject) {

    }

    @Override
    public void signInFailure(VolleyError e) {

    }

    @Override
    public void signUpSuccess(JSONObject jsonObject) {

    }

    @Override
    public void signUpFailure(VolleyError e) {

    }

    @Override
    public void otpReceived(JSONObject object) {

    }

    @Override
    public void otpError(VolleyError e) {

    }

    @Override
    public void passUpdated(JSONObject object) {
        try {
            if(object.getString("response").equals("Password Updated")){
                getAlertDialog("Reset Password", "Password Updated. Please Sign in", getContext()).show();
                replaceFragment(new SignIn(), "Signin");
            }
        } catch (JSONException e) {
            getAlertDialog("Reset Password Failed", "Something went wrong. Please try again later", getContext()).show();
            replaceFragment(new OtpVerification(), "OtpVerification");
            //e.printStackTrace();
        }
    }

    @Override
    public void passUpdateFailure(VolleyError e) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}