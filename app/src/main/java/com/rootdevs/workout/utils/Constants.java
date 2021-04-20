package com.rootdevs.workout.utils;
public class Constants {

    private static final String domain = "http://192.168.1.102:";
    private static final String portNo = "8070";
    private static final String domainSuffix = "/workout/index.php/";
    private static final String finalDomain = domain + portNo + domainSuffix;

    public static final String loginPostApi = finalDomain + "login";
    public static final int loginPostApiRequestId = 1001;

    public static final String registerPostApi = finalDomain + "register";
    public static final int registerPostRequestId = 1002;

    public static final String addWorkoutPostApi = finalDomain + "addWorkout";
    public static final int addWorkoutPostRequestId = 1003;

    public static final String addSessionsPostApi = finalDomain + "addSessions";
    public static final int addSessionsPostRequestId = 1004;

    public static final String addExcercisesPostApi = finalDomain + "addExcercises";
    public static final int addExcercisesPostRequestId = 1005;

    public static final String updatePassPostApi = finalDomain + "updatePass";
    public static final int updatePassRequestId = 1006;

    public static final String getWorkoutsApi = finalDomain + "getWorkouts?userId=%s&date=%s";
    public static final int getWorkoutsRequestId = 1007;

    public static final String getSessionsApi = finalDomain + "getSessions?workoutId=";
    public static final int getSessionsRequestId = 1008;

    public static final String getExcercisesApi = finalDomain + "getExcercises?sessionId=1";
    public static final int getExcercisesRequestId = 1009;

    public static final String getOtpApi = finalDomain + "getOtp?email=";
    public static final int getOtpRequestId = 1010;

    public static final String getWorkoutsByMonthGetApi = finalDomain + "getWorkoutsByMonth?userId=%s&mon=%s";
    public static final int getWorkoutsByMonthRequestId = 1011;

}
