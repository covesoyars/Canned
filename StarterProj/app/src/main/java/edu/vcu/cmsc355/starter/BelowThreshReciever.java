package edu.vcu.cmsc355.starter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// reciever of alarm in main activity that sends manager an email and notification with the food that
// is below the threshold level
public class BelowThreshReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // send me an email to test if it works:
       // EmailSender sender = new EmailSender("test","soyarsc@vcu.edu","postmaster@automail-canned.com","test");
        //sender.send();
    }
}
