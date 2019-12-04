package com.it.insidetowns.theinsidetowns;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.it.insidetowns.theinsidetowns.services.MyFirebaseMessagingService;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class MessageReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "ALARM!! ALARM!!", Toast.LENGTH_SHORT).show();
        Log.e("receiver-->","yes");
       /* context.startService(new Intent(context, MyFirebaseMessagingService.class));
        ComponentName comp = new ComponentName(context.getPackageName(),
                MyFirebaseMessagingService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));*/
        Intent intent1 = new Intent("com.it.insidetowns.theinsidetowns.services.MyFirebaseMessagingService");
        context.sendBroadcast(intent1);
    }
}
