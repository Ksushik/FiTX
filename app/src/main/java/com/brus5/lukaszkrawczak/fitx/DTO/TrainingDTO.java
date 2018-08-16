package com.brus5.lukaszkrawczak.fitx.DTO;

public class TrainingDTO
{
    private String trainingID;
    private String trainingDone;
    private String trainingRestTime;
    private String trainingReps;
    private String trainingWeight;
    private String userName;
    private String userID;
    private String trainingTimeStamp;
    private String trainingNotepad;
    private String trainingDate;
    private String trainingTime;




    public TrainingDTO() {}

    public String getTrainingID()
    {
        return trainingID;
    }

    public void setTrainingID(String trainingID)
    {
        this.trainingID = trainingID;
    }

    public String getTrainingDone()
    {
        return trainingDone;
    }

    public void setTrainingDone(String trainingDone)
    {
        this.trainingDone = trainingDone;
    }

    public String getTrainingRestTime()
    {
        return trainingRestTime;
    }

    public void setTrainingRestTime(String trainingRestTime)
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

    public void setUserID(String userID)
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

    public String getTrainingTime()
    {
        return trainingTime;
    }

    public void setTrainingTime(String trainingTime)
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
        if (trainingID != null)
        {
            builder.append("trainingID='");
            builder.append(trainingID);
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
        if (userName != null)
        {
            builder.append("userName='");
            builder.append(userName);
            builder.append('\'');
            builder.append("\n");
        }
        if (userID != null)
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

