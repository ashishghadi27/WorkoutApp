package com.rootdevs.workout.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rootdevs.workout.Activities.ExerciseFromAPIActivity;
import com.rootdevs.workout.Adapters.ExerciseAdapter;
import com.rootdevs.workout.Interfaces.DataAccessor;
import com.rootdevs.workout.Interfaces.ExerciseCardOperations;
import com.rootdevs.workout.Interfaces.SessionCardClickListener;
import com.rootdevs.workout.Models.Exercise;
import com.rootdevs.workout.R;
import com.rootdevs.workout.utils.BaseFragment;

import java.util.Objects;

public class ExerciseFragment extends BaseFragment implements ExerciseCardOperations {

    private DataAccessor accessor;
    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private LinearLayout addExercise;
    private TextView sessionName;
    private ImageView saveSession;

    public ExerciseFragment(DataAccessor accessor) {
        this.accessor = accessor;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        addExercise = view.findViewById(R.id.addExercise);
        sessionName = view.findViewById(R.id.sessionName);
        saveSession = view.findViewById(R.id.saveSession);
        if(this.accessor.getSession() != null){
            sessionName.setText(this.accessor.getSession().getName() + "");
        }
        adapter = new ExerciseAdapter(accessor.getExerciseList(), getContext(), false, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        addExercise.setOnClickListener(view1 -> {
            addExercise();
        });

        saveSession.setOnClickListener(view1 -> {
            accessor.saveData();
        });
    }

    private void addExercise(){
        final BottomSheetDialog bottom_sheet_dialog = new BottomSheetDialog(Objects.requireNonNull(getContext()));
        View dialog = View.inflate(getContext(), R.layout.add_exercise_lay, null);
        bottom_sheet_dialog.setContentView(dialog);
        bottom_sheet_dialog.show();

        EditText eName, sets, reps, weight, comment;
        eName = dialog.findViewById(R.id.exerciseName);
        sets = dialog.findViewById(R.id.sets);
        reps = dialog.findViewById(R.id.reps);
        weight = dialog.findViewById(R.id.weight);
        comment = dialog.findViewById(R.id.comment);
        RelativeLayout addExercise = dialog.findViewById(R.id.createSession);

        addExercise.setOnClickListener(view -> {
            String eNameStr,setsStr, repsStr, weightStr, commentStr;
            eNameStr = eName.getText().toString().trim();
            setsStr = sets.getText().toString().trim();
            repsStr = reps.getText().toString().trim();
            weightStr = weight.getText().toString().trim();
            commentStr = comment.getText().toString().trim();

            boolean isEmpty =
                    TextUtils.isEmpty(eNameStr) ||
                            TextUtils.isEmpty(setsStr) ||
                            TextUtils.isEmpty(repsStr) ||
                            TextUtils.isEmpty(weightStr) ||
                            TextUtils.isEmpty(commentStr);

            if(!isEmpty){
                accessor.getExerciseList().add(new Exercise(null,
                        eNameStr,
                        setsStr,
                        repsStr,
                        weightStr,
                        commentStr, null));
                adapter.notifyDataSetChanged();
                bottom_sheet_dialog.dismiss();
            }
            else getAlertDialog("Empty Fields", "All Fields are Mandatory", getContext()).show();
        });

    }

    @Override
    public void deleteExercise(int index) {
        accessor.getExerciseList().remove(index);
        adapter.notifyDataSetChanged();
    }
}