package com.rootdevs.workout.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.rootdevs.workout.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class BaseActivity extends AppCompatActivity {
    public void addFragmentAct(Fragment fragment, String tag){
        getSupportFragmentManager()
                .beginTransaction().add(R.id.fragment_container, fragment, tag)
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_from_right)
                .commit();
    }

    public void replaceFragment(Fragment fragment, String tag){
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragment_container, fragment, tag)
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_from_right)
                .commit();
    }

    public String getUserId(){
        SharedPreferences preferences = getSharedPreferences("userDetails",  Context.MODE_PRIVATE);
        return preferences.getString("id", null);
    }

    public String getEmailAddress(){
        SharedPreferences preferences = getSharedPreferences("userDetails",  Context.MODE_PRIVATE);
        return preferences.getString("email", null);
    }

    public String getCurrentDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ");
        Date date = new Date();
        return formatter.format(date);
    }

    public ProgressDialog getProgressDialog(String title, String message, boolean isCancellable, Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(isCancellable);
        return progressDialog;
    }

    public AlertDialog getAlertDialog(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialog = View.inflate(context, R.layout.alert_lay, null);
        TextView titleText = dialog.findViewById(R.id.title);
        TextView messageText = dialog.findViewById(R.id.message);
        titleText.setText(title);
        messageText.setText(message);
        builder.setView(dialog);
        builder.setCancelable(true);
        return builder.create();
    }

    public String getCompleteCurrentDateWithTimeStampForDB(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
}
