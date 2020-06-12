package com.batteryalarm.rupeshmalkar.batteryalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView batteryTxt;
    String NOTIFICATION_CHANNEL_ID = "10001";
    String DEFAULT_NOTI_CHANNEL_ID = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        batteryTxt = (TextView) findViewById(R.id.batteryText);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int status =  intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
                boolean batteryFull = status == BatteryManager.BATTERY_STATUS_FULL;

/*                if (batteryFull){
                    batteryTxt.setText("battery full");
                }else {
                    batteryTxt.setText("Not full");
                }*/

                if (batteryFull){

                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    builder.setContentTitle("Battery Alarm");
                    builder.setContentText("Battery Full");
                    builder.setAutoCancel(true);
                    builder.setSound(alarmSound);
                    builder.setSmallIcon(android.support.v7.appcompat.R.drawable.notification_icon_background);
                    Notification notification = builder.build();
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(1,notification);
                    batteryTxt.setText("battery full");

                }

            }
        };
        registerReceiver(receiver,intentFilter);

    }
}

