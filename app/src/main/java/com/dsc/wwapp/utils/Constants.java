package com.dsc.wwapp.utils;

public class Constants {

    public static final String PACKAGE_NAME = "com.dsc.wwapppppp";

    //debug mode
    public static final String DEBUG_MODE = "debug";

    //sharedPref launch time constants
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public static final String IS_QUESTIONS_ASKED = "IsQuestionsAsked";
    public static final String FIREBASE_DOCUMENT_ID = "FirebaseDocId";
    public static final String FIREBASE_USER_ID = "FirebaseUserId";
    public static final String USER_EMAIL = "userEmail";
    public static final String PROFILE_RANK = "profileRank";

    public static final String CHANNEL_ID_GOALS = "reminder";
    public static final String CHANNEL_ID_SUGGESTIONS = "reminder";

    public static final int JOB_ID = 1;
    public static final long JOB_PERIODIC_INTERVAL = 1000*60*20; //trigger scheduled job every 20 min

    public static final String ALARM_INTENT_ACTION = "com.dsc.wwapp.SET_ALARM";
    public static final String INTENT_REQUEST_CODE_ALARM = "alarmIntentRequest";

    //Questions Rank Constants

    public static final int PROFILE_QUESTIONS = 5;

    public static final int PROFILE_TASK_UID_1 = 0;
    public static final int PROFILE_TASK_UID_2 = 1;
    public static final int PROFILE_TASK_UID_3 = 2;
    public static final int PROFILE_TASK_UID_4 = 3;
    public static final int PROFILE_TASK_UID_5 = 4;

    public static final String PROFILE_TASK_STR_1 = "showerTime";
    public static final String PROFILE_TASK_STR_2 = "waterPlants";
    public static final String PROFILE_TASK_STR_3 = "isShowering";
    public static final String PROFILE_TASK_STR_4 = "drinkWater";
    public static final String PROFILE_TASK_STR_5 = "tapBrushing";

    public static final int TASK_TYPE_MANDATORY = 0;
    public static final int TASK_TYPE_GOOD_PRACTISE = 1;
    public static final int TASK_TYPE_YES_NO = 2;

    public static final double MANDATORY_TASK_WEIGHT_0 = 1.0;
    public static final double MANDATORY_TASK_WEIGHT_1 = 0.3;
    public static final double MANDATORY_TASK_WEIGHT_2 = 0.0;

    public static final double GOOD_PRACTICE_TASK_WEIGHT_0 = 0.6;
    public static final double GOOD_PRACTICE_TASK_WEIGHT_1 = 0.2;
    public static final double GOOD_PRACTICE_TASK_WEIGHT_2 = 0;

    public static final double YES_NO_TASK_WEIGHT_0 = 0.7;
    public static final double YES_NO_TASK_WEIGHT_1 = 0.0;

    public static final String PROFILE_RANK_STR_0 = "Novice";
    public static final String PROFILE_RANK_STR_1 = "Adept";
    public static final String PROFILE_RANK_STR_2 = "Skilled";
    public static final String PROFILE_RANK_STR_3 = "Expert";
    public static final String PROFILE_RANK_STR_4 = "Veteran";

    public static final int PROFILE_RANK_INT_0 = 1;
    public static final int PROFILE_RANK_INT_1 = 2;
    public static final int PROFILE_RANK_INT_2 = 3;
    public static final int PROFILE_RANK_INT_3 = 4;
    public static final int PROFILE_RANK_INT_4 = 5;
    public static final int PROFILE_RANK_INT_5 = 6;

    public static final int VALID_TASK = 0;
    public static final int INVALID_TASK = 1;

}
