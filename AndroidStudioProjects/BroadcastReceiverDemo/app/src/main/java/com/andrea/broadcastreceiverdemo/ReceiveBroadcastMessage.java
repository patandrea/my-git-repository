/*******************************************************
 * Author:      Andrea Patterson
 * Class:       CS311D
 * Instructor:  Abbas Moghtanei
 * Date:        08/29/14
 * Homework 1:  Receive Broadcast Receiver
 * Description: Android class which receives Broadcast
 *              message sent by DemoBroadcastReceiver.
 *********************************************************/
package com.andrea.broadcastreceiverdemo;

import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.util.Log;

public class ReceiveBroadcastMessage extends BroadcastReceiver
{
    public static final String TAG = "ReceiveBroadcastMessage";
    private static final String BROADCAST_ID = "aa.ll.pp";

    @Override
    public void onReceive(Context c, Intent i)
    {
        String action = i.getAction(); // instance method of Intent class
        Log.d(TAG, "In onReceive() action = " + action + " ***");

        if(action != null && action.equals(BROADCAST_ID))
        {
            String message = i.getStringExtra("message"); // key that was sent
            Log.d(TAG, "Received Message: " + message);

            Intent intent = new Intent(c, DemoNotifyActivity.class);
            intent.putExtra("BroadcastMessage", message);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(intent);

        }
    }

}

