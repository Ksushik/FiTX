package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;

public class TrainingSearchActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "TrainingSearchActivity";
    ImageView imageViewButtonRotate, imageViewBodyBack, imageViewBodyFront;

    TextView textViewButtonChest, textViewButtonAbs, textViewButtonQuads, textViewButtonShoulders, textViewButtonBiceps, textViewButtonForearms, textViewButtonLats, textViewButtonTraps, textViewButtonGlutes, textViewButtonTriceps, textViewButtonHamstrings, textViewButtonCalves;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_search);
        changeStatusBarColor();
        loadInputs();
        onBackButtonPressed();
        button();
    }

    private void button() {
        imageViewButtonRotate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
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
            getWindow().setStatusBarColor(ContextCompat.getColor(TrainingSearchActivity.this, R.color.colorPrimaryDark));
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
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonChest);
                break;
            case R.id.textViewButtonAbs:
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonAbs);
                break;
            case R.id.textViewButtonQuads:
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonQuads);
                break;
            case R.id.textViewButtonShoulders:
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonShoulders);
                break;
            case R.id.textViewButtonBiceps:
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonBiceps);
                break;
            case R.id.textViewButtonForearms:
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonForearms);
                break;
            case R.id.textViewButtonLats:
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonLats);
                break;
            case R.id.textViewButtonTraps:
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonTraps);
                break;
            case R.id.textViewButtonGlutes:
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonGlutes);
                break;
            case R.id.textViewButtonTriceps:
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonTriceps);
                break;
            case R.id.textViewButtonHamstrings:
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonHamstrings);
                break;
            case R.id.textViewButtonCalves:
                runNextActivity(TrainingSearchActivity.this, R.id.textViewButtonCalves);
                break;
        }
    }

    public void runNextActivity(Context context, int resId){
        Intent intent = new Intent(context,TrainingListActivity.class);
        intent.putExtra("exercise",resId);
        TrainingSearchActivity.this.startActivity(intent);
    }
}