package com.dsc.wwapp.asynchronous;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import static com.dsc.wwapp.activites.MainActivity.TAG;
import static com.dsc.wwapp.utils.Constants.JOB_ID;
import static com.dsc.wwapp.utils.Constants.JOB_PERIODIC_INTERVAL;

public class JobHandler extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        Log.i(TAG,"scheduled job ran ");

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {

        Log.i(TAG,"job stopped ");

        return false;
    }

    public void scheduleJob(Context context){

        ComponentName componentName = new ComponentName(context,JobHandler.class);
        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,componentName)
                .setPersisted(true)
                .setPeriodic(JOB_PERIODIC_INTERVAL)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresDeviceIdle(false);

        assert scheduler != null;
        scheduler.schedule(builder.build());
        Log.i(TAG,"job scheduled");

    }

    public void cancelJob(Context context){

        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (scheduler != null) {
            scheduler.cancel(JOB_ID);
        }
        Log.i(TAG,"job cancelled");
    }
}
