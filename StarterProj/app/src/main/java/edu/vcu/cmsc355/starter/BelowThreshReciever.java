package edu.vcu.cmsc355.starter;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.app.NotificationChannel;

// reciever of alarm in main activity that sends manager an email and notification with the food that
// is below the threshold level
// main activity will need to only call startAlarm() if the person logging in is a manager
public class BelowThreshReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //addNotification(context);
        // send me an email to test if it works:
       //EmailSender sender = new EmailSender("test","soyarsc@vcu.edu","postmaster@automail-canned.com","test");
       //sender.send();
    }

    public void pull(){
        //SAM i need you to pull all the fooditems from the db (i can separate out the ones below threshold)
    }

    /*private void addNotification(Context context) {
        String CHANNEL_ID = "channel_0";
        CharSequence name = "Inventory";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context);
                        builder.setSmallIcon(R.drawable.default_profile_picture)
                        .setContentTitle("Low stock items")
                        .setContentText("Check your email for a list of items below their threshold" +
                                "level.");

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }*/
}
