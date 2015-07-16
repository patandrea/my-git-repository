package com.andrea.dateandtime;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.view.View;

import java.util.Date;
import java.text.DateFormat;

public class DateandTimeActivity extends Activity
{
    Button dateBtn, timeBtn;
    TextView tv, tv_date_or_time;
    String dateTime = "";
    public static final String TAG = "DateTimeActivity";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dateBtn = (Button) findViewById(R.id.dateButton);
        timeBtn = (Button) findViewById(R.id.timeButton);

        tv = (TextView)findViewById(R.id.tv);
        tv_date_or_time = (TextView)findViewById(R.id.tv_date_or_time);
    }

    /****************************************************************
     * doClick(View v)
     * Method referenced in main.xml Button android:onClick="doClick"
     * Replaces the onClick method.
     * ***************************************************************/
    public void doClick(View v)
    {
        String dt = new Date().toString();

        switch(v.getId())
        {
            case R.id.dateButton:
                dateTime = updateDate(dt);
                break;
            case R.id.timeButton:
                dateTime = updateTime();
                break;
            default:
                Log.e(TAG, "*** Unknown Button Clicked ***" );
                break;
        }
        tv_date_or_time.setText(dateTime);
    }

    /****************************************************************
     * updateDate(String dt)
     * Method that returns a string containing the date portion of the
     * output Date().
     * ***************************************************************/
    public String updateDate(String dt)
    {
        return(dt.substring(4, 10) + ", " +
                dt.substring(dt.length()-5));
    }

    /****************************************************************
     * updateTime(String dt)
     * Method that returns a string containing the time portion of the
     * output from Date().
     * ***************************************************************/
    //public String updateTime(String dt)
    public String updateTime()
    {
        String formattedTime = formatDate();
        return(formattedTime);
    }

    public String formatDate()
    {
        String strDate = "";

        Date now = new Date();

        try{
            strDate = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(now);
        }
        catch(Exception e){
            Log.e(TAG, "*** Error In formatDate = " + e + " ***" );
        }

        String[] splitDateTime = strDate.split(" ");
        String time = "";

        for(int i = 3; i < splitDateTime.length; i++){
            time = time + " " + splitDateTime[i];
        }

        return(time);
    }
}

