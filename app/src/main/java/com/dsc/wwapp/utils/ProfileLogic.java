package com.dsc.wwapp.utils;


import android.util.Log;

import static com.dsc.wwapp.activites.MainActivity.TAG;
import static com.dsc.wwapp.utils.Constants.TASK_TYPE_GOOD_PRACTISE;
import static com.dsc.wwapp.utils.Constants.TASK_TYPE_MANDATORY;
import static com.dsc.wwapp.utils.Constants.TASK_TYPE_YES_NO;

public class ProfileLogic {


    private double taskWeight;

    public double getTaskWeight(final int taskType,final int taskInt){

       switch (taskType){
           case TASK_TYPE_MANDATORY: taskWeight = getMandatoryWeight(taskInt);
               break;
           case TASK_TYPE_GOOD_PRACTISE: taskWeight = getGoodPractiseWeight(taskInt);
               break;
           case TASK_TYPE_YES_NO: taskWeight = getYesNoWeight(taskInt);
               break;
       }

        Log.i(TAG,"taskType => "+taskType+" taskWeight => "+taskWeight);
        return taskWeight;

    }

    private double getMandatoryWeight(int taskInt) {

        switch (taskInt){
            case 0:
                return 1.0;
            case 1:
                return 0.3;
            case 2:
                return 0.0;
            default: return 0.0;
        }

    }

    private double getGoodPractiseWeight(int taskInt){

        switch (taskInt){
            case 0:
                return 0.6;
            case 1:
                return 0.2;
            case 2 :
                return 0.0;
            default:
                return 0.0;
        }
    }

    private double getYesNoWeight(int taskInt){
        switch (taskInt){
            case 0:
                return 0.7;
            case 1:
                return 0.0;
            default:
                return 0.0;
        }
    }

}
