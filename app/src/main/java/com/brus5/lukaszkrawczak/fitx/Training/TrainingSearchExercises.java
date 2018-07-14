package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.MainActivity;
import com.brus5.lukaszkrawczak.fitx.R;

public class TrainingSearchExercises extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "TrainingSearchExercises";
    ImageView imageViewButtonRotate, imageViewBodyBack, imageViewBodyFront;

    TextView textViewButtonChest, textViewButtonAbs, textViewButtonQuads, textViewButtonShoulders, textViewButtonBiceps, textViewButtonForearms, textViewButtonLats, textViewButtonTraps, textViewButtonGlutes, textViewButtonTriceps, textViewButtonHamstrings, textViewButtonCalves;

    Canvas canvas = new Canvas();
    Paint paint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_search_exercises);
        changeStatusBarColor();
        loadInputs();
        onBackButtonPressed();
        drawLine();
        button();
    }

    private void button() {
        imageViewButtonRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: "+v.toString() );
                if (imageViewBodyBack.getVisibility() == View.INVISIBLE){
                    bodyRotate(1);
                }
                else if (imageViewBodyFront.getVisibility() == View.INVISIBLE){
                    bodyRotate(0);
                }
            }
        });
    }

    private void drawLine() {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1f);
        canvas.drawLine(200,1000,1400,300,paint);
    }

    private void bodyRotate(int rotation){
        switch (rotation){
            case 0:
                hideBackBody();
                showFrontBody();
                break;
            case 1:
                hideFrontBody();
                showBackBody();
                break;
        }
    }

    private void showBackBody() {
        imageViewBodyBack.setVisibility(View.VISIBLE);
        textViewButtonLats.setVisibility(View.VISIBLE);
        textViewButtonTraps.setVisibility(View.VISIBLE);
        textViewButtonGlutes.setVisibility(View.VISIBLE);
        textViewButtonTriceps.setVisibility(View.VISIBLE);
        textViewButtonHamstrings.setVisibility(View.VISIBLE);
        textViewButtonCalves.setVisibility(View.VISIBLE);
    }

    private void hideBackBody() {
        imageViewBodyBack.setVisibility(View.INVISIBLE);
        textViewButtonLats.setVisibility(View.INVISIBLE);
        textViewButtonTraps.setVisibility(View.INVISIBLE);
        textViewButtonGlutes.setVisibility(View.INVISIBLE);
        textViewButtonTriceps.setVisibility(View.INVISIBLE);
        textViewButtonHamstrings.setVisibility(View.INVISIBLE);
        textViewButtonCalves.setVisibility(View.INVISIBLE);
    }

    private void hideFrontBody() {
        imageViewBodyFront.setVisibility(View.INVISIBLE);
        textViewButtonChest.setVisibility(View.INVISIBLE);
        textViewButtonAbs.setVisibility(View.INVISIBLE);
        textViewButtonQuads.setVisibility(View.INVISIBLE);
        textViewButtonShoulders.setVisibility(View.INVISIBLE);
        textViewButtonBiceps.setVisibility(View.INVISIBLE);
        textViewButtonForearms.setVisibility(View.INVISIBLE);
    }

    private void showFrontBody(){
        imageViewBodyFront.setVisibility(View.VISIBLE);
        textViewButtonChest.setVisibility(View.VISIBLE);
        textViewButtonAbs.setVisibility(View.VISIBLE);
        textViewButtonQuads.setVisibility(View.VISIBLE);
        textViewButtonShoulders.setVisibility(View.VISIBLE);
        textViewButtonBiceps.setVisibility(View.VISIBLE);
        textViewButtonForearms.setVisibility(View.VISIBLE);
    }

    private void loadInputs() {
        imageViewButtonRotate = findViewById(R.id.imageViewButtonRotate);
        imageViewBodyBack = findViewById(R.id.imageViewBodyBack);
        imageViewBodyFront = findViewById(R.id.imageViewBodyFront);

        textViewButtonChest = findViewById(R.id.textViewButtonChest);
        textViewButtonAbs = findViewById(R.id.textViewButtonAbs);
        textViewButtonQuads = findViewById(R.id.textViewButtonQuads);
        textViewButtonShoulders = findViewById(R.id.textViewButtonShoulders);
        textViewButtonBiceps = findViewById(R.id.textViewButtonBiceps);
        textViewButtonForearms = findViewById(R.id.textViewButtonForearms);
        textViewButtonLats = findViewById(R.id.textViewButtonLats);
        textViewButtonTraps = findViewById(R.id.textViewButtonTraps);
        textViewButtonGlutes = findViewById(R.id.textViewButtonGlutes);
        textViewButtonTriceps = findViewById(R.id.textViewButtonTriceps);
        textViewButtonHamstrings = findViewById(R.id.textViewButtonHamstrings);
        textViewButtonCalves = findViewById(R.id.textViewButtonCalves);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(TrainingSearchExercises.this, R.color.color_main_activity_statusbar));
        }
        Toolbar toolbar = findViewById(R.id.toolbarTrainingSearchExercises);
        setSupportActionBar(toolbar);
    }

    private void onBackButtonPressed() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewButtonChest:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_chest));
                break;
            case R.id.textViewButtonAbs:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_abs));
                break;
            case R.id.textViewButtonQuads:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_quads));
                break;
            case R.id.textViewButtonShoulders:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_shoulders));
                break;
            case R.id.textViewButtonBiceps:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_biceps));
                break;
            case R.id.textViewButtonForearms:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_forearms));
                break;
            case R.id.textViewButtonLats:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_lats));
                break;
            case R.id.textViewButtonTraps:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_trapss));
                break;
            case R.id.textViewButtonGlutes:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_glutes));
                break;
            case R.id.textViewButtonTriceps:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_triceps));
                break;
            case R.id.textViewButtonHamstrings:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_hamstrings));
                break;
            case R.id.textViewButtonCalves:
                runNextActivity(TrainingSearchExercises.this, String.valueOf(R.string.body_calves));
                break;
        }
    }

    public void runNextActivity(Context context, String exercise){
        Intent intent = new Intent(context,TrainingExcerciseList.class);
        intent.putExtra("exercise",exercise);
        TrainingSearchExercises.this.startActivity(intent);
    }



}