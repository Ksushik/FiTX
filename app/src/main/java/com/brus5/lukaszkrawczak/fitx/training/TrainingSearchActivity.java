package com.brus5.lukaszkrawczak.fitx.training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;

import java.util.ArrayList;

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
        loadInput();
        loadDefaultView();

        button();
        getIntentFromPreviousActiity();
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

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(TrainingSearchActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarTrainingSearchExercises);
        activityView.showBackButton();
    }

    private void button()
    {
        btRotate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (imageViewBodyBack.getVisibility() == View.INVISIBLE)
                {
                    TrainingSearchActivity.this.bodyRotate(1);
                }
                else if (imageViewBodyFront.getVisibility() == View.INVISIBLE)
                {
                    TrainingSearchActivity.this.bodyRotate(0);
                }
            }
        });
        btCardio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TrainingSearchActivity.this, CardioListActivity.class);
                intent.putExtra("dateFormat", dateFormat);
                TrainingSearchActivity.this.startActivity(intent);
            }
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
        Log.i(TAG, "dateFormat: " + DateGenerator.getSelectedDate());
    }
}



class TrainingSearchListAdapter extends ArrayAdapter<Training>
{
    int mResource;
    private Context mContext;

    public TrainingSearchListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Training> objects)
    {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @SuppressLint({"LongLogTag", "ViewHolder"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        int id = getItem(position).getId();
        String name = getItem(position).getName();
        double kcal = getItem(position).getKcal();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTrainingID = convertView.findViewById(R.id.trainingSearchID);
        TextView tvTrainingTitle = convertView.findViewById(R.id.trainingSearchTitle);
        TextView tvCalories = convertView.findViewById(R.id.textViewCalories);

        tvTrainingID.setText(String.valueOf(id));

        tvCalories.setText(String.valueOf(kcal));

        tvTrainingTitle.setText(name);

        return convertView;
    }

}