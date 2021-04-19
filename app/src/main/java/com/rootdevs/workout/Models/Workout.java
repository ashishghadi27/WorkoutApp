package com.rootdevs.workout.Models;

public class Workout {

    private String id;
    private String name;
    private String date;
    private String startTime;
    private String endTime;

    public Workout(String id, String name, String date, String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
