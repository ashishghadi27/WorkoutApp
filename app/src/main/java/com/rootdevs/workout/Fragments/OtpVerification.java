package com.rootdevs.workout.Fragments;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rootdevs.workout.Interfaces.AuthView;
import com.rootdevs.workout.Presenters.AuthPresenter;
import com.rootdevs.workout.R;
import com.rootdevs.workout.utils.BaseFragment;
import com.rootdevs.workout.utils.EmailValidation;

import org.json.JSONException;
import org.json.JSONObject;

public class OtpVerification extends BaseFragment implements AuthView {

    private RelativeLayout verify, getOTP;
    private EditText otpEditText, email;
    private TextView message, resend, signIn, signUp;
    private LinearLayout resendLay;
    private String otp = "";
    private AuthPresenter presenter;
    private ProgressDialog dialog;

    public OtpVerification() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp_verification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        otpEditText = view.findViewById(R.id.otp);
        verify = view.findViewById(R.id.verify);
        email = view.findViewById(R.id.email);
        getOTP = view.findViewById(R.id.getOTP);
        message = view.findViewById(R.id.message);
        resendLay = view.findViewById(R.id.resendLay);
        resend = view.findViewById(R.id.resend);
        signIn = view.findViewById(R.id.signIn);
        signUp = view.findViewById(R.id.signUp);
        dialog = getProgressDialog("Password Reset", "Sending OTP", false, getContext());
        getOTP.setOnClickListener(view1 -> {
            sendOtp();
        });

        verify.setOnClickListener(view1 -> {
            if(!TextUtils.isEmpty(this.otp)){
                String userOtp = otpEditText.getText().toString().trim();
                if(!TextUtils.isEmpty(userOtp))
                {
                    if(otp.equals(userOtp)){
                        UpdatePassword updatePassword = new UpdatePassword();
                        Bundle bundle = new Bundle();
                        bundle.putString("email", email.getText().toString().trim());
                        updatePassword.setArguments(bundle);
                        addFragment(updatePassword, "UpdatePass");
                    }
                    else otpEditText.setError("Invalid OTP");
                }
                else otpEditText.setError("Please Enter OTP");
            }
            else getAlertDialog("OTP Verification Error",  "Please Click on Resend OTP and enter new OTP", getContext()).show();
        });

        resend.setOnClickListener(view1 -> {
            sendOtp();
        });

        signIn.setOnClickListener(view1 -> {
            replaceFragment(new SignIn(), "Signin");
        });

        signUp.setOnClickListener(view1 -> {
            replaceFragment(new SignIn(), "Signup");
        });
    }

    private void sendOtp(){
        String emailStr = email.getText().toString().trim();
        if(!TextUtils.isEmpty(emailStr)){
            if(EmailValidation.validateEmail(emailStr)){
                presenter.getOTP(emailStr);
            }
            else email.setError("Invalid Email Address");
        }
        else email.setError("Enter Email Address");
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

        try {
            if(object.getString("message").equals("Success")){
                this.otp = object.getJSONObject("response").getString("otp");
                message.setText(R.string.enter_otp_that_you_received_on_your_mail);
                email.setVisibility(View.GONE);
                otpEditText.setVisibility(View.VISIBLE);
                getOTP.setVisibility(View.GONE);
                verify.setVisibility(View.VISIBLE);
                resendLay.setVisibility(View.VISIBLE);
            }
            else getAlertDialog("ERROR", "Some Server Error Occurred", getContext()).show();
        }
        catch (JSONException e){
            getAlertDialog("ERROR", "Some Server Error Occurred", getContext()).show();
        }
    }

    @Override
    public void otpError(VolleyError e) {
        Log.v("Error", e.getCause() + "   " + e.getMessage());
        getAlertDialog("ERROR", "Some Server Error Occurred", getContext()).show();
    }

    @Override
    public void passUpdated(JSONObject object) {

    }

    @Override
    public void passUpdateFailure(VolleyError e) {

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