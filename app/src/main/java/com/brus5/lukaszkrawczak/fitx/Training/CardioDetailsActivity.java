package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.Configuration;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.Validator.CharacterLimit;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CardioDetailsActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "CardioDetailsAct";
    @SuppressLint("SimpleDateFormat")
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private String trainingTimeStamp, trainingTarget, previousActivity;
    private LinearLayout linearLayout;
    private int trainingID;
    private ImageView imgTrainingL, imgTrainingR;
    private EditText etNotepad;
    private TextView tvName, tvDetails, tvCharsLeft;
    private CheckBox checkBox;
    private TrainingInflater inflater = new TrainingInflater(CardioDetailsActivity.this);
    private Timer timer;
    private CharacterLimit characterLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_1);
        changeStatusBarColor();
        onBackButtonPressed();
        loadInput();
        getIntentFromPreviousActiity();
        String url = RestAPI.URL + "images/cardio/"  + trainingID + ".jpg";
//        loadImages(imgTrainingL, url);
        timer = new Timer(this);
        timer.seekBarTimer();
        getPreviousActivity(previousActivity);
//        characterLimit = new CharacterLimit(etNotepad, tvCharsLeft, 280);
//        etNotepad.addTextChangedListener(characterLimit);
        onTrainingChangerListener(1);
    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_save_cardio:
                if (previousActivity.equals( TrainingActivity.class.getSimpleName() ))
                {
                    TrainingService updateTraining = new TrainingService();
                    //                    updateTraining.TrainingUpdate(saveDTO(), TrainingDetailsActivity.this);
                    finish();
                }

                else if (previousActivity.equals( TrainingListActivity.class.getSimpleName() ))
                {
                    TrainingService acceptService = new TrainingService();
                    //                    acceptService.TrainingInsert(saveDTO(), TrainingDetailsActivity.this);
                    finish();
                }
                else
                {
                    Configuration cfg = new Configuration();
                    //                    cfg.showError(TrainingDetailsActivity.this);
                }
                break;
            case R.id.menu_delete_cardio:
                TrainingService deleteService = new TrainingService();
                //                deleteService.TrainingDelete(deleteDto(), TrainingDetailsActivity.this);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cardio_3_details, menu);

        MenuItem item = menu.findItem(R.id.menu_delete_cardio);
        if (previousActivity.equals( TrainingListActivity.class.getSimpleName() ))
        {
            item.setVisible(false);
        }
        else
        {
            item.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(CardioDetailsActivity.this, R.color.colorPrimaryDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbarCardio);
        setSupportActionBar(toolbar);
    }

    private void onBackButtonPressed()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadInput()
    {
        imgTrainingL = findViewById(R.id.imageViewTraining);
        checkBox = findViewById(R.id.checkBox);
    }

    @SuppressLint("LongLogTag")
    private void getIntentFromPreviousActiity()
    {
        Intent intent = getIntent();
        trainingID = intent.getIntExtra("trainingID", -1);
        trainingTarget = intent.getStringExtra("trainingTarget");
        trainingTimeStamp = intent.getStringExtra("trainingTimeStamp");
        previousActivity = intent.getStringExtra("previousActivity");

        Log.e(TAG, "onCreate: " + trainingID);
        Log.e(TAG, "onCreate: " + trainingTimeStamp);
        Log.e(TAG, "onCreate: " + trainingTarget);
        Log.e(TAG, "onCreate: " + previousActivity);
    }

    private void loadImages(final ImageView imageView, String url)
    {
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(0)
                .cornerRadiusDp(5)
                .oval(false)
                .build();


        Picasso.with(CardioDetailsActivity.this).load(url).placeholder(null).transform(transformation)
                .error(R.mipmap.ic_launcher_error)
                .into(imageView, new com.squareup.picasso.Callback()
                {
                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onError()
                    {
                        Configuration cfg = new Configuration();
                        cfg.showError(CardioDetailsActivity.this);
                    }
                });
    }

    private void getPreviousActivity(String previousActivity)
    {
        if (previousActivity.equals(TrainingActivity.class.getSimpleName()))
        {
//            getUserTrainingDetailsAsynch(CardioDetailsActivity.this);
//            getTrainingNameAsynch(CardioDetailsActivity.this);
            Log.e(TAG, "getPreviousActivity: "+ TrainingActivity.class.getSimpleName());
        }

        // TODO: 13.08.2018 else if CardioList

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.floatingButtonStartPause:
                Log.i(TAG, "onClick: play" );
                if (timer.timerRunning)
                {
                    timer.pauseTimer();
                }
                else
                {
                    timer.startTimer();
                }
                break;
            case R.id.buttonResetTimer:
                Log.i(TAG, "onClick: reset" );
                timer.resetTimer();
                break;
        }
    }

    private int setOnCheckedChangeListener()
    {
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                checkBox.setText(R.string.done);
            }
            else
            {
                checkBox.setText(R.string.not_done);
            }
        });

        if (checkBox.isChecked())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    private void onTrainingChangerListener(int i)
    {
        setOnCheckedChangeListener();
        if (i == 0)
        {
            checkBox.setChecked(false);
            checkBox.setText(R.string.not_done);
        }
        else
        {
            checkBox.setChecked(true);
            checkBox.setText(R.string.done);
        }
    }
}
