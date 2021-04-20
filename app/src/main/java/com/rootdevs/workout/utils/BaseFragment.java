package com.rootdevs.workout.utils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.rootdevs.workout.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public void saveUserDetails(String id, String name, String age, String height, String weight, String email){
        SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences("userDetails",  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id", id);
        editor.putString("name", name);
        editor.putString("age", age);
        editor.putString("height", height);
        editor.putString("weight", weight);
        editor.putString("email", email);
        editor.apply();
    }

    public String getUserId(){
        SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences("userDetails",  Context.MODE_PRIVATE);
        return preferences.getString("id", null);
    }

    public String getEmailAddress(){
        SharedPreferences preferences = Objects.requireNonNull(getContext()).getSharedPreferences("userDetails",  Context.MODE_PRIVATE);
        return preferences.getString("email", null);
    }

    public String getCurrentDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM-yyyy ");
        Date date = new Date();
        return formatter.format(date);
    }

    public String getCompleteCurrentDate(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ");
        Date date = new Date();
        return formatter.format(date);
    }

    public String getCompleteCurrentDateWithTimeStamp(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public String timeIn12HrFormat(String date) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
        Date date1 = dateFormat.parse(date);
        dateFormat = new SimpleDateFormat("hh:mm aa");
        assert date1 != null;
        String formattedDate = dateFormat.format(date1);
        return formattedDate;
    }

    @SuppressLint("SimpleDateFormat")
    public String parseDate(String d) throws ParseException {
        //d = d.replace("-", "/");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = formatter.parse(d);
        SimpleDateFormat format = new SimpleDateFormat("d");
        assert date1 != null;
        String date = format.format(date1);
        if(date.endsWith("1") && !date.endsWith("11"))
            format = new SimpleDateFormat("MMMM d'st', yyyy");
        else if(date.endsWith("2") && !date.endsWith("12"))
            format = new SimpleDateFormat("MMMM d'nd', yyyy");
        else if(date.endsWith("3") && !date.endsWith("13"))
            format = new SimpleDateFormat("MMMM d'rd', yyyy");
        else
            format = new SimpleDateFormat("MMMM d'th', yyyy");
        return format.format(date1);
    }

    @SuppressLint("SimpleDateFormat")
    public String getDayOfWeek(String date) throws ParseException {
        SimpleDateFormat format1=new SimpleDateFormat("dd-MM-yyyy");
        Date dt1= null;
        dt1 = format1.parse(date);
        DateFormat format2=new SimpleDateFormat("EEEE");
        assert dt1 != null;
        return format2.format(dt1);
    }

    @SuppressLint("SimpleDateFormat")
    public boolean dateMatches(String date1, String date2){
        SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);
            Log.v("Date1: " , d1.toString() + "");
            Log.v("Date2: " , d2.toString() + "");
            if(d1.toString().equals(d2.toString())){
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}
