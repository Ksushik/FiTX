package com.brus5.lukaszkrawczak.fitx.training;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.IDefaultView;
import com.brus5.lukaszkrawczak.fitx.IPreviousActivity;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.async.HTTPService;
import com.brus5.lukaszkrawczak.fitx.converter.TimeStamp;
import com.brus5.lukaszkrawczak.fitx.diet.DietService;
import com.brus5.lukaszkrawczak.fitx.training.addons.Timer;
import com.brus5.lukaszkrawczak.fitx.training.addons.TimerGym;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;
import com.brus5.lukaszkrawczak.fitx.utils.ImageLoader;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;
import com.brus5.lukaszkrawczak.fitx.utils.SaveSharedPreference;
import com.brus5.lukaszkrawczak.fitx.validator.CharacterLimit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_TRAINING_DELETE;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_TRAINING_INSERT;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_TRAINING_SHOW;
import static com.brus5.lukaszkrawczak.fitx.utils.RestAPI.URL_TRAINING_UPDATE;

/**
 * In this class user can configure specific gym exercise where he can:
 * - see two images: concentrinc movment, excentric movment
 * - add next serie of exercise and add in each row number of repetitions and
 *   weight.
 * - configure timer and user also can start, pause and reset that timer
 * - write thoughts in notepad
 * - read "how to train" in the exercise description
 * - user can save or modify specific exercise
 * - user can delete exercise
 */
public class TrainingDetailsActivity extends AppCompatActivity implements View.OnClickListener, IDefaultView, IPreviousActivity
{
    private static final String TAG = "TrainingDetailsA";
    @SuppressLint("SimpleDateFormat")
    private String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    private String trainingTimeStamp, trainingTarget, previousActivity, newTimeStamp;
    private LinearLayout linearLayout;
    private int trainingID;
    private EditText etNotepad;
    private TextView tvName, tvCharsLeft;
    private CheckBox checkBox;
    private CharacterLimit characterLimit;
    private ScrollView scrollView;

