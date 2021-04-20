package com.rootdevs.workout.Models;

public class Session {

    private String id;
    private String name;
    private String workOutId;

    public Session(String id, String name, String workOutId) {
        this.id = id;
        this.name = name;
        this.workOutId = workOutId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkOutId() {
        return workOutId;
    }

    public void setWorkOutId(String workOutId) {
        this.workOutId = workOutId;
    }
}
