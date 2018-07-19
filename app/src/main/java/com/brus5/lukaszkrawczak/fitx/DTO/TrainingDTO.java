package com.brus5.lukaszkrawczak.fitx.DTO;

import android.util.Log;

public class TrainingDTO implements DtoStatus {
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


        @Override
        public void printStatus() {
            Log.i(TAG, "printStatus: " +
                    " * trainingID: " + trainingID +
                    " * trainingDone: " + trainingDone +
                    " * trainingRestTime: " + trainingRestTime +
                    " * trainingReps: " + trainingReps +
                    " * trainingWeight: " + trainingWeight +
                    " * userName: " + userName +
                    " * trainingTimeStamp: " + trainingTimeStamp +
                    " * trainingNotepad: " + trainingNotepad +
                    " *");
        }
    }

