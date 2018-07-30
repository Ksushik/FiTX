package com.brus5.lukaszkrawczak.fitx.DTO;

public class MainDTO extends TrainingStatusDTO implements DtoStatus
{
    private static final String TAG = "TrainingShowByUserDTO";
    public String trainingID;
    public String trainingDone;
    public String trainingRestTime;
    public String trainingReps;
    public String trainingWeight;
    public String userName;
    public String userID;
    public String trainingTimeStamp;
    public String trainingNotepad;
    public String date;


    public MainDTO(String trainingID, String trainingDone, String trainingRestTime, String trainingReps, String trainingWeight, String userName, String userID, String trainingTimeStamp, String trainingNotepad, String trainingDate)
    {
        super(trainingID, trainingDone, trainingRestTime, trainingReps, trainingWeight, userName, userID,trainingTimeStamp, trainingNotepad, trainingDate);
    }

    public MainDTO()
    {
    }

    @Override
    public void printStatus()
    {
        new MainDTO(trainingID, trainingDone, trainingRestTime, trainingReps, trainingWeight, userName, userID, trainingTimeStamp, trainingNotepad, date);
    }
}

