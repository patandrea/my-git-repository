package com.andrea.asynctaksdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

public class AsyncTaskDemoActivity extends Activity {
    TextView tv, tv2, tv3;
    int i;
    public static final String TAG = "AsyncTaskDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.d(TAG, "*** In onCreate ***");
        tv = (TextView)findViewById(R.id.tv);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);
        // Heart of job
        // Create child thread and execute it to do job
        // execute is var arg (E .. args) any data type - variable number of args - data that
        // child needs
        new PrintSequenceTask().execute(1);
    }

    // Inner class
    public class PrintSequenceTask extends AsyncTask<Integer, Integer, Void>
    {
        // 1st Integer - data coming into class are all Integers
        // 2nd Integer for progress (reports to main Activity)
        // Void is result value - result of action - when no value is returned.

        // optional before thread starts what to do
        @Override
        protected void onPreExecute()
        {
            tv2.setText("Sequence numbers begin");
        }

        // Most important - do job of child here
// Should match arg in execute(1) of PrintSequenceTask
// not always Void  - matches last arg in PrintSequenceTask
        @Override
        protected Void doInBackground(Integer ... args)
        {
            // sending one arg to execute (args[0] = 1)
            for(i=args[0]; i<= 10; i++)
            {
                // reporting progress to parent
                Log.d(TAG, "*** In doInBackground i = " + i + " ***");
                publishProgress(i);
                SystemClock.sleep(1000); // delay to mimic heavy load task
            }
            // void nothing returned - Void is returning void object
            return null; // Void
        }

        // In Android several widgets for progress
        // return progress to parent
        // this method will run in main activity - call back method - called by publishProgress
        @Override
        protected void onProgressUpdate(Integer ... args)
        {
            Log.d(TAG, "*** In onProgressUpdate() i = " + i + " ***");
            tv3.setText("");
            tv3.setText(Integer.toString(i));
        }
        // child is done - what to do now
        // arg is Void - result of doInBackground

        @Override
        protected void onPostExecute(Void result)
        {
            tv2.setText("");
            tv2.setText("Sequence numbers over");
        }

    }
} // close outer class
