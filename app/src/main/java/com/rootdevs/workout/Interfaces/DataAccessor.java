package com.rootdevs.workout.Interfaces;

import com.rootdevs.workout.Models.Exercise;
import com.rootdevs.workout.Models.Session;
import com.rootdevs.workout.Models.Workout;

import java.util.List;

public interface DataAccessor {

    Session getSession();
    List<Exercise> getExerciseList();
    Workout getWorkoutData();
    void setExerciseList(List<Exercise> exerciseList);
    void setSession(Session session);
    void setWorkoutData(Workout workoutData);
}
