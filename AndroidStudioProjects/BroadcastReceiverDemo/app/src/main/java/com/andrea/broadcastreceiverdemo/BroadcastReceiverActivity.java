package com.andrea.broadcastreceiverdemo;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BroadcastReceiverActivity extends Activity
{
    public static final String TAG = "BroadcastReceiverActivity";
    // create broadcast identifier
    public static String BROADCAST_STRING = "aa.ll.pp";
    private EditText et_broadcast_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_broadcast_receiver);

        mkToast("Enter Broadcast Message");

        et_broadcast_msg = (EditText) findViewById(R.id.et_broadcast_msg);
    }

    /****************************************************************
     * void sendBroadcastMsg(String broadcastMsg)
     * Method to sent a Broadcast Message
     ******************************************************************/
    public void sendBroadcastMsg(String broadcastMsg)
    {
        Log.d(TAG, "*** In sendBroadcastMsg ***");
        // create intent to wrap in Broadcast
        Intent i = new Intent();
        i.putExtra("message", broadcastMsg);
        // set action - carries identification
        i.setAction(BROADCAST_STRING);
        // method of Context class - Activity inherits from Context class
        sendBroadcast(i);
    }

    /****************************************************************
     * doClick(View v)
     * Method referenced in main_demo_broadcast_receiver.xml
     * Button android:onClick="doClick" replaces the onClick method.
     ******************************************************************/
    public void doClick(View v)
    {
        Log.d(TAG, "*** In doClick ***");
        switch(v.getId())
        {
            case R.id.send_broadcast_btn: // Send Broadcast Message from EditText
                Log.d(TAG, "*** In sendBroadcastMsg send_broadcast_btn pushed ***");
                String broadcastMsg = et_broadcast_msg.getText().toString();
                Log.d(TAG, "*** In doClick() broadcastMsg = " + broadcastMsg + " ***");
                sendBroadcastMsg(broadcastMsg);
                break;
            default:
                Log.e(TAG, "*** Unknown Button Clicked ***" );
                break;
        }
    }

    /****************************************************************
     * MkToast(String text)
     * Used to display message to user.
     ******************************************************************/
    private void mkToast(String text)
    {
        Context con = getApplicationContext();
        Toast t =
                Toast.makeText(con, text, Toast.LENGTH_SHORT);
        t.show();
    }
}
