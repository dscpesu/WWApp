package com.dsc.wwapp.utils;

import android.content.Context;

import com.dsc.wwapp.asynchronous.AppExecutor;
import com.dsc.wwapp.database.questions.QuestionsDatabase;
import com.dsc.wwapp.database.questions.Questions;

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
import static com.dsc.wwapp.utils.Constants.TASK_TYPE_YES_NO;

public class ProfileQuestions {

    private Context context;

    public ProfileQuestions(Context context){
        this.context = context;
    }

    public void createProfile(){

        final QuestionsDatabase questionsDB = QuestionsDatabase.getInstance(context);
        final Questions questionsQ1 = new Questions("how long do you shower daily?",TASK_TYPE_MANDATORY, PROFILE_TASK_UID_1,PROFILE_TASK_STR_1);
        final Questions questionsQ2 = new Questions("Do you use water filter waste water to water the plants?", TASK_TYPE_GOOD_PRACTISE, PROFILE_TASK_UID_2,PROFILE_TASK_STR_2);
        final Questions questionsQ3 = new Questions("Do you use shower while taking bath?",TASK_TYPE_YES_NO, PROFILE_TASK_UID_3,PROFILE_TASK_STR_3);
        final Questions questionsQ4 = new Questions("Do you usually drink a glass of water completely or throw away leftover water ", TASK_TYPE_GOOD_PRACTISE, PROFILE_TASK_UID_4,PROFILE_TASK_STR_4);
        final Questions questionsQ5 = new Questions("Do you keep the tap always on while brushing?",TASK_TYPE_MANDATORY, PROFILE_TASK_UID_5,PROFILE_TASK_STR_5);

        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                questionsDB.questiondDAO().insertQuestions(questionsQ1);
                questionsDB.questiondDAO().insertQuestions(questionsQ2);
                questionsDB.questiondDAO().insertQuestions(questionsQ3);
                questionsDB.questiondDAO().insertQuestions(questionsQ4);
                questionsDB.questiondDAO().insertQuestions(questionsQ5);

            }
        });


    }

}
