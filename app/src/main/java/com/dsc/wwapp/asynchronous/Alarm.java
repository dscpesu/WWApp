package com.dsc.wwapp.asynchronous;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import static com.dsc.wwapp.activites.MainActivity.TAG;
import static com.dsc.wwapp.utils.Constants.ALARM_INTENT_ACTION;
import static com.dsc.wwapp.utils.Constants.INTENT_REQUEST_CODE_ALARM;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"alarm manager worked");
        //post notification using notificationHandler class
    }

    public void setAlarm(Context context,long time,int requestCode,int flag){

        long newTime = convertedTime(time);

        Intent intent = new Intent(context,Alarm.class);
        intent.setAction(ALARM_INTENT_ACTION);
        intent.putExtra(INTENT_REQUEST_CODE_ALARM,requestCode);
        PendingIntent pi = PendingIntent.getBroadcast(context,requestCode,intent,flag);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,newTime,pi);
        }else
            am.setExact(AlarmManager.RTC_WAKEUP,newTime,pi);


    }

    private long convertedTime(long time) {

        //TODO: Convert given time format to system time in milliseconds
        //use simpledateformat

        return System.currentTimeMillis();
    }

    public void cancelAlarm(Context context,int requestCode, int flag){

        Intent intent = new Intent(context,Alarm.class);
        intent.setAction(ALARM_INTENT_ACTION);
        PendingIntent pi = PendingIntent.getBroadcast(context,requestCode,intent,flag);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
        pi.cancel();

    }
}
