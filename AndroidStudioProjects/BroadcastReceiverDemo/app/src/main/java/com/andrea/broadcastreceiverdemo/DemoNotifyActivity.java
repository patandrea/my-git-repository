/*******************************************************
 * Author:      Andrea Patterson
 * Class:       CS311D
 * Instructor:  Abbas Moghtanei
 * Date:        09/10/14
 * Homework 2:  Demo Notifications
 * Description: Android activity which sends a Notification
 *              received from a BroadcastReceiver message.
 *********************************************************/
package com.andrea.broadcastreceiverdemo;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.andrea.broadcastreceiverdemo.R;


public class DemoNotifyActivity extends Activity
{
    public static final String TAG = "DemoNotifyActivity";
    // create notification id - unique number
    private static final int NOTIFY_ME_ID = 1337;
    private static final String NOTIFICATION_TITLE = "Yappy Hour Invitation";
    NotificationManager mgr = null;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.main_notify);

        mgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /****************************************************************
     * doClick(View v)
     * Method referenced in main_demo_broadcast_receiver.xml
     * Button android:onClick="doClick" replaces the onClick method.
     ******************************************************************/
    public void doClick(View v)
    {
        switch(v.getId())
        {
            case R.id.send_notification_btn: // Send Notification
                Log.d(TAG, "*** In doClick send_notification_btn pushed ***");
                sendNotification();
                break;
            case R.id.clear_notification_btn: // Clear Notification
                Log.d(TAG, "*** In doClick clear_notification_btn pushed ***");
                clearNotification();
                break;
            default:
                Log.e(TAG, "*** Unknown Button Clicked ***" );
                break;
        }
    }

    public void sendNotification()
    {
        // Get Intent sent by BroadcastReceiver
        Intent intent = getIntent();
        String broadcastMsg = intent.getStringExtra("BroadcastMessage");
        Log.d(TAG, "*** In sendNotification()  broadcastMsg = " + broadcastMsg + " ***");

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        // create object of Notification Builder
        NotificationCompat.Builder nb = new NotificationCompat.Builder(getBaseContext())
                .setTicker("New Notification")
                .setWhen(System.currentTimeMillis())
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(broadcastMsg)
                        //.setSmallIcon(R.drawable.small_light_bulb)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent);
        Log.d(TAG, "*** In sendNotification()  nb = " + nb + " ***");

        mgr.notify(NOTIFY_ME_ID, nb.build());
        Log.d(TAG, "*** In sendNotification()  notification has been sent ***");
    }
    public void clearNotification()
    {
        mgr.cancel(NOTIFY_ME_ID);
    }
}