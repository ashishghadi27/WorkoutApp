package com.rootdevs.workout.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.rootdevs.workout.R;

import java.util.Objects;

public class BaseFragment extends Fragment {



    public void addFragment(Fragment fragment, String tag){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_from_right)
                .add(R.id.fragment_container, fragment, tag).addToBackStack(tag)
                .commit();
    }

    public void replaceFragment(Fragment fragment, String tag){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_from_right)
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
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
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        return builder.create();
    }

    public void saveUserDetails(String name, String age, String height, String weight, String email){
        SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences("userDetails",  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.putString("age", age);
        editor.putString("height", height);
        editor.putString("weight", weight);
        editor.putString("email", email);
        editor.apply();
    }
}
