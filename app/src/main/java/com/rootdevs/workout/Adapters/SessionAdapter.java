package com.rootdevs.workout.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.rootdevs.workout.Interfaces.SessionCardClickListener;
import com.rootdevs.workout.Models.Exercise;
import com.rootdevs.workout.Models.Session;
import com.rootdevs.workout.R;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.MyViewHolder> {

    private List<Session> listItems;
    private Context context;
    private SessionCardClickListener cardClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sessionName;
        MaterialCardView cardView;

        public MyViewHolder(View view) {
            super(view);
            sessionName = view.findViewById(R.id.sessionName);
            cardView = view.findViewById(R.id.card);
        }

    }

    public SessionAdapter(List<Session> list, Context context, SessionCardClickListener cardClickListener) {
        this.listItems = list;
        this.cardClickListener = cardClickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_lay, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Session session = listItems.get(position);
        holder.sessionName.setText(session.getName());
        holder.cardView.setOnClickListener(view -> {
            this.cardClickListener.toExerciseActivity(session.getName(), session.getId());
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

}
