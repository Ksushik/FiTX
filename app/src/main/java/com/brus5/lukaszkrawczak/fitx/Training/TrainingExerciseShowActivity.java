package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.brus5.lukaszkrawczak.fitx.Diet.DietProductShowActivity;
import com.brus5.lukaszkrawczak.fitx.R;
import com.squareup.picasso.Picasso;

public class TrainingExerciseShowActivity extends AppCompatActivity {
    private static final String TAG = "TrainingExerciseShowActivity";

    int trainingID;
    String trainingTimeStamp, trainingTarget;
    ImageView trainingImageView,trainingImageView2;
    EditText editTextTrainingExerciseShow;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_exercise_show);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();
        getIntentFromPreviousActiity();

        String url = "http://justfitx.xyz/images/exercises/" + trainingTarget + "/" + trainingID + "_1" + ".jpg";
        String url2 = "http://justfitx.xyz/images/exercises/" + trainingTarget + "/" + trainingID + "_2" + ".jpg";

        Log.i(TAG, "onCreate: " + url);
        Log.i(TAG, "onCreate: " + url2);

        loadImageFromUrl(url);
        loadImageFromUrl2(url2);
    }

    private void loadImageFromUrl(String url) {
        Picasso.with(TrainingExerciseShowActivity.this).load(url).placeholder(null)
                .error(R.mipmap.ic_launcher_round)
                .into(trainingImageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
//                        progressBarDietProductShowActivity.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
//                        progressBarDietProductShowActivity.setVisibility(View.VISIBLE);
                        isError();
                    }
                });
    }
    private void isError() {
        Toast.makeText(TrainingExerciseShowActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
    }
    private void loadImageFromUrl2(String url) {
        Picasso.with(TrainingExerciseShowActivity.this).load(url).placeholder(null)
                .error(R.mipmap.ic_launcher_round)
                .into(trainingImageView2, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
//                        progressBarDietProductShowActivity.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
//                        progressBarDietProductShowActivity.setVisibility(View.VISIBLE);
                        isError();
                    }
                });
    }


    private void loadInput() {
        editTextTrainingExerciseShow = findViewById(R.id.editTextTrainingExerciseShow);
        trainingImageView = findViewById(R.id.trainingImageView);
        trainingImageView2 = findViewById(R.id.trainingImageView2);
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(TrainingExerciseShowActivity.this, R.color.color_main_activity_statusbar));
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

    @SuppressLint("LongLogTag")
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

    @SuppressLint("LongLogTag")
    private void getIntentFromPreviousActiity() {
        Intent intent = getIntent();
        trainingID = intent.getIntExtra("trainingID",-1);
        trainingTimeStamp = intent.getStringExtra("trainingTimeStamp");
        trainingTarget = intent.getStringExtra("trainingTarget");

        Log.e(TAG, "onCreate: "+trainingID);
        Log.e(TAG, "onCreate: "+trainingTimeStamp);
        Log.e(TAG, "onCreate: "+trainingTarget);


    }

}