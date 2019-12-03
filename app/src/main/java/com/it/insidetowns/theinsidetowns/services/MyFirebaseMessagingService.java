package com.it.insidetowns.theinsidetowns.services;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.it.insidetowns.theinsidetowns.Activities.InfoPage;
import com.it.insidetowns.theinsidetowns.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;


/*This is the service running in background to receive the message
which is sent by server or fc messaging service*/
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    String title;

    //message list adapter to create a message item
//    private NotificationListAdapter notificationssListAdapter;
//    List<NotificationListItem> notificationsArraylist = new ArrayList<>();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log

        try {
            Log.e(TAG,"Data---" +remoteMessage.getMessageId()+
                                    "\n"+remoteMessage.getData().get("id")+
                                    "\n"+remoteMessage.getData().get("image")+
                                    "\n"+remoteMessage.getMessageType()+
                                    "\n"+remoteMessage.getNotification());
        } catch (Exception e) {
            e.printStackTrace();
        }


//        Log.e(TAG,"Title--" +remoteMessage.getData().get("dataTitle"));
//        Log.e(TAG,"Message--" +remoteMessage.getData().get("dataMsg"));
//        Log.e(TAG,"Message--" +remoteMessage.getData().get("dataTime"));
//
//        NotificationListItem notificationListItem = new NotificationListItem(remoteMessage.getData().get("dataTitle"), remoteMessage.getData().get("dataMsg"),remoteMessage.getData().get("dataTime"),remoteMessage.getData().get("notification_id"),"UNRD");
//
//        notificationsArraylist.add(notificationListItem);

        Intent notificationIntent =  new Intent();

        notificationIntent.putExtra("notificationId",remoteMessage.getData().get("notification_id"));
        notificationIntent.putExtra("notificationTitle",remoteMessage.getData().get("dataTitle"));
        notificationIntent.putExtra("notificationMessage",remoteMessage.getData().get("dataMsg"));
        notificationIntent.putExtra("notificationTime",remoteMessage.getData().get("dataTime"));

        onReceive(this,notificationIntent);


        String json = remoteMessage.getNotification().getBody();
        try {
            JSONObject obj = new JSONObject(json);
            String image = obj.getString("Product_Image");
            String id = obj.getString("Product_ID");
            Log.d("My App", image );
            Log.d("My App", id);
            sendNotification(remoteMessage.getData().get("dataTitle"),image,id);

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
        }


    }


    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        Bundle extras = intent.getExtras();
        Intent i = new Intent("NotificationReceived");
        Log.e("Broadcast", "__________GCM Broadcast"+extras.toString());

        //Log messages
        Log.e(TAG,"NotifId" +extras.getString("notificationId"));
        Log.e(TAG,"Notiftitle"+extras.getString("notificationTitle"));
        Log.e(TAG,"notificationMessage"+extras.getString("notificationMessage"));
        Log.e(TAG,"notificationTime"+extras.getString("notificationTime"));
        // Data you need to pass to activity
        i.putExtra("notificationId", extras.getString("notificationId"));
        i.putExtra("notificationTitle", extras.getString("notificationTitle"));
        i.putExtra("notificationMessage", extras.getString("notificationMessage"));
        i.putExtra("notificationTime", extras.getString("notificationTime"));

        context.sendBroadcast(i);
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String title,String messageBody,String productId) {

        Intent resultIntent = new Intent(this, InfoPage.class);
//
//        Log.d("Message body","" +messageBody);
//
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        // Adds the back stack
//        stackBuilder.addParentStack(SplashScreenActivity.class);
//
        resultIntent.putExtra("dataMsg",messageBody);
//        resultIntent.putExtra("dataTitle",title);

        // Adds the intent to top of the stack
        stackBuilder.addNextIntent(resultIntent);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        //  editor.putString("CatId", ""+itemList.get(position).getId());
        editor.putString("Product_Id", "" + productId);

        /*intialising pending intent for the future use when  notificatiion is clicked from
        notification bar of device for navigating to notification screen*/
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        /*setting notification tone*/
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap image= null;
        try {
            String imageurl = "http://theitapp.tech"+messageBody;
            URL url = new URL(imageurl);
             image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }

        /*setting up notification builder to make notification ui and setting its properties*/
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.mipmap.app_iconn);
            notificationBuilder.setLargeIcon(image);
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.app_iconn);
        }
                notificationBuilder.setContentTitle(title);
//                notificationBuilder.setContentText(messageBody);
                notificationBuilder.setAutoCancel(true);
//                notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
                notificationBuilder.setSound(defaultSoundUri);
                notificationBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /*sending the notification to user when message is received from the fcm*/
        notificationManager.notify(0, notificationBuilder.build());
    }
}