package com.rootdevs.workout.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {

    public static boolean validateEmail(String email){
        Pattern p = Pattern.compile("\\w[a-zA-Z0-9_.]*@\\w+(([.][a-zA-Z])+)+");
        Matcher matcher = p.matcher(email);
        return matcher.find();
    }

}
