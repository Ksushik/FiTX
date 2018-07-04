package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.brus5.lukaszkrawczak.fitx.R;

public class TrainingExerciseShow extends AppCompatActivity {
    private static final String TAG = "TrainingExerciseShow";
    EditText editTextTrainingExerciseShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_exercise_show);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();

        Intent intent = getIntent();
        String mExercise = intent.getStringExtra("exercise");
        Log.e(TAG, "onCreate: "+mExercise);

    }

    private void loadInput() {
        editTextTrainingExerciseShow = findViewById(R.id.editTextTrainingExerciseShow);
        Log.e(TAG, "loadInput: "+editTextTrainingExerciseShow.getText().toString());
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(TrainingExerciseShow.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarTrainingExerciseShow);
        setSupportActionBar(toolbar);
    }
    private void onBackButtonPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training_exercise_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_save_exercise:
                saveExerciseToDB();
                editTextTrainingExerciseShow.getText().toString();
                Log.e(TAG, "save button pressed: "+editTextTrainingExerciseShow.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveExerciseToDB() {
    }
}