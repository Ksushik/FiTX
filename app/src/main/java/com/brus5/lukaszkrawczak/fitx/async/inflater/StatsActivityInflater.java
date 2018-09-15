package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.training.TrainingDetailsActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressLint({"LongLogTag", "Registered"})

public class StatsActivityInflater extends TrainingDetailsActivity
{
    private static final String TAG = "StatsActivityInflater";

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

                // Setting line to graph for consumed calories
                setLineGraphSeries(convertStringsToDate(dateArray),resultArray,context.getString(R.string.calories_consumed),Color.argb(255,51,153,255));

                // Configuring view style
                setViewStyle();

                // Setting X axis and Y axis titles and formats
                setLabelFormatter();
            }


            JSONArray arrayLimit = jsonObject.getJSONArray("calories_limit");

            if (arrayLimit.length() > 0)
            {
                ArrayList<String> resultArray = new ArrayList<>();
                ArrayList<String> dateArray = new ArrayList<>();

                for (int i = 0; i < arrayLimit.length(); i++)
                {
                    JSONObject j = arrayLimit.getJSONObject(i);
                    String RESULT = j.getString("RESULT");
                    String date = j.getString("date");

                    resultArray.add(RESULT);
                    dateArray.add(date);
                }

                // Setting line to graph for consumed calories
                setLineGraphSeries(convertStringsToDate(dateArray),resultArray,context.getString(R.string.calories_limit),Color.argb(255,242, 0, 86));

                // Configuring view style
                setViewStyle();

                // Setting X axis and Y axis titles and formats
                setLabelFormatter();
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
    }


    /**
     * Adding series to graph
     * @param list array of Date
     * @param array array of results
     * @param string name of lineGraph
     */
    private void setLineGraphSeries(List<Date> list, ArrayList<String> array, String string, int color)
    {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{});

        for (int i = array.size(); i > 0; i--)
        {
            Date x = list.get(array.size()-i);
            int y = Integer.valueOf(array.get(array.size()-i));

            series.appendData(new DataPoint(x,y),false, list.size()+10, true);
            series.setTitle(string);
        }

        series.setColor(color);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(8);
        series.setThickness(5);

        graphView.addSeries(series);
    }


    /**
     * Converting String date to Date format
     * @param dateArray date array pass
     */
    private List<Date> convertStringsToDate(ArrayList<String> dateArray)
    {
        List<Date> dateArrayList = new ArrayList<>();
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
        Log.i(TAG, "convertStringsToDate: " + String.valueOf(dateArray.get(dateArray.size()-1)));
        /*
        String dt = "2008-01-01";  // Start date
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
Calendar c = Calendar.getInstance();
c.setTime(sdf.parse(dt));
c.add(Calendar.DATE, 1);  // number of days to add
dt = sdf.format(c.getTime());  // dt is now the new date
        * */
        Calendar c = Calendar.getInstance();
        try
        {
            c.setTime(simpleDateFormat.parse(dateArray.get(dateArray.size()-1)));
            c.add(Calendar.DATE,5);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        dateArrayList.add(c.getTime());

//        for (int i = 0; i < 5; i++) {
//
//        }
        return dateArrayList;
    }

    /**
     * Configuring view style
     */
    private void setViewStyle()
    {
        graphView.getGridLabelRenderer().setVerticalAxisTitle(context.getString(R.string.calories));
        graphView.getGridLabelRenderer().setHorizontalLabelsAngle(130);
        graphView.getGridLabelRenderer().setTextSize(25);
        graphView.getGridLabelRenderer().setLabelsSpace(15);
        graphView.getGridLabelRenderer().setLabelHorizontalHeight(150);
        graphView.getGridLabelRenderer().setHumanRounding(true);
        graphView.getGridLabelRenderer().setGridColor(Color.argb(255,204,204,204));

        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(false);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(1.520114E12); //1.5185628E12
        graphView.getViewport().setMaxX(1.521714E12); // 1.5186492E12
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollable(true);
//        graphView.getViewport().scrollToEnd();

        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }


    /**
     * Setting X axis and Y axis titles and formats
     */
    private void setLabelFormatter()
    {
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graphView.getContext()){
            @Override
            public String formatLabel(double value, boolean isValueX)
            {
                if(isValueX)
                {
                    return simpleDateFormat.format(new Date((long) value));
                }
                else
                {
                    return super.formatLabel(value, false) ;
                }
            }
        });
    }

}
