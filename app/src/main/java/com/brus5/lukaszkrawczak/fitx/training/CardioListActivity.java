package com.brus5.lukaszkrawczak.fitx.training;

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
import com.brus5.lukaszkrawczak.fitx.RestAPI;
import com.brus5.lukaszkrawczak.fitx.utils.ActivityView;
import com.brus5.lukaszkrawczak.fitx.utils.DateGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardioListActivity extends AppCompatActivity implements DefaultView
{
    private static final String TAG = "CardioListActivity";
    private ArrayList<Training> list = new ArrayList<>();
    private TrainingSearchListAdapter adapter;
    private ListView listView;
    private String dateFormat;
    private DateGenerator cfg = new DateGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_3_listview);
        loadInput();
        loadDefaultView();

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

    @Override
    public void loadDefaultView()
    {
        ActivityView activityView = new ActivityView(CardioListActivity.this, getApplicationContext(), this);
        activityView.statusBarColor(R.id.toolbarCardioList);
        activityView.showBackButton();
    }


    private void asynchTask(final Context ctx)
    {
        StringRequest strRequest;
        strRequest = new StringRequest(Request.Method.POST, RestAPI.URL_CARDIO_GET_LIST, new Response.Listener<String>()
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
                    double calories;

                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    if (jsonArray.length() > 0)
                    {
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);

                            trainingID = object.getInt(RestAPI.DB_CARDIO_ID);
                            productName = object.getString(RestAPI.DB_CARDIO_NAME);
                            calories = object.getDouble(RestAPI.DB_CARDIO_CALORIES);

                            String trainingName = productName.substring(0, 1).toUpperCase() + productName.substring(1);

                            Training training = new Training(trainingID, trainingName, calories);
                            list.add(training);
                        }
                    }
                    /* End */

                    adapter = new TrainingSearchListAdapter(CardioListActivity.this, R.layout.row_training_exercise_search, list);
                    listView.setAdapter(adapter);
                    listView.invalidate();
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
                Toast.makeText(ctx, RestAPI.CONNECTION_INTERNET_FAILED, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onErrorResponse: Error" + error);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(ctx);
        queue.add(strRequest);
    }

    private void onListViewItemSelected()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                TextView tvTrainingID = view.findViewById(R.id.trainingSearchID);

                Intent intent = new Intent(CardioListActivity.this, CardioDetailsActivity.class);
                intent.putExtra("trainingID", Integer.valueOf(tvTrainingID.getText().toString()));
                intent.putExtra("previousActivity", CardioListActivity.class.getSimpleName());
                intent.putExtra("dateFormat", dateFormat);

                CardioListActivity.this.startActivity(intent);
            }
        });
    }
}