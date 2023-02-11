package com.example.androidbasic_step8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NotifyMeActivity extends AppCompatActivity {

    private Button button_notify;
    private Button button_cancel;
    private Button button_update;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotifyManager;
    private NotificationReceiver mReceiver = new NotificationReceiver();
    private static final String ACTION_UPDATE_NOTIFICATION = BuildConfig.APPLICATION_ID + ".ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_CANCEL_NOTIFICATION = BuildConfig.APPLICATION_ID + ".ACTION_CANCEL_NOTIFICATION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_notify = findViewById(R.id.notify);
        button_update = findViewById(R.id.update);
        button_cancel = findViewById(R.id.cancel);
        button_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotification();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelNotification();
            }
        });

        createNotificationChannel();
        setNotificationButtonState(true, false, false);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_NOTIFICATION);
        intentFilter.addAction(ACTION_CANCEL_NOTIFICATION);
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private NotificationCompat.Builder getNotificationBuilder(){
        // 인스턴스화 후 속성 설정
        Intent notificationIntent = new Intent(this, NotifyMeActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent cancelIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent notificationCancelPendingIntent = PendingIntent.getBroadcast(this,NOTIFICATION_ID, cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent) // 알림 누르면 실행되는 PendingIntent 등록
                .setDeleteIntent(notificationCancelPendingIntent)
                .setAutoCancel(true) // tap하면 notification 종료
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 우선순위
                .setDefaults(NotificationCompat.DEFAULT_ALL); // 사운드, 진동, LED 등
        return notifyBuilder;
    }

    public void createNotificationChannel()
    {
        // NotificationManager를 인스턴스화
        mNotifyManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);
            // 알림 채널 속성
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification(){
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.addAction(R.drawable.ic_update,"Update Notification",updatePendingIntent);

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        setNotificationButtonState(false, true, true);
    }

    public void updateNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        // Update version 1. BigPictureStyle
        // Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.mascot_1);
        // notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(img).setBigContentTitle("Notification Updated!"));

        // Update version 2. InBoxStyle
        String str1 = "Here is the first one";
        String str2 = "This is the second one";
        notifyBuilder.setStyle(new NotificationCompat.InboxStyle().addLine(str1).addLine(str2).setBigContentTitle("Title").setSummaryText("+2 more"));

        mNotifyManager.notify(NOTIFICATION_ID,notifyBuilder.build());
        setNotificationButtonState(false, false, true);
    }

    public void cancelNotification() {
        mNotifyManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true, false, false);
    }

    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled, Boolean isCancelEnabled) {
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }

    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // 알림 내 아이콘을 클릭하면 알림이 업데이트 되도록 할 예정
            // 근데 이 때 broadcast를 사용하나봄봄
            String intentAction = intent.getAction();
            if(intentAction != null){
                switch(intentAction){
                    case ACTION_UPDATE_NOTIFICATION:
                        Log.d(ACTION_UPDATE_NOTIFICATION,"UPDATE CLICKED");
                        updateNotification();
                        break;
                    case ACTION_CANCEL_NOTIFICATION:
                        Log.d(ACTION_CANCEL_NOTIFICATION,"CANCEL CLICKED");
                        setNotificationButtonState(true,false, false);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}