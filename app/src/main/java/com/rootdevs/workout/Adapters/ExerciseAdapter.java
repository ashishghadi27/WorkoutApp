package com.rootdevs.workout.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.rootdevs.workout.Models.Exercise;
import com.rootdevs.workout.R;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder> {

    private List<Exercise> listItems;
    private Context context;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName, sets, reps, weight, comment;
        MaterialCardView cardView;

        public MyViewHolder(View view) {
            super(view);
            exerciseName = view.findViewById(R.id.exerciseName);
            sets = view.findViewById(R.id.sets);
            reps = view.findViewById(R.id.reps);
            weight = view.findViewById(R.id.weight);
            comment = view.findViewById(R.id.comment);
            cardView =view.findViewById(R.id.card);
        }

    }

    public ExerciseAdapter(List<Exercise> list, Context context) {
        this.listItems = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_lay, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Exercise exercise = listItems.get(position);
        holder.exerciseName.setText(exercise.getName());
        holder.weight.setText(exercise.getWeight());
        holder.sets.setText(exercise.getSets());
        holder.reps.setText(exercise.getReps());
        holder.comment.setText(exercise.getComment());
        holder.cardView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

}
