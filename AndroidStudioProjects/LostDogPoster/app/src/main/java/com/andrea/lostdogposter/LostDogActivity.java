package com.andrea.lostdogposter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;

public class LostDogActivity extends Activity implements Serializable {
    public static final String TAG = "LostDogPosterActivity";
    public static final int CREATE_POSTER_REQUEST_CODE = 1;
    public static final int SHOW_POSTER_REQUEST_CODE = 2;
    private HashMap<String, String> htDogInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RadioGroup rg_what_to_do;
        RadioButton rb_new_poster;
        RadioButton rb_lost_info;
        Button btnSubmit;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_lost_dog);

        rb_new_poster = (RadioButton)findViewById(R.id.rb_new_poster);
        rb_lost_info = (RadioButton)findViewById(R.id.rb_lost_info);

        btnSubmit = (Button)findViewById(R.id.btnSubmit);

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
                        createNewPoster();

                        break;
                    case R.id.rb_lost_info:
                        Log.d(TAG, "*** Add lost info ***");
                        //addLostInfo = true;
                        //nextBtn.setText("Add Info");
                        //nextScreen = 2;
                        break;
                    default:
                        Log.d(TAG, "*** Unknown Button Clicked ***");
                        break;
                }


            }}); // END (call to setOnCheckedListener)

    } // end onCreate()

    // Create new Intent to create a new lost dog poster
    public void createNewPoster()
    {
        // Launch CreatePosterActivity
        Intent i = new Intent(this, CreatePosterActivity.class);
        startActivityForResult(i, CREATE_POSTER_REQUEST_CODE);
    }

    // Create new Intent to show Poster that was just createdr
    public void showPoster()
    {
        // Launch ShowPosterActivity
        Intent i = new Intent(this, ShowPosterActivity.class);

        i.putExtra("htDogInfo", (Serializable) htDogInfo);
        startActivityForResult(i, SHOW_POSTER_REQUEST_CODE);
    }

    /****************************************************************
     * onCreate(Bundle savedInstanceState)
     * Method called when the Activity is first created.
     ******************************************************************/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String returnString;

        Log.d(TAG, "*** In LostDogActivity onActivityResult ***\n"
                   + "requestCode = " + requestCode + "\n"
                   + "resultCode = " + resultCode + "\n"
                   + "data = " + data + " ***"
                   );
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // Returned from CreatePosterActivity
                case CREATE_POSTER_REQUEST_CODE:
                    Log.d(TAG, "*** onActivityResult data = " + data + " ***");
                    Intent intent = getIntent();
                    try {
                        htDogInfo = (HashMap<String, String>)intent.getSerializableExtra("htDogInfo");
                    }
                    catch (Exception e) {
                        Log.d(TAG, "*** onActivityResult in catch ***");
                        e.printStackTrace();
                    }
                    //htDogInfo = (HashMap<String, String>)intent.getSerializableExtra("htDogInfo");
                    Log.d(TAG, "*** onActivityResult request code =  CREATE_POSTER_REQUEST_CODE ***");
                    if(htDogInfo == null)
                        Log.d(TAG, "*** onActivityResult htDogInfo is NULL ***");
                    // Start intent ShowPosterActivity (will save data)
                    showPoster();
                    break;

                case SHOW_POSTER_REQUEST_CODE:
                    // Back from ShowPosterActivity - need to save data in hashMap to DB
                    /*
                    returnString = data.getStringExtra("ReturnString");
                    // Stop Button clicked
                    if(returnString.length() > 3) {
                        if (returnString.substring(0, 4).equalsIgnoreCase("STOP")) {
                            finish();
                        }
                    }
                    // Score button clicked before game played
                    if(returnString.length() > 3) {
                        if (!returnString.substring(0, 4).equalsIgnoreCase("XXXX")) {
                            et.setText(returnString);
                        }
                    }
                    */
                    Log.d(TAG, "*** onActivityResult request code =  SHOW_POSTER_REQUEST_CODE ***");
                    break;

            }

        }

    }


    /*************************************************************
     * MkToast(String text)
     * Used to display directions and other messages to user.
     *************************************************************/
    private void mkToast(String text) {
        Context con = getApplicationContext();
        Toast t =
                Toast.makeText(con, text, Toast.LENGTH_SHORT);
        t.show();
    }

}
