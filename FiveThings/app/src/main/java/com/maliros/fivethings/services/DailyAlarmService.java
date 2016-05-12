package com.maliros.fivethings.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by user on 09/05/2016.
 */
public class DailyAlarmService extends Service {

    private AlarmManager alarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("starting service!!", "DailyAlarmService");
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60 * 1000,
                PendingIntent.getBroadcast(this, 0, alarmIntent, 0));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
