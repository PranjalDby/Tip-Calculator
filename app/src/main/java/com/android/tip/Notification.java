package com.android.tip;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class Notification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity not = new MainActivity();
    }
    public void pushNotification(Context context,String des){
        Intent intent = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.tip)
                .setContentTitle("Tip")
                .setChannelId("1001")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentText("Amount is " + des)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Welcome To Tip App For Android."))
                .setAutoCancel(true);

        NotificationManager mNotificationMan = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationMan.createNotificationChannel(new NotificationChannel("1001","Tip", NotificationManager.IMPORTANCE_DEFAULT));
        }
        mNotificationMan.notify(1,mbuilder.build());
    }
}
