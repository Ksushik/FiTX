package com.brus5.lukaszkrawczak.fitx.Training;

public class TrainingList {
    private int resId;
    public String name;

    public void setResId(int resId) {
        this.resId = resId;
    }

    String getResourceName(){
        String excerciseName = "";
        switch (this.resId) {
            case 2131623973:
                excerciseName = "chest";
                break;
            case 2131623969:
                excerciseName = "abs";
                break;
            case 2131623979:
                excerciseName = "quads";
                break;
            case 2131623980:
                excerciseName = "shoulders";
                break;
            case 2131623981:
                excerciseName = "traps";
                break;
            case 2131623978:
                excerciseName = "lats";
                break;
            case 2131623976:
                excerciseName = "glutes";
                break;
            case 2131623982:
                excerciseName = "triceps";
                break;
            case 2131623977:
                excerciseName = "hamstrings";
                break;
            case 2131623972:
                excerciseName = "calves";
                break;
            case 2131623971:
                excerciseName = "biceps";
                break;
            case 2131623974:
                excerciseName = "forearms";
                break;
        }
        return excerciseName;
    }





}