    private TrainingInflater trainingInflater;
    private TimerGym timer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_4_details);
        loadInput();
        loadDefaultView();
        getIntentFromPreviousActiity();

        String urlL = RestAPI.SERVER_URL + "images/exercises/" + trainingTarget + "/" + trainingID + "_2" + ".jpg";
        String urlR = RestAPI.SERVER_URL + "images/exercises/" + trainingTarget + "/" + trainingID + "_1" + ".jpg";
        new ImageLoader(TrainingDetailsActivity.this, R.id.imageViewTraining, R.id.progressBarTrainingDetailsL, urlL);
        new ImageLoader(TrainingDetailsActivity.this, R.id.imageViewTraining1, R.id.progressBarTrainingDetailsR, urlR);

        startProvider(previousActivity);
        characterLimit = new CharacterLimit(etNotepad, tvCharsLeft, 280);
        etNotepad.addTextChangedListener(characterLimit);

        trainingInflater = new TrainingInflater(TrainingDetailsActivity.this);
        timer = new TimerGym(TrainingDetailsActivity.this);
    }


    /**
     * (Fragments provide their own onCreateOptionsMenu() callback).
     * In this method, you can inflate your menu resource (defined in XML)
     * into the Menu provided in the callback.
     * @param menu menu
     * @return initialize the contents of the Activity's standard options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_training_4_details, menu);

        MenuItem item = menu.findItem(R.id.menu_delete_exercise);
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

    /**
     * When you add items to the menu, you can implement the Activity's
     * onOptionsItemSelected(MenuItem) method to handle them there.
     * @param item Interface for direct access to a previously created menu item.
     * @return boolean Return item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            // Save exercise where user entered all needed data into the rows
            case R.id.menu_save_exercise:
                // Update exercise
                if (previousActivity.equals(TrainingActivity.class.getSimpleName()) && (trainingInflater.isValid()) && characterLimit.isLimit())
                {
                    Toast.makeText(this, R.string.training_updated, Toast.LENGTH_SHORT).show();

                    DietService service = new DietService(TrainingDetailsActivity.this);

                    HashMap<String, String> params = new HashMap<>();
                    params.put(RestAPI.DB_EXERCISE_ID, String.valueOf(trainingID));
                    params.put(RestAPI.DB_EXERCISE_DONE, String.valueOf(getChecked()));
                    params.put(RestAPI.DB_EXERCISE_REST_TIME, String.valueOf(Timer.START_TIME_IN_MILLIS));
                    params.put(RestAPI.DB_EXERCISE_REPS, trainingInflater.getReps());
                    params.put(RestAPI.DB_EXERCISE_WEIGHT, trainingInflater.getWeight());
                    params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(TrainingDetailsActivity.this)));
                    params.put(RestAPI.DB_EXERCISE_NOTEPAD, etNotepad.getText().toString());
                    params.put(RestAPI.DB_EXERCISE_DATE, newTimeStamp);

                    service.post(params, URL_TRAINING_UPDATE);

                    finish();
                }

                // Save exercise
                else if (previousActivity.equals(TrainingListActivity.class.getSimpleName()) && (trainingInflater.isValid()) && characterLimit.isLimit())
                {
                    Toast.makeText(this, R.string.training_inserted, Toast.LENGTH_SHORT).show();

                    DietService service = new DietService(TrainingDetailsActivity.this);

                    HashMap<String, String> params = new HashMap<>();
                    params.put(RestAPI.DB_EXERCISE_ID, String.valueOf(trainingID));
                    params.put(RestAPI.DB_EXERCISE_DONE, String.valueOf(getChecked()));
                    params.put(RestAPI.DB_EXERCISE_REST_TIME, String.valueOf(Timer.START_TIME_IN_MILLIS));
                    params.put(RestAPI.DB_EXERCISE_REPS, trainingInflater.getReps());
                    params.put(RestAPI.DB_EXERCISE_WEIGHT, trainingInflater.getWeight());
                    params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(TrainingDetailsActivity.this)));
                    params.put(RestAPI.DB_EXERCISE_NOTEPAD, etNotepad.getText().toString());
                    params.put(RestAPI.DB_EXERCISE_DATE, newTimeStamp);

                    service.post(params, URL_TRAINING_INSERT);

                    finish();
                }
                else
                {
                    DateGenerator cfg = new DateGenerator();
                    cfg.showError(TrainingDetailsActivity.this);
                }
                break;

            // Delete exercise
            case R.id.menu_delete_exercise:
                Toast.makeText(this, R.string.training_deleted, Toast.LENGTH_SHORT).show();

                DietService service = new DietService(TrainingDetailsActivity.this);

                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_EXERCISE_ID, String.valueOf(trainingID));
                params.put(RestAPI.DB_USER_ID_NO_PRIMARY_KEY, String.valueOf(SaveSharedPreference.getUserID(TrainingDetailsActivity.this)));
                params.put(RestAPI.DB_EXERCISE_DATE, trainingTimeStamp);

                service.post(params, URL_TRAINING_DELETE);

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Setting checkbox with automatically setted value:
     * - 0 - for not trained
     * - 1 - for trained
     * @param context actual context
     * @param i value of trained or not trained
     */
    private void onTrainingChangerListener(Context context, int i)
    {
        checkBox = ((Activity)context).findViewById(R.id.checkBox);

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


    /**
     * Loading setOnCheckedChangeListener to actual checkBox
     * Here user can see value of CheckBox with values:
     * - not trained
     * - trained
     */
    private void setOnCheckedChangeListener()
    {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    checkBox.setText(R.string.done);
                }
                else
                {
                    checkBox.setText(R.string.not_done);
                }
            }
        });
    }


    /**
     * Getting checkbox checked
     * @return 1 for trained, 0 for not trained
     */
    private int getChecked()
    {
        if (checkBox.isChecked())
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    /**
     * This method is responsible to start Providing data from DB
     * @param previousActivity previous activity in String
     */
    private void startProvider(String previousActivity)
    {
        if (previousActivity.equals( TrainingActivity.class.getSimpleName() ))
        {
            int userID = SaveSharedPreference.getUserID(this);
            String date = DateGenerator.getSelectedDate();

            // Glueing SERVER_URL params
            String params = "?user_id=" + userID + "&date=" + date + "&id=" + trainingID;

            new MyTraining(TrainingDetailsActivity.this).execute(URL_TRAINING_SHOW, params);
        }
        else if (previousActivity.equals( TrainingListActivity.class.getSimpleName() ))
        {
            String params = "?id=" + trainingID;

            new MyTraining(TrainingDetailsActivity.this).execute(URL_TRAINING_SHOW, params);

        }
    }

    @Override
    public void loadInput()
    {
        checkBox = findViewById(R.id.checkBox);
        linearLayout = findViewById(R.id.container);
        tvCharsLeft = findViewById(R.id.textViewCharsLeft);
        tvName = findViewById(R.id.textViewExerciseName);
        etNotepad = findViewById(R.id.editTextNotepad);
        etNotepad.clearFocus();
        etNotepad.didTouchFocusSelect();

        scrollView = findViewById(R.id.scrollViewTrainingActivityDetails);
        scrollView.requestFocus();
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(TrainingDetailsActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarTrainingExerciseShow);
        activityView.showBackButton();
    }

    @Override
    public void getIntentFromPreviousActiity()
    {
        Intent intent = getIntent();
        trainingID = intent.getIntExtra("trainingID", -1);
        trainingTarget = intent.getStringExtra("trainingTarget");
        trainingTimeStamp = intent.getStringExtra("trainingTimeStamp");
        // Make sure this method is below trainingTimeStamp = intent.getStringExtra("trainingTimeStamp");
        trainingTimeStamp = timeStampChanger(trainingTimeStamp);
        previousActivity = intent.getStringExtra("previousActivity");

        // Make sure this method is below
        TimeStamp time = new TimeStamp(DateGenerator.getSelectedDate(), trainingTimeStamp);
        newTimeStamp = time.getNewTimeStamp();
    }

    /**
     * This method is responsible for adding value for timeStamp/
     * if trainingTimeStamp is null then returning actual timeStamp
     * else
     * returning trainingTimeStamp which is attributed already.
     * @param trainingTimeStamp training timeStamp, can be null
     * @return changed timeStamp
     */
    private String timeStampChanger(String trainingTimeStamp)
    {
        if (trainingTimeStamp == null) return timeStamp;
        else return this.trainingTimeStamp;
    }

    /**
     * Automatic serie generator
     * @param seriesNumber how many series has been saved
     */
    private void seriesGenerator(int seriesNumber)
    {
        for (int i = 0; i < seriesNumber; i++)
        {
            linearLayout.addView(trainingInflater.trainingSetGenerator());
        }
    }

    /**
     * Creating new serie of gym exercise
     */
    private void nextSerie()
    {
        linearLayout.addView(trainingInflater.trainingSetGenerator());
    }

    /**
     * This method loading "show description text" after pressed button
     * @param context actual context
     */
    private void getTrainingDescAsynch(final Context context)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_TRAINING_DESCRIPTION, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.d(TAG, "onResponse: " + jsonObject.toString(1));


                    String description = "";

                    JSONArray server_response = jsonObject.getJSONArray("server_response");

                    for (int i = 0; i < server_response.length(); i++)
                    {
                        JSONObject object = server_response.getJSONObject(0);
                        description = object.getString(RestAPI.DB_EXERCISE_DESCRITION);
                    }
                    TrainingDetailsActivity.this.alertDialog(description);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onErrorResponse: Error" + error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_EXERCISE_ID, String.valueOf(trainingID));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(strRequest);
    }

    /**
     * Method with button responsibilities
     * @param view actual view
     */
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.buttonAddSeries:
                nextSerie();
                break;
            case R.id.buttonShowDescription:
                getTrainingDescAsynch(TrainingDetailsActivity.this);
                break;
        }
    }

    /**
     * Showing up alert dialog
     * @param string showed value
     */
    private void alertDialog(String string)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.description).setPositiveButton("Close", null).setMessage(string).show();
    }


    @SuppressLint("StaticFieldLeak")
    private class MyTraining extends HTTPService
    {
        private Context context;

        MyTraining(Context context)
        {
            super(context);
            this.context = context;
        }

        @Override
        public void onPostExecute(String s)
        {
            super.onPostExecute(s);

            try
            {
                JSONObject jsonObject = new JSONObject(s);
                Log.d(TAG, "onResponse: " + jsonObject.toString(1));

                int done = 0;
                int time = 0;
                String reps = "";
                String weight = "";
                String notepad = "";

                String exerciseName = "";
                String[] mReps_table = new String[]{};
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);
                    exerciseName = object.getString(RestAPI.DB_EXERCISE_NAME);
                }


                JSONArray trainings_info = jsonObject.getJSONArray("trainings_info");

                for (int i = 0; i < trainings_info.length(); i++)
                {
                    JSONObject tr_info = trainings_info.getJSONObject(i);
                    done = tr_info.getInt(RestAPI.DB_EXERCISE_DONE);
                    time = tr_info.getInt(RestAPI.DB_EXERCISE_REST_TIME);

                    // reps and weight variables contains RAW data from MySQL.
                    // Each dot in this String represents 1 serie.
                    // Example 12.10.8. <- last dot also represents serie
                    reps = tr_info.getString(RestAPI.DB_EXERCISE_REPS);

                    weight = tr_info.getString(RestAPI.DB_EXERCISE_WEIGHT);
                    notepad = tr_info.getString(RestAPI.DB_EXERCISE_NOTEPAD);

                    // This String removes dots from RAW String and preparing it
                    // to being inserted to String[]
                    String mReps = reps.replaceAll("\\p{Punct}", " ");

                    // This table is separating each number
                    mReps_table = mReps.split("\\s+");

                }

                Training t = new Training.Builder()
                        .name(exerciseName)
                        .done(done)
                        .reps(reps)
                        .weight(weight)
                        .notepad(notepad)
                        .time(time)
                        .sets(mReps_table.length)
                        .build();

                Log.d(TAG, "onPostExecute() called with: s = [" + t.getReps() + "] " + " [" + t.getWeight() + "] " + " [" + t.getSets() + "] ");


                tvName = ((Activity) context).findViewById(R.id.textViewExerciseName);
                etNotepad = ((Activity) context).findViewById(R.id.editTextNotepad);
                linearLayout = ((Activity) context).findViewById(R.id.container);

                tvName.setText(t.getName());
                etNotepad.setText(t.getNotepad());

                onTrainingChangerListener(context, t.getDone());

                trainingInflater.setReps(t.getReps());
                trainingInflater.setWeight(t.getWeight());
                seriesGenerator(t.getSets());
                timer.seekbar();

                timer.setSeekbarProgress(t.getTime());


            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }
}