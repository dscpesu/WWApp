package com.dsc.wwapp.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dsc.wwapp.R;
import com.dsc.wwapp.activites.MainActivity;
import com.dsc.wwapp.activites.SignUserActivity;
import com.dsc.wwapp.asynchronous.AppExecutor;
import com.dsc.wwapp.database.profile.ProfileDatabase;
import com.dsc.wwapp.database.profile.UserProfile;
import com.dsc.wwapp.database.questions.QuestionsDatabase;
import com.dsc.wwapp.utils.ProfileLogic;

import static com.dsc.wwapp.activites.MainActivity.TAG;
import static com.dsc.wwapp.activites.WelcomeActivity.prefManager;
import static com.dsc.wwapp.asynchronous.FirestoreHandler.user;
import static com.dsc.wwapp.utils.Constants.PROFILE_RANK;
import static com.dsc.wwapp.utils.Constants.PROFILE_TASK_STR_1;
import static com.dsc.wwapp.utils.Constants.PROFILE_TASK_STR_2;
import static com.dsc.wwapp.utils.Constants.PROFILE_TASK_STR_3;
import static com.dsc.wwapp.utils.Constants.PROFILE_TASK_STR_4;
import static com.dsc.wwapp.utils.Constants.PROFILE_TASK_STR_5;
import static com.dsc.wwapp.utils.Constants.PROFILE_TASK_UID_1;
import static com.dsc.wwapp.utils.Constants.PROFILE_TASK_UID_2;
import static com.dsc.wwapp.utils.Constants.PROFILE_TASK_UID_3;
import static com.dsc.wwapp.utils.Constants.PROFILE_TASK_UID_4;
import static com.dsc.wwapp.utils.Constants.PROFILE_TASK_UID_5;
import static com.dsc.wwapp.utils.Constants.TASK_TYPE_GOOD_PRACTISE;
import static com.dsc.wwapp.utils.Constants.TASK_TYPE_MANDATORY;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionsFragment extends Fragment {


    private ProfileLogic logic;
    private UserProfile userProfile;
    private QuestionsDatabase questionDB;
    private ProfileDatabase profileDB;

    public QuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions, container, false);

        return view;


    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Questions");
        questionDB = QuestionsDatabase.getInstance(getContext());
        profileDB = ProfileDatabase.getInstance(getContext());
        logic = new ProfileLogic();
        /*
         * taskType will be set manually for each page where question is asked
         * taskInt will be entered by user where it will be calculated and appropriate weight will be added
        */

        //each block of code represents a single page where question is asked;

        // block 1


        {

            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final int taskCategory = questionDB.questiondDAO().getCategory(PROFILE_TASK_UID_1);
                    final double taskWeight = logic.getTaskWeight(taskCategory,2);
                    user.put(questionDB.questiondDAO().getKey(PROFILE_TASK_UID_1), taskWeight); //values to be put in server
                    userProfile = new UserProfile(PROFILE_TASK_UID_1,taskCategory,true,1,taskWeight); //values stored locally
                    profileDB.profileDAO().insertProfile(userProfile);
                }
            });
            Log.i(TAG,"Q1 answered");

        }

        // block 2
        {

            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final int taskCategory = questionDB.questiondDAO().getCategory(PROFILE_TASK_UID_2);
                    final double weight = logic.getTaskWeight(taskCategory,2);
                    user.put(questionDB.questiondDAO().getKey(PROFILE_TASK_UID_2), weight); //values to be put in server
                    userProfile = new UserProfile(PROFILE_TASK_UID_2,taskCategory,true,1,weight); //values stored locally
                    profileDB.profileDAO().insertProfile(userProfile);
                }
            });

            Log.i(TAG,"Q2 answered");
        }

        // block 3

        {

            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final int taskCategory = questionDB.questiondDAO().getCategory(PROFILE_TASK_UID_3);
                    final double weight = logic.getTaskWeight(taskCategory,0);
                    user.put(questionDB.questiondDAO().getKey(PROFILE_TASK_UID_3), weight); //values to be put in server
                    userProfile = new UserProfile(PROFILE_TASK_UID_3,taskCategory,true,1,weight); //values stored locally
                    profileDB.profileDAO().insertProfile(userProfile);
                }
            });
            Log.i(TAG,"Q3 answered");
        }

        //block 4

        {

            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final int taskCategory = questionDB.questiondDAO().getCategory(PROFILE_TASK_UID_4);
                    final double weight = logic.getTaskWeight(taskCategory,1);
                    user.put(questionDB.questiondDAO().getKey(PROFILE_TASK_UID_4), weight); //values to be put in server
                    userProfile = new UserProfile(PROFILE_TASK_UID_4,taskCategory,true,1,weight); //values stored locally
                    profileDB.profileDAO().insertProfile(userProfile);
                }
            });
            Log.i(TAG,"Q4 answered");
        }

        //block 5

        {

            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final int taskCategory = questionDB.questiondDAO().getCategory(PROFILE_TASK_UID_5);
                    final double weight = logic.getTaskWeight(taskCategory,1);
                    user.put(questionDB.questiondDAO().getKey(PROFILE_TASK_UID_5), weight); //values to be put in server
                    userProfile = new UserProfile(PROFILE_TASK_UID_5,taskCategory,true,1,weight); //values stored locally
                    profileDB.profileDAO().insertProfile(userProfile);
                }
            });
            Log.i(TAG,"Q5 answered");
        }

        logic.getProfileRank(getContext());

    }
}
