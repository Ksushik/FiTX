package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.R;

public class TrainingExcerciseListActivity extends AppCompatActivity {
    private static final String TAG = "TrainingExcerciseListActivity";
    private String excerciseSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_excercise_list);
        loadInput();
        changeStatusBarColor();
        onBackButtonPressed();
        getIntentFromPreviousActiity();

    }

    private void getIntentFromPreviousActiity() {
        Intent intent = getIntent();
        int mExercise = intent.getIntExtra("exercise",-1);
        Log.e(TAG, "onCreate: "+mExercise);

        TrainingList trainingList = new TrainingList();
        trainingList.setResId(mExercise);
        excerciseSelect = trainingList.getResourceName();
        Log.e(TAG, "getIntentFromPreviousActiity: "+excerciseSelect);
    }



    private void loadInput() {
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(TrainingExcerciseListActivity.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarTrainingExcerciseList);
        setSupportActionBar(toolbar);
    }
    private void onBackButtonPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
