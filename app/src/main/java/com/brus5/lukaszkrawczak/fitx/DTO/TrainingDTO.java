package com.brus5.lukaszkrawczak.fitx.DTO;

public class TrainingDTO extends TrainingStatusDTO implements DtoStatus
{
    public String trainingID;
    public String trainingDone;
    public String trainingRestTime;
    public String trainingReps;
    public String trainingWeight;
    public String userName;
    public String userID;
    public String trainingTimeStamp;
    public String trainingNotepad;
    public String trainingDate;
    public String trainingTime;


    public TrainingDTO(String trainingID, String trainingDone, String trainingRestTime, String trainingReps, String trainingWeight, String userName, String userID, String trainingTimeStamp, String trainingNotepad, String trainingDate, String trainingTime)
    {
        super(trainingID, trainingDone, trainingRestTime, trainingReps, trainingWeight, userName, userID,trainingTimeStamp, trainingNotepad, trainingDate, trainingTime);
    }

    public TrainingDTO()
    {
    }

    @Override
    public void printStatus()
    {
        new TrainingDTO(trainingID, trainingDone, trainingRestTime, trainingReps, trainingWeight, userName, userID, trainingTimeStamp, trainingNotepad, trainingDate, trainingTime);
    }
}

