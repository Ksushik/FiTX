package com.brus5.lukaszkrawczak.fitx.dto;

public class MainDTO
{
    public String userName;
    public int userID;

    public int trainingID;
    public String trainingTimeStamp;
    public String date;

    public String trainingDone;
    public String trainingRestTime;
    public String trainingReps;
    public String trainingWeight;
    public String trainingNotepad;
    public String trainingTime;

    public MainDTO()
    {
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("MainDTO");
        builder.append("\n");
        builder.append("{");
        builder.append("\n");
        if (userID != 0)
        {
            builder.append("userID='");
            builder.append(userID);
            builder.append('\'');
            builder.append("\n");
        }
        if (userName != null)
        {
            builder.append("userName='");
            builder.append(userName);
            builder.append('\'');
            builder.append("\n");
        }
        if (trainingDone != null)
        {
            builder.append("trainingDone='");
            builder.append(trainingDone);
            builder.append('\'');
            builder.append("\n");
        }
        if (trainingRestTime != null)
        {
            builder.append("trainingRestTime='");
            builder.append(trainingRestTime);
            builder.append('\'');
            builder.append("\n");
        }
        if (trainingReps != null)
        {
            builder.append("trainingReps='");
            builder.append(trainingReps);
            builder.append('\'');
            builder.append("\n");
        }
        if (trainingWeight != null)
        {
            builder.append("trainingWeight='");
            builder.append(trainingWeight);
            builder.append('\'');
            builder.append("\n");
        }
        if (trainingNotepad != null)
        {
            builder.append("trainingNotepad='");
            builder.append(trainingNotepad);
            builder.append('\'');
            builder.append("\n");
        }
        if (trainingTime != null)
        {
            builder.append("trainingTime='");
            builder.append(trainingTime);
            builder.append('\'');
            builder.append("\n");
        }
        builder.append("}");

        return builder.toString();
    }

}

