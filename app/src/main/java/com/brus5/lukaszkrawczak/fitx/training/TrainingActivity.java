package com.brus5.lukaszkrawczak.fitx.training;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.IDefaultView;
import com.brus5.lukaszkrawczak.fitx.MainActivity;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.async.ConnectionChecker;
import com.brus5.lukaszkrawczak.fitx.async.provider.Provider;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;
import com.brus5.lukaszkrawczak.fitx.utils.MyCalendar;

public class TrainingActivity extends AppCompatActivity implements IDefaultView
{
    TextView tvDate;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_1);
        loadInput();
        loadDefaultView();
        new MyCalendar(this, this, R.id.calendarViewTraining, listView);
        new ConnectionChecker(this).execute();
    }

    @Override
    public void loadInput()
    {
        tvDate = findViewById(R.id.textViewDate);
        listView = findViewById(R.id.listViewTraining);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(TrainingActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarTraining);
        activityView.showBackButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training_1_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_search_exercise:
                Intent intent = new Intent(TrainingActivity.this, TrainingSearchActivity.class);
                intent.putExtra("dateFormat", DateGenerator.getSelectedDate());
                TrainingActivity.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickCardioDetails(View view)
    {
        TrainingProvider trainingProvider = new TrainingProvider(TrainingActivity.this, view, TrainingActivity.class);
        trainingProvider.startCardioDetailsActivity();
    }

    public void onClickTrainingDetails(View view)
    {
        TrainingProvider trainingProvider = new TrainingProvider(TrainingActivity.this, view, TrainingActivity.class);
        trainingProvider.startTrainingDetailsActivity();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        new Provider(TrainingActivity.this, listView).load();
    }
}

class TrainingProvider
{
    private static final String TAG = "TrainingProvider";

    private View view;
    private Context context;

    private String previousActivity;
    private String dateFormat;

    /**
     * Constructor of Training Provider
     *
     * @param context pass actual context
     * @param view    pass view of listView
     */
    TrainingProvider(Context context, View view, Class<?> aClass)
    {
        this.context = context;
        this.view = view;

        previousActivity = aClass.getSimpleName();
        dateFormat = DateGenerator.getSelectedDate();

        Log.d(TAG, "TrainingProvider: \n" + "context: " + context.getClass() + "\n" + "view: " + view.getId() + "\n" + "class: " + aClass.getSimpleName());
    }

    /**
     * This method starts TrainingDetailsActivity with values of concrete TextView's:
     * trainingID, timeStamp, trainingTarget
     * and passing it to TrainingDetailsActivity where are grabbed by getIntent() method.
     */
    protected void startTrainingDetailsActivity()
    {
        TextView tvTrainingID = view.findViewById(R.id.trainingID);
        TextView tvTrainingTimeStamp = view.findViewById(R.id.trainingTimeStamp);
        TextView tvTrainingTarget = view.findViewById(R.id.trainingTarget);

        int trainingID = Integer.parseInt(tvTrainingID.getText().toString());
        String trainingTimeStamp = tvTrainingTimeStamp.getText().toString();
        String trainingTarget = tvTrainingTarget.getText().toString();

        Intent intent = new Intent(context, TrainingDetailsActivity.class);

        intent.putExtra("previousActivity", this.previousActivity);
        intent.putExtra("dateFormat", this.dateFormat);

        intent.putExtra("trainingID", trainingID);
        intent.putExtra("trainingTimeStamp", trainingTimeStamp);
        intent.putExtra("trainingTarget", trainingTarget);

        this.context.startActivity(intent);
    }

    /**
     * This method starts CardioDetailsActivity with values of concrete TextView's:
     * cardioID, timeStamp, cardioTime, caloriesBurnedPerMinute
     * and passing it to CardioDetailsActivity where are grabbed by getIntent() method.
     */
    protected void startCardioDetailsActivity()
    {

        TextView tvCardioID = view.findViewById(R.id.cardioID);
        TextView tvTimeStamp = view.findViewById(R.id.cardioTimeStamp);
        TextView tvTime = view.findViewById(R.id.cardioTime);
        TextView tvKcalPerMin = view.findViewById(R.id.cardioBurnPerMin);

        int trainingID = Integer.parseInt(tvCardioID.getText().toString());
        String trainingTimeStamp = tvTimeStamp.getText().toString();
        double kcalPerMin = Double.parseDouble(tvKcalPerMin.getText().toString());
        int cardioTime;


        if (tvTime.length() < 5)
        {
            cardioTime = Integer.valueOf("0" + tvTime.getText().toString().substring(0, 1));
        }
        else
        {
            cardioTime = Integer.valueOf(tvTime.getText().toString().substring(0, 2));
        }

        Intent intent = new Intent(context, CardioDetailsActivity.class);

        intent.putExtra("previousActivity", this.previousActivity);
        intent.putExtra("dateFormat", this.dateFormat);

        intent.putExtra("trainingID", trainingID);
        intent.putExtra("trainingTimeStamp", trainingTimeStamp);
        intent.putExtra("trainingTime", cardioTime);
        intent.putExtra("kcalPerMin", kcalPerMin);

        this.context.startActivity(intent);
    }
}