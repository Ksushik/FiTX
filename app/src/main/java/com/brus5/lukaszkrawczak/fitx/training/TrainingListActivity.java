package com.brus5.lukaszkrawczak.fitx.training;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrainingListActivity extends AppCompatActivity implements DefaultView
{
    private static final String TAG = "TrainingListActivity";
    private ArrayList<Training> trainingSearchArrayList = new ArrayList<>();
    private TrainingSearchListAdapter trainingSearchListAdapter;
    private ListView listViewTrainingActivity;
    private String trainingTarget, dateFormat;
    private DateGenerator cfg = new DateGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_3_listview);
        loadInput();
        loadDefaultView();
        getIntentFromPreviousActiity();
        asynchTask(TrainingListActivity.this);
        onListViewItemSelected();
    }

    @SuppressLint("LongLogTag")
    private void getIntentFromPreviousActiity()
    {
        Intent intent = getIntent();
        int mExercise = intent.getIntExtra("exercise", -1);
        TrainingList trainingList = new TrainingList(TrainingListActivity.this);
        trainingList.setResId(mExercise);
        trainingTarget = trainingList.getResourceName();
        dateFormat = intent.getStringExtra("dateFormat");
    }

    public void loadInput()
    {
        listViewTrainingActivity = findViewById(R.id.listViewTraining);
    }

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(TrainingListActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarTrainingExcerciseList);
        activityView.showBackButton();
    }

    private void asynchTask(final Context ctx)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_TRAINING_SEARCH_BY_TARGET, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {

                try
                {
                    /* Getting DietRatio from MySQL */
                    JSONObject jsonObject = new JSONObject(response);

                    Log.d(TAG, "onResponse: " + jsonObject.toString(1));

                    int trainingID;
                    String productName;

                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    if (jsonArray.length() > 0)
                    {
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            trainingID = object.getInt(RestAPI.DB_EXERCISE_ID);
                            productName = object.getString(RestAPI.DB_EXERCISE_NAME);

                            String trainingName = productName.substring(0, 1).toUpperCase() + productName.substring(1);

                            Training training = new Training(trainingID, trainingName);
                            trainingSearchArrayList.add(training);
                        }
                    }
                    /* End */

                    trainingSearchListAdapter = new TrainingSearchListAdapter(TrainingListActivity.this, R.layout.row_training_exercise_search, trainingSearchArrayList);
                    listViewTrainingActivity.setAdapter(trainingSearchListAdapter);
                    listViewTrainingActivity.invalidate();
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
                Toast.makeText(ctx, R.string.connection_error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onErrorResponse: Error" + error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                HashMap<String, String> params = new HashMap<>();
                params.put(RestAPI.DB_EXERCISE_TARGET, trainingTarget);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    private void onListViewItemSelected()
    {
        listViewTrainingActivity.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                TextView trainingID = view.findViewById(R.id.trainingSearchID);

                Intent intent = new Intent(TrainingListActivity.this, TrainingDetailsActivity.class);
                intent.putExtra("trainingID", Integer.valueOf(trainingID.getText().toString()));
                intent.putExtra("trainingTarget", trainingTarget);
                intent.putExtra("previousActivity", TrainingListActivity.class.getSimpleName());
                intent.putExtra("dateFormat", dateFormat);
                TrainingListActivity.this.startActivity(intent);
            }
        });
    }
}

class TrainingList
{
    Context mContext;
    private int resId;

    @SuppressLint("ResourceType")

    public TrainingList(Context context)
    {
        this.mContext = context;
    }

    public void setResId(int resId)
    {
        this.resId = resId;
    }

    String getResourceName()
    {
        String excerciseName = "";
        switch (resId)
        {
            case R.id.textViewButtonChest:
                excerciseName = "chest";
                break;
            case R.id.textViewButtonAbs:
                excerciseName = "abs";
                break;
            case R.id.textViewButtonQuads:
                excerciseName = "quads";
                break;
            case R.id.textViewButtonShoulders:
                excerciseName = "shoulders";
                break;
            case R.id.textViewButtonTraps:
                excerciseName = "traps";
                break;
            case R.id.textViewButtonLats:
                excerciseName = "lats";
                break;
            case R.id.textViewButtonGlutes:
                excerciseName = "glutes";
                break;
            case R.id.textViewButtonTriceps:
                excerciseName = "triceps";
                break;
            case R.id.textViewButtonHamstrings:
                excerciseName = "hamstrings";
                break;
            case R.id.textViewButtonCalves:
                excerciseName = "calves";
                break;
            case R.id.textViewButtonBiceps:
                excerciseName = "biceps";
                break;
            case R.id.textViewButtonForearms:
                excerciseName = "forearms";
                break;
        }
        return excerciseName;
    }

}