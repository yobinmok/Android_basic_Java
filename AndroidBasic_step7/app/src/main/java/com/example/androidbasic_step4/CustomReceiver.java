package com.example.androidbasic_step4;

import static java.lang.Math.sqrt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
    private TextView textView;

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        int random = intent.getIntExtra("random",0);
        if (intentAction != null){
            String toastMessage = "unknown intent action";
            switch (intentAction){
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power connected!";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power disconnected!";
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    int num_result = (int) Math.pow(random,2);
                    toastMessage = "Custom Broadcast Received\nSquare of the Random number: " + num_result;
                    break;
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}