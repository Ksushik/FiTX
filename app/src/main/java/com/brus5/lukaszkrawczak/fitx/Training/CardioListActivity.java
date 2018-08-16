package com.brus5.lukaszkrawczak.fitx.Training;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.brus5.lukaszkrawczak.fitx.DefaultView;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.RestAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardioListActivity extends AppCompatActivity implements DefaultView
{
    private static final String TAG = "CardioListActivity";
    private ArrayList<TrainingSearch> list = new ArrayList<>();
    private TrainingSearchListAdapter adapter;
    private ListView listView;
    private String dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_3_listview);
        loadInput();
        changeStatusBarColor();
        onBackButtonPressed();
        getIntentFromPreviousActiity();
        asynchTask(CardioListActivity.this);
        onListViewItemSelected();
    }

    private void getIntentFromPreviousActiity()
    {
        Intent intent = getIntent();
        dateFormat = intent.getStringExtra("dateFormat");
    }

    public void loadInput()
    {
        listView = findViewById(R.id.listViewTraining);
    }

    public void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(CardioListActivity.this, R.color.colorPrimaryDark));
        }
        Toolbar toolbar = findViewById(R.id.toolbarCardioList);
        setSupportActionBar(toolbar);
    }

    private void onBackButtonPressed()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void asynchTask(final Context ctx)
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_CARDIO_GET_LIST, response -> {
            try
            {
                /* Getting DietRatio from MySQL */
                JSONObject jsonObject = new JSONObject(response);

                Log.d(TAG, "onResponse: " + jsonObject.toString(1));

                int trainingID;
                String productName;
                double calories;

                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                if (jsonArray.length() > 0)
                {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);

                        trainingID =        object.getInt(RestAPI.DB_CARDIO_ID);
                        productName =       object.getString(RestAPI.DB_CARDIO_NAME);
                        calories =          object.getDouble(RestAPI.DB_CARDIO_CALORIES);

                        String trainingName = productName.substring(0, 1).toUpperCase() + productName.substring(1);

                        TrainingSearch trainingSearch = new TrainingSearch(trainingID, trainingName, calories);
                        list.add(trainingSearch);
                    }
                }
                /* End */

                adapter = new TrainingSearchListAdapter(CardioListActivity.this, R.layout.row_training_exercise_search, list);
                listView.setAdapter(adapter);
                listView.invalidate();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(ctx, RestAPI.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onErrorResponse: Error" + error);
        });
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    private void onListViewItemSelected()
    {
        listView.setOnItemClickListener((parent, view, position, id) -> {

            TextView tvTrainingID = view.findViewById(R.id.trainingSearchID);

            Intent intent = new Intent(CardioListActivity.this, CardioDetailsActivity.class);
            intent.putExtra("trainingID",       Integer.valueOf(tvTrainingID.getText().toString()));
            intent.putExtra("previousActivity", CardioListActivity.class.getSimpleName());
            intent.putExtra("dateFormat",       dateFormat);

            startActivity(intent);
        });
    }
}