package com.andrea.statesandcapswithunclesam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class StatesandCapsActivity extends Activity {

    public static final int GAME_REQUEST_CODE = 1;
    public static final int SCORE_REQUEST_CODE = 2;
    // String containing Player Name and Score returned from GameActivity
    private String dataFromGame = "";
    private EditText et;

    /****************************************************************
     * onCreate(Bundle savedInstanceState)
     * Method called when the Activity is first created.
     ******************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView image;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen1);

        image = (ImageView) findViewById(R.id.uncle_sam);
        image.setImageResource(R.drawable.uncle_sam_wants_you);
        et = (EditText) findViewById(R.id.et);
    }

    /****************************************************************
     * doClick(View v)
     * Method referenced in screen2.xml Button android:onClick="doClick"
     * Replaces the onClick method.
     ******************************************************************/
    public void doClick(View v) {
        String playerName = et.getText().toString();

        switch (v.getId()) {
            // Play Game
            case R.id.button1:
                if (!playerName.equals("")) {
                    mkToast("Welcome to the State Capitals Game " + playerName + "!");
                }
                // Launch StateCapitalGame
                Intent i = new Intent(this, GameActivity.class);
                i.putExtra("PlayerName", playerName);
                startActivityForResult(i, GAME_REQUEST_CODE);
                break;
            // Show Score
            case R.id.button2:
                // Launch ScoreActivity
                Intent scoreIntent = new Intent(this, ScoreActivity.class);
                scoreIntent.putExtra("NameAndScore", dataFromGame);
                startActivityForResult(scoreIntent, SCORE_REQUEST_CODE);
                break;
            default:
        }
    }

    /**
     * *************************************************************
     * MkToast(String text)
     * Used to display directions and other messages to user.
     * ****************************************************************
     */
    private void mkToast(String text) {
        Context con = getApplicationContext();
        Toast t =
                Toast.makeText(con, text, Toast.LENGTH_SHORT);
        t.show();
    }

    /****************************************************************
     * onCreate(Bundle savedInstanceState)
     * Method called when the Activity is first created.
     ******************************************************************/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String returnString;

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GAME_REQUEST_CODE:
                    dataFromGame = data.getStringExtra("NameAndScore");
                    break;
                case SCORE_REQUEST_CODE:
                    // Back from ScoreActivity
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
                    break;
            }
        }
    }
}
