package com.capstone.adityanaikdomsangiovanni.wa_agenda;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Alarm_Receiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){


        NotificationCompat.Builder noti = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle("Task Due Soon")
                        .setContentText("Task Due Now");

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, noti.build());
    }


}
