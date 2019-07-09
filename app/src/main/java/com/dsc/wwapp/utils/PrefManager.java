package com.dsc.wwapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.dsc.wwapp.utils.Constants.DEBUG_MODE;
import static com.dsc.wwapp.utils.Constants.FIREBASE_DOCUMENT_ID;
import static com.dsc.wwapp.utils.Constants.FIREBASE_USER_ID;
import static com.dsc.wwapp.utils.Constants.IS_FIRST_TIME_LAUNCH;
import static com.dsc.wwapp.utils.Constants.IS_QUESTIONS_ASKED;
import static com.dsc.wwapp.utils.Constants.PACKAGE_NAME;
import static com.dsc.wwapp.utils.Constants.USER_EMAIL;

public class PrefManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public PrefManager(Context context){

        this.context = context;
        pref = this.context.getSharedPreferences(PACKAGE_NAME,Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){

        editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTime);
        editor.commit();
    }

    public void setDebugMode(boolean mode){

        editor.putBoolean(DEBUG_MODE,mode);
        editor.commit();
    }
    public void setQuestionAsked(boolean isQuestionAsked){

        editor.putBoolean(IS_QUESTIONS_ASKED,isQuestionAsked);
        editor.commit();
    }

    public void setFirebaseDocId(String firebaseDocId) {
        editor.putString(FIREBASE_DOCUMENT_ID,firebaseDocId);
        editor.commit();
    }

    public void setUserEmail(String email){
        editor.putString(USER_EMAIL,email);
        editor.commit();

    }
    public void setFirebaseAuthUserID(String uid){
        editor.putString(FIREBASE_USER_ID,uid);
        editor.commit();
    }

    public String getUserEmail(){ return  pref.getString(USER_EMAIL,null);}

    public String getFirebaseUserId(){ return pref.getString(FIREBASE_USER_ID,null);}

    public String getFirebaseDocId() { return pref.getString(FIREBASE_DOCUMENT_ID,null);}

    public boolean isFirstTimeLaunch(){ return pref.getBoolean(IS_FIRST_TIME_LAUNCH,true);}

    //change default boolean to true if you want to debug to be enabled when user installs
    public boolean isDebugMode(){return  pref.getBoolean(DEBUG_MODE,false);}

    public boolean isQuestionAsked(){ return pref.getBoolean(IS_QUESTIONS_ASKED,false);}
}
