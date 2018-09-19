package com.brus5.lukaszkrawczak.fitx.training;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brus5.lukaszkrawczak.fitx.R;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class TrainingInflater
{
    private static final String TAG = "TrainingInflater";
    private Context ctx;
    @SuppressLint("UseSparseArrays")
    private Map<Integer, String> mapWeight = new HashMap<>();
    @SuppressLint("UseSparseArrays")
    private Map<Integer, String> mapReps = new HashMap<>();
    private String reps;
    private String weight;
    private boolean valid;
    private int clickCounter = 0;
    private int rowsNum = 0;

    public TrainingInflater(Context ctx)
    {
        this.ctx = ctx;
    }

    /**
     * This method is responsible for generating new rows in TrainingDetailsActivity
     * @return View of the generated method
     */
    public View trainingSetGenerator()
    {
        // You can trace the LayoutInflater returned by getLayoutInflater() to LayoutInflater.from()
        // and you can see this is just a shortcut for getSystemService()
        LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate a new view hierarchy from the specified xml resource. Throws
        // where null is the parent layout where you want to add a child layout.
        final View addView = layoutInflater.inflate(R.layout.row_training_details_add, null);

        // Finding Views from addView
        TextView textViewTrainingDetailsID = addView.findViewById(R.id.textViewTrainingDetailsID);

        // Adding EditText responsible for Reps
        final EditText editTextTrainingRowReps = addView.findViewById(R.id.editTextTrainingRowReps);
        editTextTrainingRowReps.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}

            @Override
            public void afterTextChanged(final Editable s)
            {
                final TextView textViewNoEmpty = addView.findViewById(R.id.textViewNoEmpty);

                if (s.length() == 0)
                {
                    textViewNoEmpty.setVisibility(View.VISIBLE);
                    mapReps.remove(Integer.valueOf(((TextView) addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
                    repsConverter();
                    valid = false;
                }
                else if (s.length() > 3)
                {
                    editTextTrainingRowReps.getText().clear();
                    valid = false;
                }
                else
                {
                    textViewNoEmpty.setVisibility(View.INVISIBLE);
                    mapReps.put(Integer.valueOf(((TextView) addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()), s.toString());
                    repsConverter();
                    valid = true;
                }

            }
        });

        // Adding EditText responsible for Weight
        final EditText editTextTrainingRowWeight = addView.findViewById(R.id.editTextTrainingRowWeight);
        editTextTrainingRowWeight.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(final Editable s)
            {
                final TextView textViewNoEmpty1 = addView.findViewById(R.id.textViewNoEmpty1);

                if (s.length() == 0)
                {
                    textViewNoEmpty1.setVisibility(View.VISIBLE);
                    mapWeight.remove(Integer.valueOf(((TextView) addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
                    weightConverter();
                    valid = false;
                }
                else if (s.length() > 3)
                {
                    editTextTrainingRowWeight.getText().clear();
                    valid = false;
                }
                else
                {
                    textViewNoEmpty1.setVisibility(View.INVISIBLE);
                    mapWeight.put(Integer.valueOf(((TextView) addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()), s.toString());
                    weightConverter();
                    valid = true;
                }
            }
        });

        // Adding Delete Button responsible for deleting actual View
        Button buttonRemove = addView.findViewById(R.id.buttonTrainingRowRemove);
        buttonRemove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                mapReps.remove(Integer.valueOf(((TextView) addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
                mapWeight.remove(Integer.valueOf(((TextView) addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
                ((LinearLayout) addView.getParent()).removeView(addView);
                TrainingInflater.this.repsConverter();
                TrainingInflater.this.weightConverter();

                rowsNum--;

                Log.i(TAG, "onClick: mapReps: " + mapReps + " size: " + mapReps.size() + " mapWeight: " + mapWeight + " size: " + mapWeight.size() + " rowsNum: " + rowsNum);

            }
        });

        textViewTrainingDetailsID.setText(String.valueOf(clickCounter));
        editTextTrainingRowReps.setText(mapReps.get(clickCounter));
        editTextTrainingRowWeight.setText(mapWeight.get(clickCounter));

        clickCounter++;
        rowsNum++;

        Log.i(TAG, "onClick: mapReps: " + mapReps + " size: " + mapReps.size() + " mapWeight: " + mapWeight + " size: " + mapWeight.size() + " rowsNum: " + rowsNum + " reps: " + ((EditText) addView.findViewById(R.id.editTextTrainingRowReps)).getText().toString() + " weight: " + ((EditText) addView.findViewById(R.id.editTextTrainingRowWeight)).getText().toString());

        return addView;
    }

    private void insertWeight(int i, String weight)
    {
        this.mapWeight.put(i, weight);
        weightConverter();
    }

    private void insertReps(int i, String reps)
    {
        this.mapReps.put(i, reps);
        repsConverter();
    }

    public void setWeight(String weight)
    {
        String s = weight.replaceAll("\\p{Punct}", " ");
        String[] strings = s.split("\\s+");
        for (int i = 0; i < strings.length; i++)
        {
            insertWeight(i, strings[i]);
        }
        weightConverter();
    }

    public void setReps(String reps)
    {
        String s = reps.replaceAll("\\p{Punct}", " ");
        String[] strings = s.split("\\s+");
        for (int i = 0; i < strings.length; i++)
        {
            insertReps(i, strings[i]);
        }
        repsConverter();
    }

    private void repsConverter()
    {
        StringBuilder builder = new StringBuilder();
        String[] map = mapReps.values().toArray(new String[0]);
        for (String s : map)
        {
            builder.append(s);
            builder.append(".");
        }
        this.reps = builder.toString();
    }

    private void weightConverter()
    {
        StringBuilder builder = new StringBuilder();
        String[] map = mapWeight.values().toArray(new String[0]);
        for (String s : map)
        {
            builder.append(s);
            builder.append(".");
        }
        this.weight = builder.toString();
    }

    public String getReps()
    {
        return reps;
    }

    public String getWeight()
    {
        return weight;
    }

    public boolean isValid()
    {
        return (mapReps.size() == rowsNum) && (mapWeight.size() == rowsNum) && this.weight != null && this.reps != null;
    }

    public String printResult()
    {
        return "\n" + "this.reps: " + this.reps + " mapWeight: " + mapReps + " size: " + mapReps.size() + "\n" + "this.weight: " + this.weight + " mapWeight: " + mapWeight + " size: " + mapWeight.size();
    }

    public int getSetNumber()
    {
        return mapReps.size();
    }

    public int countLiftedWeight()
    {
        int rep;
        int weight;
        int lifted = 0;

        int[] table = new int[mapReps.size()];

        for (int i = 0; i < mapReps.size(); i++)
        {
            rep = Integer.valueOf(mapReps.get(i));
            weight = Integer.valueOf(mapWeight.get(i));

            lifted = rep * weight;
            table[i] = lifted;
        }

        lifted = 0;
        for (int i : table)
        {
            lifted += i;
        }
        return lifted;
    }

    public int countRepsTime(String reps)
    {
        final int REP_TIME = 3;

        // This String removes dots from RAW String and preparing it
        // to being inserted to String[]
        String mReps = reps.replaceAll("\\p{Punct}", " ");

        // This table is separating each number
        String[] strings = mReps.split("\\s+");

        int[] iReps = new int[strings.length];


        int k = 0;

        for (String string : strings)
        {
            iReps[k] = Integer.valueOf(string);
            k++;
        }


        int time = 0;

        for (int i = 0; i < iReps.length; i++)
        {
            time += iReps[i] * REP_TIME;
        }

        return time;
    }

}