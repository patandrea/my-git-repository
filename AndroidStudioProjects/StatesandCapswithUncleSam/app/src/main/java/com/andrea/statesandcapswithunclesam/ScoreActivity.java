package com.andrea.statesandcapswithunclesam;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;


public class ScoreActivity extends Activity {

    private String playerName = "";
    private String returnString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView image;
        TextView tv_player_name, tv_player_score, tv_congrats;

        String[] nameScore;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_score);

        image = (ImageView) findViewById(R.id.uncle_sam);
        image.setImageResource(R.drawable.uncle_sam_wants_you2);

        // Get Player's Name and Score from Launcher activity
        Intent i = getIntent();
        String gameReturnString  = i.getStringExtra("NameAndScore");

        // Check Name and Score are set
        if(gameReturnString !=null && gameReturnString.length() > 0) {
            nameScore = gameReturnString.split("\\s+");
            playerName = nameScore[0];
            String playerScore = nameScore[1];

            tv_player_name = (TextView) findViewById(R.id.tv_player_name);
            tv_player_name.setText(playerName);

            tv_player_score = (TextView) findViewById(R.id.tv_player_score);
            tv_player_score.setText(playerScore);

            image = (ImageView) findViewById(R.id.uncle_sam);
            image.setImageResource(R.drawable.uncle_sam_wants_you2);

            String userFinishMsg = setFinishValues(playerName, Integer.parseInt(playerScore));
            tv_congrats = (TextView) findViewById(R.id.tv_congrats);
            tv_congrats.setText(userFinishMsg);
        }
        else{
            gameNotPlayedError();
        }
    }

    public void doClick(View v)
    {
        switch(v.getId())
        {
            case R.id.play_again_button:
                returnString = playerName;
                backToLauncher(returnString);
                break;
            // Stop
            case R.id.stop_button:
                returnString = "STOP";
                backToLauncher(returnString);
                break;
            default:
                break;
        }
    }

    /**
     * **************************************************************************
     * String setFinishValues(int score)
     * Returns string containing text message and image to display to User based
     * on User's score
     * **************************************************************************
     */
    private String setFinishValues(String playerName, int score) {
        String userFinishMessage = "";
        switch(score)
        {
            case 100:
                userFinishMessage = getResources().getString(R.string.congrats) + " " +
                        playerName + ", " +
                        getResources().getString(R.string.perfect_score);
                break;

            case 90:
                userFinishMessage = getResources().getString(R.string.congrats) + " " +
                        playerName + ", " +
                        getResources().getString(R.string.good_score);

                break;

            case 80:
                userFinishMessage = getResources().getString(R.string.congrats) + " " +
                        playerName + ", " +
                        getResources().getString(R.string.very_good_score);

                break;

            case 70:
            case 60:
                userFinishMessage =
                        playerName + ", " +
                                getResources().getString(R.string.fair_score);

                break;

            case 50:
            case 40:
            case 30:
            case 20:
            case 10:
            case 0:
                userFinishMessage =
                        playerName + ", " +
                                getResources().getString(R.string.poor_score);

                break;
            default:
        }
        return(userFinishMessage);
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
                Toast.makeText(con, text, Toast.LENGTH_LONG);
        t.show();
    }

    /**
     **************************************************************
     * gameNotPlayed
     * Score Button on main screen clicked before game played
     * Toast messages to user. Return "XXXX" to main activity.
     * ****************************************************************
     */
    public void gameNotPlayedError(){
        mkToast("Game has NOT been played Click Play Button");
        returnString = "XXXX";
        backToLauncher(returnString);
    }

    /*******************************************************************
     * backToLauncher()
     * Return to Launcher Activity with Player Name if "Play Again" clicked
     *                             or "STOP" if Stop button clicked
     *******************************************************************/
    public void backToLauncher(String returnString) {
        Intent in = new Intent();
        in.putExtra("ReturnString", returnString);

        setResult(RESULT_OK, in);
        finish();
    }

}
