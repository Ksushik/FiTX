package com.brus5.lukaszkrawczak.fitx.DTO;

import android.util.Log;

public abstract class TrainingStatusDTO
{
    private static final String TAG = "TrainingStatusDTO";
    private String trainingID;
    private String trainingDone;
    private String trainingRestTime;
    private String trainingReps;
    private String trainingWeight;
    private String userName;
    private String trainingTimeStamp;
    private String trainingNotepad;
    private String trainingDate;
    private String userID;
    private String trainingTime;

    public TrainingStatusDTO()
    {
    }

    public TrainingStatusDTO(String trainingID, String trainingDone, String trainingRestTime, String trainingReps, String trainingWeight, String userName, String userID, String trainingTimeStamp, String trainingNotepad, String trainingDate, String trainingTime)
    {
        this.trainingID = trainingID;
        this.trainingDone = trainingDone;
        this.trainingRestTime = trainingRestTime;
        this.trainingReps = trainingReps;
        this.trainingWeight = trainingWeight;
        this.userName = userName;
        this.userID = userID;
        this.trainingTimeStamp = trainingTimeStamp;
        this.trainingNotepad = trainingNotepad;
        this.trainingDate = trainingDate;
        this.trainingTime = trainingTime;


        if (this.trainingID != null)
        {
            Log.i(TAG, "trainingID: " + this.trainingID);
        }
        if (this.trainingDone != null)
        {
            Log.i(TAG, "trainingDone: " + this.trainingDone);
        }
        if (this.trainingRestTime != null)
        {
            Log.i(TAG, "trainingRestTime: " + this.trainingRestTime);
        }
        if (this.trainingReps != null)
        {
            Log.i(TAG, "trainingReps: " + this.trainingReps);
        }
        if (this.trainingWeight != null)
        {
            Log.i(TAG, "trainingWeight: " + this.trainingWeight);
        }
        if (this.userName != null)
        {
            Log.i(TAG, "userName: " + this.userName);
        }
        if (this.trainingTimeStamp != null)
        {
            Log.i(TAG, "trainingTimeStamp: " + this.trainingTimeStamp);
        }
        if (this.trainingNotepad != null)
        {
            Log.i(TAG, "trainingNotepad: " + this.trainingNotepad);
        }
        if (this.trainingDate != null)
        {
            Log.i(TAG, "date: " + this.trainingDate);
        }
        if (this.trainingTime != null)
        {
            Log.i(TAG, "trainingTime: " + this.trainingTime);
        }
    }


}
