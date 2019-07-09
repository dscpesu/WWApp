package com.dsc.wwapp.utils;

public class Constants {

    public static final String PACKAGE_NAME = "com.pratik.productize";

    //debug mode
    public static final String DEBUG_MODE = "debug";

    //sharedPref launch time constants
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public static final String IS_QUESTIONS_ASKED = "IsQuestionsAsked";
    public static final String FIREBASE_DOCUMENT_ID = "FirebaseDocId";
    public static final String FIREBASE_USER_ID = "FirebaseUserId";
    public static final String USER_EMAIL = "userEmail";

    public static final String CHANNEL_ID_GOALS = "reminder";
    public static final String CHANNEL_ID_SUGGESTIONS = "reminder";

    public static final int JOB_ID = 1;
    public static final long JOB_PERIODIC_INTERVAL = 1000*60*20; //trigger scheduled job every 20 min

    public static final String ALARM_INTENT_ACTION = "com.dsc.wwapp.SET_ALARM";
    public static final String INTENT_REQUEST_CODE_ALARM = "alarmIntentRequest";
}
