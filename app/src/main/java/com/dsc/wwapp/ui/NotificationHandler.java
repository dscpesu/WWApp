package com.dsc.wwapp.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dsc.wwapp.R;
import com.dsc.wwapp.activites.MainActivity;
import com.dsc.wwapp.activites.WelcomeActivity;
import com.dsc.wwapp.utils.PrefManager;

import static com.dsc.wwapp.activites.MainActivity.TAG;
import static com.dsc.wwapp.utils.Constants.CHANNEL_ID_GOALS;
import static com.dsc.wwapp.utils.Constants.CHANNEL_ID_SUGGESTIONS;

public class NotificationHandler {

    //rename references later
    private NotificationCompat.Builder builder,builder2,builder3;

    public void createNotificationChannel(Context context){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);

            //motivitating the user and making sure they login to app to maintain streak channel
            CharSequence goalName = "Reminder";
            String goalDesc = "Notifies you on your daily goals and task";
            NotificationChannel goals = new NotificationChannel(CHANNEL_ID_GOALS, goalName, importance);
            goals.setDescription(goalDesc);

            //suggest user or remind them to do a task before an event to occur
            CharSequence suggestionName = "Suggestions";
            String suggestionDesc = "Suggestions based on your water using habits";
            NotificationChannel suggestions = new NotificationChannel(CHANNEL_ID_SUGGESTIONS,suggestionName,importance);
            suggestions.setDescription(suggestionDesc);

            //register channel with system
            notificationManager.createNotificationChannel(goals);
            notificationManager.createNotificationChannel(suggestions);
        }

    }

    public void buildNotification(Context context){

        builder = new NotificationCompat.Builder(context, CHANNEL_ID_SUGGESTIONS)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Test Notification")
                .setContentText("Suggest fact 1")
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(context,9,new Intent(context, MainActivity.class),0))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        builder2 = new NotificationCompat.Builder(context, CHANNEL_ID_SUGGESTIONS)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Test Notification")
                .setContentText("Suggest fact 2")
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(context,9,new Intent(context, MainActivity.class),0))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        builder3 = new NotificationCompat.Builder(context, CHANNEL_ID_SUGGESTIONS)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Test Notification")
                .setContentText("Suggest fact 3")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(PendingIntent.getActivity(context, 9, new Intent(context,MainActivity.class), 0));


    }

    public void displayNotification(Context context,int flag) {

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        switch (flag) {
            case 0:
                notificationManager.notify(7, builder.build());
                break;
            case 1:
                notificationManager.notify(7, builder2.build());
                break;
            case 2:
                notificationManager.notify(7, builder3.build());
                break;
            default:
                Log.i(TAG, "failed to create notification");
                break;
        }

    }




}
