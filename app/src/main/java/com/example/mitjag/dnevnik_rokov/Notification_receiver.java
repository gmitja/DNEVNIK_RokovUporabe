package com.example.mitjag.dnevnik_rokov;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by mitja on 7.12.2016.
 */
public class Notification_receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent=new Intent(context,MainActivity.class);

        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle("Dnevnik rokov uporabe")
                .setContentText("Preglej potecene artikle!")
                .setAutoCancel(true);
        notificationManager.notify(100,builder.build());

    }

}
