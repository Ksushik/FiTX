package com.brus5.lukaszkrawczak.fitx.Training;

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.zip.Inflater;

public class TrainingInflater extends Inflater {

    private static final String TAG = "TrainingInflater";

    private Context ctx;
    @SuppressLint("UseSparseArrays")
    private Map<Integer,String> mapWeight = new HashMap<>();
    @SuppressLint("UseSparseArrays")
    private Map<Integer,String> mapReps = new HashMap<>();
    private String reps;
    private String weight;

    private boolean valid;

    private int clickCounter = 0;

    TrainingInflater(Context ctx) {
        this.ctx = ctx;
    }

    public void setMapWeight(int i, String weight) {
        this.mapWeight.put(i,weight);
    }

    public void setMapReps(int i, String reps) {
        this.mapReps.put(i, reps);
    }

    public View generateTrainingSets(){
        LayoutInflater layoutInflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.training_details_add_row, null);
        TextView textViewTrainingDetailsID = addView.findViewById(R.id.textViewTrainingDetailsID);

        final EditText editTextTrainingRowReps = addView.findViewById(R.id.editTextTrainingRowReps);
        editTextTrainingRowReps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                final TextView textViewNoEmpty = addView.findViewById(R.id.textViewNoEmpty);

                if (s.length() == 0) {
                    textViewNoEmpty.setVisibility(View.VISIBLE);
                    mapReps.remove(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
                    setReps();
                    valid = false;
                }

                else if (s.length() > 3){
                    editTextTrainingRowReps.getText().clear();
                    valid = false;
                }

                else{
                    textViewNoEmpty.setVisibility(View.INVISIBLE);
                    mapReps.put(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()),s.toString());
                    setReps();
                    valid = true;
                }

            }
        });

        final EditText editTextTrainingRowWeight = addView.findViewById(R.id.editTextTrainingRowWeight);
        editTextTrainingRowWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                final TextView textViewNoEmpty1 = addView.findViewById(R.id.textViewNoEmpty1);

                if (s.length() == 0){
                    textViewNoEmpty1.setVisibility(View.VISIBLE);
                    mapWeight.remove(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
                    setWeight();
                    valid = false;
                }

                else if (s.length() > 3){
                    editTextTrainingRowWeight.getText().clear();
                    valid = false;
                }


                else {
                    textViewNoEmpty1.setVisibility(View.INVISIBLE);
                    mapWeight.put(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()),s.toString());
                    setWeight();
                    valid = true;
                }
            }
        });

        Button buttonRemove = addView.findViewById(R.id.buttonTrainingRowRemove);
        buttonRemove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                mapReps.remove(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
                mapWeight.remove(Integer.valueOf(((TextView)addView.findViewById(R.id.textViewTrainingDetailsID)).getText().toString()));
                setReps();
                setWeight();
                ((LinearLayout)addView.getParent()).removeView(addView);

                Log.i(TAG, "onClick: " + mapReps);
                Log.i(TAG, "onClick: " + mapWeight);

            }});

        textViewTrainingDetailsID.setText(String.valueOf(clickCounter));
        editTextTrainingRowReps.setText(mapReps.get(clickCounter));
        editTextTrainingRowWeight.setText(mapWeight.get(clickCounter));

        Log.i(TAG, "onClick: reps: " + ((EditText)addView.findViewById(R.id.editTextTrainingRowReps)).getText().toString() + " weight: " + ((EditText)addView.findViewById(R.id.editTextTrainingRowWeight)).getText().toString());
        Log.i(TAG, "onClick: " + mapReps);
        Log.i(TAG, "onClick: " + mapWeight);
        clickCounter++;

        return addView;
    }

    public void setReps() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mapReps.size(); i++) {
            stringBuilder.append(mapReps.get(i));
            stringBuilder.append(".");
        }
        this.reps = stringBuilder.toString();
        Log.i(TAG, "onClick: " + this.reps);
        Log.i(TAG, "onClick: " + mapReps);
    }

    public void setWeight() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mapWeight.size(); i++) {
            stringBuilder.append(mapWeight.get(i));
            stringBuilder.append(".");
        }
        this.weight = stringBuilder.toString();
        Log.i(TAG, "onClick: " + this.weight);
        Log.i(TAG, "onClick: " + mapWeight);
    }

    public String getReps() {
        return reps;
    }

    public String getWeight() {
        return weight;
    }

    public boolean isValid() {
        if (this.weight.contains("null")) return false;
        return valid;
    }

    // FIXME: 18.07.2018 EDITTEXT AFTER CREATION MUST SHOW "0"
    
}