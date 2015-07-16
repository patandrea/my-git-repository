package com.andrea.findmydog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//import android.widget.TextView;

public class LostDogPosterActivity extends Activity {
    public static final String TAG = "LostDogPosterActivity";
    public static final int CREATE_POSTER_REQUEST_CODE = 1;
    private int nextScreen;
    private Button nextBtn;
    private static final String selectEither =
            "Select Either:\n" +
                    "Create New Poster or\n" +
                    "Add Lost Info (where/when pet was lost)\n" +
                    "then Click Enter Button";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RadioGroup rg_what_to_do;
        RadioButton rb_new_poster;
        RadioButton rb_lost_info;
        //Button nextBtn;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_find_my_dog);

        rb_new_poster = (RadioButton)findViewById(R.id.rb_new_poster);
        rb_lost_info = (RadioButton)findViewById(R.id.rb_lost_info);

        nextBtn = (Button)findViewById(R.id.nextBtn);

        // get Radio Button selected in RadioGroup
        rg_what_to_do = (RadioGroup)findViewById(R.id.rg_what_to_do);
        int checkedId = rg_what_to_do.getCheckedRadioButtonId();
        Log.d(TAG, "*** In OnCreate checkedId = " + checkedId + "***");

        // Instruct user to select a Radio Button option
        rg_what_to_do.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             * Called when a RadioButton in a RadioGroup is selected
             */
            @Override
            public void onCheckedChanged(RadioGroup arg0, int checkedId) {

                switch (checkedId) {
                    case -1: // Nothing Checked
                        Log.d(TAG, "*** -1  Nothing Checked ***");
                        mkToast("Select what you want to do");
                        break;
                    case R.id.rb_new_poster:
                        Log.d(TAG, "*** New Poster ***");
                        //createNewPoster = true;
                        //nextBtn.setText("Create Poster");
                        nextScreen = 1;
                        break;
                    case R.id.rb_lost_info:
                        Log.d(TAG, "*** Add lost info ***");
                        //addLostInfo = true;
                        //nextBtn.setText("Add Info");
                        nextScreen = 2;
                        break;
                    default:
                        Log.d(TAG, "*** Unknown Button Clicked ***");
                        break;
                }


            }}); // END (call to setOnCheckedListener)

    } // end onCreate()

    /****************************************************************
     * doClick(View v)
     * Method referenced in main_lost_dog_poster.xml Button android:onClick="doClick"
     * Replaces the onClick method.
     ******************************************************************/
    public void doClick(View v) {
        Log.d(TAG, "*** In doClick ***");

        // Code to see which radio button selected
        // Either start new Intent to Create New Poster or Add Lost Info

        switch (nextScreen) {
            // Create new lost dog poster
            case 1:
                Log.d(TAG, "*** In doClick() Create New Poster selected ***");
                createNewPoster();
                /*
                createNewPoster();
                // Launch CreateNewPoster
                Intent i = new Intent(this, GameActivity.class);
                i.putExtra("PlayerName", playerName);
                Log.d(TAG, "*** In doClick() playerName = " + playerName + " ***");
                startActivityForResult(i, GAME_REQUEST_CODE);
                */
                break;
            // Add lost info to existing poster and send to recipients
            case 2:
                Log.d(TAG, "*** In doClick() Add lost info selected ***");
                /*
                addLostInfo();
                // Launch ScoreActivity
                Intent scoreIntent = new Intent(this, ScoreActivity.class);
                scoreIntent.putExtra("NameAndScore", dataFromGame);
                startActivityForResult(scoreIntent, SCORE_REQUEST_CODE);
                */
                break;
            default:
                Log.d(TAG, "*** In doClick() default Invalid Button ID  ***");
        }
    }

    // Create new Intent to create a new lost dog poster
    public void createNewPoster()
    {
        // Launch CreateNewPoster
        Intent i = new Intent(this, CreatePosterActivity.class);
        //i.putExtra("PlayerName", playerName);
        startActivityForResult(i, CREATE_POSTER_REQUEST_CODE);
    }

    // Create new Intent to add lost info to an existing lost dog poster
    public void addLostInfo()
    {

    }

    /****************************************************************
     * MkToast(String text)
     * Used to display directions and other messages to user.
     ******************************************************************/
    private void mkToast(String text)
    {
        Context con = getApplicationContext();
        Toast t = Toast.makeText(con, text, Toast.LENGTH_SHORT);
        t.show();
    }

    /****************************************************************
     * MkToast(String text, int duration)
     * arg duration
     ******************************************************************/
    private void mkToast(String text, int toastLength)
    {
        Context con = getApplicationContext();
        Toast t =
                Toast.makeText(con, text, toastLength);
        t.show();
    }

}
