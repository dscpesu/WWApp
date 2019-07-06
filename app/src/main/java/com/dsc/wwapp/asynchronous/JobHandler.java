package com.dsc.wwapp.asynchronous;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class JobHandler extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public void scheduleJob(){

    }

    public void cancelJob(){

    }
}
