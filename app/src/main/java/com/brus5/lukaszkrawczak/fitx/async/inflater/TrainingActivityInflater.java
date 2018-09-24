package com.brus5.lukaszkrawczak.fitx.async.inflater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.training.Training;
import com.brus5.lukaszkrawczak.fitx.training.TrainingInflater;
import com.brus5.lukaszkrawczak.fitx.utils.RestAPI;
import com.brus5.lukaszkrawczak.fitx.utils.StringConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class TrainingActivityInflater
{
    private static final String TAG = "MainActivityInflater";

    private Context context;
    private ListView listView;
    private TrainingAdapter adapter;
    private ArrayList<Training> list = new ArrayList<>();

    public TrainingActivityInflater(Context context, ListView listView, String response)
    {
        this.context = context;
        this.listView = listView;

        dataInflater(response);
    }

    private void dataInflater(String s)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(s);
            Log.d(TAG, "onResponse: " + jsonObject.toString(1));

            JSONArray training_types = jsonObject.getJSONArray("training_types");

            JSONArray trainings_info = jsonObject.getJSONArray("trainings_info");

            int id;
            String name;
            String target;

            int restTime;
            int done;
            String reps;
            String weight;
            String date;

            if (trainings_info.length() > 0)
            {
                for (int i = 0; i < trainings_info.length(); i++)
                {
                    JSONObject trainings_infoObj = trainings_info.getJSONObject(i);

                    restTime = trainings_infoObj.getInt(RestAPI.DB_EXERCISE_REST_TIME);
                    done = trainings_infoObj.getInt(RestAPI.DB_EXERCISE_DONE);
                    reps = trainings_infoObj.getString(RestAPI.DB_EXERCISE_REPS);
                    weight = trainings_infoObj.getString(RestAPI.DB_EXERCISE_WEIGHT);
                    date = trainings_infoObj.getString(RestAPI.DB_EXERCISE_DATE);


                    JSONObject server_responseObj = training_types.getJSONObject(i);

                    id = server_responseObj.getInt(RestAPI.DB_EXERCISE_ID);
                    name = server_responseObj.getString(RestAPI.DB_EXERCISE_NAME);
                    target = server_responseObj.getString(RestAPI.DB_EXERCISE_TARGET);

                    //                    Training training = new Training(id, done, name, restTime, weight, reps, date, target, 1);

                    Training t = new Training.Builder().viewType(1).id(id).done(done).name(name).time(restTime).weight(weight).reps(reps).timeStamp(date).target(target).build();

                    list.add(t);
                    adapter = new TrainingAdapter(context, R.layout.row_training_excercise, list);

                }


            }


        } catch (JSONException e)
        {
            e.printStackTrace();
        }


        try
        {
            JSONObject jsonObject = new JSONObject(s);
            Log.d(TAG, "onResponse: " + jsonObject.toString(1));

            JSONArray cardio_types = jsonObject.getJSONArray("cardio_types");

            JSONArray cardio_info = jsonObject.getJSONArray("cardio_info");

            int id;
            String name;
            String kcalPerMin;

            int done;
            int time;
            String date;

            if (cardio_info.length() > 0)
            {
                for (int i = 0; i < cardio_info.length(); i++)
                {
                    JSONObject cardio = cardio_info.getJSONObject(i);

                    done = cardio.getInt(RestAPI.DB_CARDIO_DONE);
                    time = cardio.getInt(RestAPI.DB_CARDIO_TIME);
                    date = cardio.getString(RestAPI.DB_DATE);


                    JSONObject cardioType = cardio_types.getJSONObject(i);

                    id = cardioType.getInt(RestAPI.DB_CARDIO_ID);
                    name = cardioType.getString(RestAPI.DB_CARDIO_NAME);
                    kcalPerMin = cardioType.getString(RestAPI.DB_CARDIO_CALORIES);

                    Training t = new Training.Builder().viewType(2).id(id).done(done).name(name).time(time).timeStamp(date).kcalPerMin(kcalPerMin).build();

                    list.add(t);
                    adapter = new TrainingAdapter(context, R.layout.row_training_excercise, list);
                }
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        listView.setAdapter(adapter);
        listView.invalidate();
    }


    class TrainingAdapter extends ArrayAdapter<Training>
    {
        private static final String TAG = "TrainingAdapter";
        private static final int KG_ONE_TONE = 1000;
        int mResource;
        private Context mContext;

        public TrainingAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Training> objects)
        {
            super(context, resource, objects);
            mContext = context;
            mResource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            int viewType = getItem(position).getViewType();

            int id = getItem(position).getId();
            int done = getItem(position).getDone();
            String name = getItem(position).getName();
            int restTime = getItem(position).getRestTime();
            String weight = getItem(position).getWeight();
            String reps = getItem(position).getReps();
            String timeStamp = getItem(position).getTimeStamp();
            String target = getItem(position).getTarget();

            int time = getItem(position).getTime();
            String kcalPerMin = getItem(position).getKcalPerMin();



            /*training = new Training(id, done, name, time, date, kcalPerMin, 2);*/

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);


            if (viewType == 1)
            {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_training_excercise, parent, false);

                CheckBox checkBox = convertView.findViewById(R.id.trainingExcerciseCheckBox);
                TextView tvId = convertView.findViewById(R.id.trainingID);
                TextView tvName = convertView.findViewById(R.id.trainingExcerciseTitle);
                TextView tvRest = convertView.findViewById(R.id.trainingExcerciseRestTime);
                TextView tvTimeStamp = convertView.findViewById(R.id.trainingTimeStamp);
                TextView tvTarget = convertView.findViewById(R.id.trainingTarget);
                TextView tvSeriesNum = convertView.findViewById(R.id.textViewSeriesNum);
                TextView tvLifted = convertView.findViewById(R.id.textViewSumLiftedWeight);
                TextView tvWeightType = convertView.findViewById(R.id.textViewWeightType);

                if (done == 1)
                {
                    checkBox.setChecked(true);
                }
                else
                {
                    checkBox.setChecked(false);
                }

                tvId.setText(String.valueOf(id));
                tvName.setText(StringConverter.toUpperFirstLetter(name));
                tvTimeStamp.setText(timeStamp);
                tvTarget.setText(target);

                int minutes = time / 1000 / 60;
                int seconds = time / 1000 % 60;

                String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                Log.i(TAG, "tvRest: " + tvRest.getText().toString() + "\n" + "time: " + time + "\n" + "timeLeftFormatted: " + timeLeftFormatted);
                tvRest.setText(timeLeftFormatted);

                TrainingInflater trainingInflater = new TrainingInflater(mContext);
                trainingInflater.setReps(reps);
                trainingInflater.setWeight(weight);

                tvSeriesNum.setText(String.valueOf(trainingInflater.getSetNumber()));

                double mWeight = trainingInflater.countLiftedWeight();
                double toneConverter;

                if (mWeight < KG_ONE_TONE)
                {
                    tvLifted.setText(String.valueOf(trainingInflater.countLiftedWeight()));
                    tvWeightType.setText(R.string.kg_short);
                }
                else
                {
                    toneConverter = mWeight / KG_ONE_TONE;
                    String value = String.format(Locale.getDefault(), "%.2f", toneConverter);
                    tvLifted.setText(value);
                    tvWeightType.setText(R.string.t_short);
                }

                return convertView;

            }


            if (viewType == 2)
            {

                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_training_cardio, parent, false);

                CheckBox checkBox = convertView.findViewById(R.id.cardioCheckBox);
                TextView tvId = convertView.findViewById(R.id.cardioID);
                TextView tvName = convertView.findViewById(R.id.cardioTitle);
                TextView tvTimeStamp = convertView.findViewById(R.id.cardioTimeStamp);
                TextView tvTime = convertView.findViewById(R.id.cardioTime);
                TextView tvKcalBurned = convertView.findViewById(R.id.cardioKcalBurned);
                TextView tvKcalPerMin = convertView.findViewById(R.id.cardioBurnPerMin);


                if (done == 1)
                {
                    checkBox.setChecked(true);
                }
                else
                {
                    checkBox.setChecked(false);
                }

                tvId.setText(String.valueOf(id));

                tvName.setText(StringConverter.toUpperFirstLetter(name));

                tvTimeStamp.setText(timeStamp);

                StringBuilder builder = new StringBuilder();
                builder.append(time);
                builder.append(":");
                builder.append("00");

                tvTime.setText(builder);

                double iBurned = Integer.valueOf(time) * Double.parseDouble(kcalPerMin);
                String burned = String.format(Locale.getDefault(), "%.0f", iBurned);


                tvKcalBurned.setText(String.valueOf(burned));

                tvKcalPerMin.setText(kcalPerMin);

                Log.e(TAG, "tvId: " + tvId.getText().toString());
                Log.e(TAG, "tvName: " + tvName.getText().toString());


                return convertView;
            }

            return convertView;

        }
    }


}



