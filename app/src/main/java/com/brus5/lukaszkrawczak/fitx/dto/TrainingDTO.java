package com.brus5.lukaszkrawczak.fitx.dto;

public class TrainingDTO
{
    private int trainingID;
    private int trainingDone;
    private long trainingTime;

    private long trainingRestTime;
    private String trainingReps;
    private String trainingWeight;
    private String trainingTimeStamp;
    private String trainingNotepad;
    private String trainingDate;

    private String userName;
    private int userID;


    public TrainingDTO() {}


    public int getTrainingID()
    {
        return trainingID;
    }

    public void setTrainingID(int trainingID)
    {
        this.trainingID = trainingID;
    }

    public int getTrainingDone()
    {
        return trainingDone;
    }

    public void setTrainingDone(int trainingDone)
    {
        this.trainingDone = trainingDone;
    }

    public long getTrainingRestTime()
    {
        return trainingRestTime;
    }

    public void setTrainingRestTime(long trainingRestTime)
    {
        this.trainingRestTime = trainingRestTime;
    }

    public String getTrainingReps()
    {
        return trainingReps;
    }

    public void setTrainingReps(String trainingReps)
    {
        this.trainingReps = trainingReps;
    }

    public String getTrainingWeight()
    {
        return trainingWeight;
    }

    public void setTrainingWeight(String trainingWeight)
    {
        this.trainingWeight = trainingWeight;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setUserID(int userID)
    {
        this.userID = userID;
    }

    public String getTrainingTimeStamp()
    {
        return trainingTimeStamp;
    }

    public void setTrainingTimeStamp(String trainingTimeStamp)
    {
        this.trainingTimeStamp = trainingTimeStamp;
    }

    public String getTrainingNotepad()
    {
        return trainingNotepad;
    }

    public void setTrainingNotepad(String trainingNotepad)
    {
        this.trainingNotepad = trainingNotepad;
    }

    public String getTrainingDate()
    {
        return trainingDate;
    }

    public void setTrainingDate(String trainingDate)
    {
        this.trainingDate = trainingDate;
    }

    public long getTrainingTime()
    {
        return trainingTime;
    }

    public void setTrainingTime(long trainingTime)
    {
        this.trainingTime = trainingTime;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("TrainingDTO");
        builder.append("\n");
        builder.append("{");
        builder.append("\n");
        if (trainingID != 0)
        {
            builder.append("trainingID='");
            builder.append(trainingID);
            builder.append('\'');
            builder.append("\n");
        }
        if (trainingDone != 0)
        {
            builder.append("trainingDone='");
            builder.append(trainingDone);
            builder.append('\'');
            builder.append("\n");
        }
        if (trainingRestTime != 0)
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
        if (userName != null)
        {
            builder.append("userName='");
            builder.append(userName);
            builder.append('\'');
            builder.append("\n");
        }
        if (userID != 0)
        {
            builder.append("userID='");
            builder.append(userID);
            builder.append('\'');
            builder.append("\n");
        }
        if (trainingTimeStamp != null)
        {
            builder.append("trainingTimeStamp='");
            builder.append(trainingTimeStamp);
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
        if (trainingDate != null)
        {
            builder.append("trainingDate='");
            builder.append(trainingDate);
            builder.append('\'');
            builder.append("\n");
        }
        if (trainingTime != 0)
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

