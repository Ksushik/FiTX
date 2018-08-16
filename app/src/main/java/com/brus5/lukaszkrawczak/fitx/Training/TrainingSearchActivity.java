package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.SaveSharedPreference;

public class TrainingSearchActivity extends AppCompatActivity implements View.OnClickListener, DefaultView
{
    private static final String TAG = "TrainingSearchActivity";
    private ImageView imageViewBodyBack, imageViewBodyFront;

    private TextView tvButtonChest, tvButtonAbs, tvButtonQuads,
            tvButtonShoulders, tvButtonBiceps, tvButtonForearms,
            tvButtonLats, tvButtonTraps, tvButtonGlutes,
            tvButtonTriceps, tvButtonHamstrings, tvButtonCalves;

    private Button btRotate, btCardio;

    private String dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_2_muscle_choose);
        changeStatusBarColor();
        loadInput();
        onBackButtonPressed();
        button();
        getIntentFromPreviousActiity();
    }

    private void button()
    {
        btRotate.setOnClickListener(v -> {
            if (imageViewBodyBack.getVisibility() == View.INVISIBLE)
            {
                bodyRotate(1);
            }
            else if (imageViewBodyFront.getVisibility() == View.INVISIBLE)
            {
                bodyRotate(0);
            }
        });
        btCardio.setOnClickListener(v -> {
            Intent intent = new Intent(TrainingSearchActivity.this,CardioListActivity.class);
            intent.putExtra("dateFormat", dateFormat);
            startActivity(intent);
        });
    }

    private void bodyRotate(int rotation)
    {
        switch (rotation)
        {
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

    private void showBackBody()
    {
        imageViewBodyBack.setVisibility(View.VISIBLE);
        tvButtonLats.setVisibility(View.VISIBLE);
        tvButtonTraps.setVisibility(View.VISIBLE);
        tvButtonGlutes.setVisibility(View.VISIBLE);
        tvButtonTriceps.setVisibility(View.VISIBLE);
        tvButtonHamstrings.setVisibility(View.VISIBLE);
        tvButtonCalves.setVisibility(View.VISIBLE);
    }

    private void hideBackBody()
    {
        imageViewBodyBack.setVisibility(View.INVISIBLE);
        tvButtonLats.setVisibility(View.INVISIBLE);
        tvButtonTraps.setVisibility(View.INVISIBLE);
        tvButtonGlutes.setVisibility(View.INVISIBLE);
        tvButtonTriceps.setVisibility(View.INVISIBLE);
        tvButtonHamstrings.setVisibility(View.INVISIBLE);
        tvButtonCalves.setVisibility(View.INVISIBLE);
    }

    private void hideFrontBody()
    {
        imageViewBodyFront.setVisibility(View.INVISIBLE);
        tvButtonChest.setVisibility(View.INVISIBLE);
        tvButtonAbs.setVisibility(View.INVISIBLE);
        tvButtonQuads.setVisibility(View.INVISIBLE);
        tvButtonShoulders.setVisibility(View.INVISIBLE);
        tvButtonBiceps.setVisibility(View.INVISIBLE);
        tvButtonForearms.setVisibility(View.INVISIBLE);
    }

    private void showFrontBody()
    {
        imageViewBodyFront.setVisibility(View.VISIBLE);
        tvButtonChest.setVisibility(View.VISIBLE);
        tvButtonAbs.setVisibility(View.VISIBLE);
        tvButtonQuads.setVisibility(View.VISIBLE);
        tvButtonShoulders.setVisibility(View.VISIBLE);
        tvButtonBiceps.setVisibility(View.VISIBLE);
        tvButtonForearms.setVisibility(View.VISIBLE);
    }

    public void loadInput()
    {
        imageViewBodyBack = findViewById(R.id.imageViewBodyBack);
        imageViewBodyFront = findViewById(R.id.imageViewBodyFront);

        tvButtonChest = findViewById(R.id.textViewButtonChest);
        tvButtonAbs = findViewById(R.id.textViewButtonAbs);
        tvButtonQuads = findViewById(R.id.textViewButtonQuads);
        tvButtonShoulders = findViewById(R.id.textViewButtonShoulders);
        tvButtonBiceps = findViewById(R.id.textViewButtonBiceps);
        tvButtonForearms = findViewById(R.id.textViewButtonForearms);
        tvButtonLats = findViewById(R.id.textViewButtonLats);
        tvButtonTraps = findViewById(R.id.textViewButtonTraps);
        tvButtonGlutes = findViewById(R.id.textViewButtonGlutes);
        tvButtonTriceps = findViewById(R.id.textViewButtonTriceps);
        tvButtonHamstrings = findViewById(R.id.textViewButtonHamstrings);
        tvButtonCalves = findViewById(R.id.textViewButtonCalves);

        btRotate = findViewById(R.id.buttonRotate);
        btRotate.setOnClickListener(this);

        btCardio = findViewById(R.id.buttonCardio);
        btCardio.setOnClickListener(this);
    }

    public void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(TrainingSearchActivity.this, R.color.colorPrimaryDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbarTrainingSearchExercises);
        setSupportActionBar(toolbar);
    }

    private void onBackButtonPressed()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
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

    public void runNextActivity(Context context, int resId)
    {
        Intent intent = new Intent(context, TrainingListActivity.class);
        intent.putExtra("exercise",         resId);
        intent.putExtra("dateFormat",       this.dateFormat);
        TrainingSearchActivity.this.startActivity(intent);
    }

    private void getIntentFromPreviousActiity()
    {
        Log.i(TAG, "dateFormat: " + Configuration.getDate());
    }
}