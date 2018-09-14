package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.training.Training;
import com.brus5.lukaszkrawczak.fitx.training.TrainingDetailsActivity;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressLint({"LongLogTag", "Registered"})

public class StatsActivityInflater extends TrainingDetailsActivity
{
    private static final String TAG = "StatsActivityInflater";

    private List<Date> dateArrayList = new ArrayList<>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


    private Activity activity;
    private Context context;

    private GraphView graphView;

    public StatsActivityInflater(Activity activity, Context context, String response)
    {
        this.activity = activity;
        this.context = context;

        dataInflater(response);



    }

    private void dataInflater(String s)
    {
        graphView = activity.findViewById(R.id.grapViewStatistics);



        try
        {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");


            if (jsonArray.length() > 0)
            {

                ArrayList<String> resultArray = new ArrayList<>();
                ArrayList<String> dateArray = new ArrayList<>();


                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject j = jsonArray.getJSONObject(i);
                    String RESULT = j.getString("RESULT");
                    String date = j.getString("date");

                    resultArray.add(RESULT);
                    dateArray.add(date);
                }


                for (String r : dateArray)
                {
                    try
                    {
                        dateArrayList.add(simpleDateFormat.parse(r));
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                }



                LineGraphSeries<DataPoint> line = new LineGraphSeries<>(new DataPoint[]{});

                for (int i = dateArrayList.size(); i > 0; i--)
                {
                    Date x = dateArrayList.get(dateArrayList.size()-i);
                    int y = Integer.valueOf(resultArray.get(resultArray.size()-i));

                    line.appendData(new DataPoint(x,y),true, dateArrayList.size(), true);
                }

                graphView.addSeries(line);









//                Log.d(TAG, "dataInflater() called with: resultArray = [" + resultArray + "]");
//                Log.d(TAG, "dataInflater() called with: dateArray = [" + dateArray + "]");

            }
















        }
        catch (JSONException e)
        {
            Log.e(TAG, "dataInflater: ",e);
        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }


        graphView.getGridLabelRenderer().setHorizontalLabelsAngle(120);
        graphView.getGridLabelRenderer().setTextSize(25);
        graphView.getGridLabelRenderer().setLabelsSpace(20);
        graphView.getGridLabelRenderer().setLabelHorizontalHeight(80);
        graphView.getGridLabelRenderer().setHumanRounding(true);
        graphView.getGridLabelRenderer().setGridColor(Color.argb(255,204,204,204));
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(false);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(1.520114E12); //1.5185628E12
        graphView.getViewport().setMaxX(1.521714E12); // 1.5186492E12
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().scrollToEnd();




        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graphView.getContext()){
            @Override
            public String formatLabel(double value, boolean isValueX)
            {
                if(isValueX)
                {
                    return simpleDateFormat.format(new Date((long) value));

                            /*new SimpleDateFormat().format(new Date((long) value));*/
                }
                else
                    {
                    return super.formatLabel(value, false) + "\nkcal";
                    }

            }
        });
    }

}
