package com.brus5.lukaszkrawczak.fitx.DTO;

import android.util.Log;

public class TrainingDTO extends TrainingStatusDTO implements DtoStatus {
        private static final String TAG = "TrainingShowByUserDTO";
        public String trainingID;
        public String trainingDone;
        public String trainingRestTime;
        public String trainingReps;
        public String trainingWeight;
        public String userName;
        public String trainingTimeStamp;
        public String trainingNotepad;
        public String trainingDate;


    public TrainingDTO(String trainingID, String trainingDone, String trainingRestTime, String trainingReps, String trainingWeight, String userName, String trainingTimeStamp, String trainingNotepad, String trainingDate) {
        super(trainingID, trainingDone, trainingRestTime, trainingReps, trainingWeight, userName, trainingTimeStamp, trainingNotepad, trainingDate);
    }

    public TrainingDTO() {
    }

    @Override
        public void printStatus() {

        new TrainingDTO(trainingID,trainingDone,trainingRestTime,trainingReps,trainingWeight,userName,trainingTimeStamp,trainingNotepad,trainingDate);
        }
    }

