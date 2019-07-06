package com.dsc.wwapp.asynchronous;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.dsc.wwapp.activites.MainActivity.TAG;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"alarm manager worked");
        //post notification using notificationHandler class
    }

    public void setAlarm(Context context,String time,int requestCode,int flag){



    }

    public void cancelAlarm(Context context,int requestCode){

    }
}
