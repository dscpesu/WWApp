package com.dsc.wwapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.dsc.wwapp.utils.Constants.DEBUG_MODE;
import static com.dsc.wwapp.utils.Constants.FIREBASE_DOCUMENT_ID;
import static com.dsc.wwapp.utils.Constants.FIREBASE_USER_ID;
import static com.dsc.wwapp.utils.Constants.IS_FIRST_TIME_LAUNCH;
import static com.dsc.wwapp.utils.Constants.IS_QUESTIONS_ASKED;
import static com.dsc.wwapp.utils.Constants.PACKAGE_NAME;
import static com.dsc.wwapp.utils.Constants.PROFILE_RANK;
import static com.dsc.wwapp.utils.Constants.USER_EMAIL;

public  class PrefManager {

    private SharedPreferences pref;

     public PrefManager(Context context){

        pref = context.getSharedPreferences("com.dsc.wwappads", Context.MODE_PRIVATE);

    }

    public void setFirstTimeLaunch(boolean isFirstTime){

        pref.edit().putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTime).apply();

    }

    public void setDebugMode(boolean mode){

        pref.edit().putBoolean(DEBUG_MODE,mode).apply();

    }
    public void setQuestionAsked(boolean isQuestionAsked){

        pref.edit().putBoolean(IS_QUESTIONS_ASKED,isQuestionAsked).apply();

    }

    public void setFirebaseDocId(String firebaseDocId) {
        pref.edit().putString(FIREBASE_DOCUMENT_ID,firebaseDocId).apply();

    }

    public void setUserEmail(String email){
        pref.edit().putString(USER_EMAIL,email).apply();


    }
    public void setFirebaseAuthUserID(String uid){
        pref.edit().putString(FIREBASE_USER_ID,uid).apply();

    }

    public void setProfileRank(float rank){
        pref.edit().putFloat(PROFILE_RANK,rank).apply();

    }

    public String getUserEmail(){ return  pref.getString(USER_EMAIL,null);}

    public String getFirebaseUserId(){ return pref.getString(FIREBASE_USER_ID,null);}

    public String getFirebaseDocId() { return pref.getString(FIREBASE_DOCUMENT_ID,null);}

    public boolean isFirstTimeLaunch(){ return pref.getBoolean(IS_FIRST_TIME_LAUNCH,true);}

    public double getProfileRank(){ return pref.getFloat(PROFILE_RANK,0);}

    //change default boolean to true if you want to debug to be enabled when user installs
    public boolean isDebugMode(){return  pref.getBoolean(DEBUG_MODE,false);}

    public boolean isQuestionAsked(){ return pref.getBoolean(IS_QUESTIONS_ASKED,false);}
}
