package com.brus5.lukaszkrawczak.fitx.Training.DTO;

import android.util.Log;

import com.brus5.lukaszkrawczak.fitx.DtoStatus;

public class TrainingShowByUserDTO implements DtoStatus {
        private static final String TAG = "TrainingShowByUserDTO";
        public String userName;
        public String dateToday;

        @Override
        public void printStatus() {
            Log.i(TAG, "printStatus: " +
                    " * userName: " + userName +
                    " * dateToday: " + dateToday +
                    " *");
        }
    }

