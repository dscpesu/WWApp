package com.dsc.wwapp.utils;


import android.content.Context;
import android.util.Log;

import com.dsc.wwapp.asynchronous.AppExecutor;
import com.dsc.wwapp.database.profile.ProfileDatabase;
import com.dsc.wwapp.database.profile.UserProfile;

import java.util.List;

import static com.dsc.wwapp.activites.MainActivity.TAG;
import static com.dsc.wwapp.asynchronous.FirestoreHandler.user;
import static com.dsc.wwapp.utils.Constants.PROFILE_RANK;
import static com.dsc.wwapp.utils.Constants.TASK_TYPE_GOOD_PRACTISE;
import static com.dsc.wwapp.utils.Constants.TASK_TYPE_MANDATORY;
import static com.dsc.wwapp.utils.Constants.TASK_TYPE_YES_NO;

public class ProfileLogic {

    private double taskWeight;
    private double profileRank;
    private PrefManager pref;

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

    public double getProfileRank(final Context context){

        pref = new PrefManager(context);

        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                ProfileDatabase database = ProfileDatabase.getInstance(context);
                int taskType1 = database.profileDAO().getCountTaskType(TASK_TYPE_MANDATORY);
                int taskType2 = database.profileDAO().getCountTaskType(TASK_TYPE_GOOD_PRACTISE);
                int taskType3 = database.profileDAO().getCountTaskType(TASK_TYPE_YES_NO);
                double effectiveTask = taskType1 + taskType2*0.6 + taskType3;
                double effectiveScore = database.profileDAO().getTotalValidWeight();
                if(effectiveScore != 0)
                    profileRank = effectiveTask / effectiveScore;
                else
                    profileRank = 0;
                Log.i(TAG,"effective task =>"+effectiveTask);
                Log.i(TAG,"effective score =>"+effectiveScore);
                Log.i(TAG,"profile rank =>"+profileRank);

                user.put(PROFILE_RANK,profileRank);

            }
        });

        pref.setProfileRank((float) profileRank);

        return profileRank;
    }

}
