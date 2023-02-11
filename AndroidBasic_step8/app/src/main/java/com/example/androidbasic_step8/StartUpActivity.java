package com.example.androidbasic_step8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class StartUpActivity extends AppCompatActivity {

    private ToggleButton alarmToggle;
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
        alarmToggle = findViewById(R.id.alarmToggle);
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);

        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this,NOTIFICATION_ID,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // HW
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent nextAlarmPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, alarmIntent, 0);
        AlarmManager.AlarmClockInfo ac = new AlarmManager.AlarmClockInfo(System.currentTimeMillis()+10, nextAlarmPendingIntent);
        alarmManager.setAlarmClock(ac,nextAlarmPendingIntent);

        Log.d("Homework Toast Message", String.valueOf(alarmManager.getNextAlarmClock().getTriggerTime()));

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    long triggerTime = SystemClock.elapsedRealtime(); // + repeatInterval;

                    if (alarmManager != null)
                        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, notifyPendingIntent);
                    else
                        alarmManager.cancel(notifyPendingIntent);
                }else{
                    mNotificationManager.cancelAll();
                }
            }
        });
    }

    public void createNotificationChannel() {
        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Stand up notification", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void deliverNotification(Context context){
        Intent contentIntent = new Intent(context, StartUpActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID, contentIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID,builder.build());
    }
}