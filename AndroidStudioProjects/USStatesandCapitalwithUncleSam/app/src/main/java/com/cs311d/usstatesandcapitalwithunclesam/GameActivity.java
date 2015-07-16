/*******************************************************
 * Name:        GameActivity
 * Description: Android App to gets a state or a capitol
 *              from an EditText box.
 *
 *              1. Select state to capitol or vise versa
 *                 via the radio buttons.
 *              2. Enter the state or capitol in the
 *                 the EditText box.
 *              3. Press the "Submit button" to get back a
 *                 state or capitol.
 *********************************************************/
package com.cs311d.usstatesandcapitalwithunclesam;

import android.app.Activity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import android.view.View;

import java.util.Scanner;
import java.io.InputStream;

public class GameActivity extends Activity {
    public static final String TAG = "GameActivity";
    private static final String TABLE_NAME = "States";
    public static final String STATE = "state";
    public static final String CAPITAL = "capital";
    public static final String _ID = "_id";

    private EditText et_sc, et_sc_guess;
    private RadioButton radio_sc;
    private RadioButton radio_cs;

    private int randomNum = 0;
    private String playerName = "";
    private String sc = ""; // State or Capital entered by Game
    private boolean isState = false;
    private String correctAnswer = "";
    private int score; // Player's score
    private int guessCount = 0;

    private static final String text10Guesses =
            "You have had 10 guesses.\n" +
                    "To see your score\n" +
                    "Click Show Score Button";

    private SQLiteDatabase db;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        ImageView image;
        RadioGroup rg_cs;
        //int checkedId;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen2);

        image = (ImageView) findViewById(R.id.uncle_sam);
        image.setImageResource(R.drawable.uncle_sam_half_size);

        // Get Player's Name from Launcher activity (GuessStatesAndCapsActivity)
        Intent i = getIntent();
        playerName = i.getStringExtra("PlayerName");

        et_sc = (EditText) findViewById(R.id.et_sc);
        et_sc_guess = (EditText) findViewById(R.id.et_sc_guess);

        // Create or Open StateCaps database if necessary
        try {
            db = this.openOrCreateDatabase("StateCaps.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        }
        catch (Exception e) {
            Log.e(TAG, "Error occurred opening StateCaps.db " + e);
        }

        // Create States table if it does not exist
        createStatesTable(db);
        if (!tableHasData(db)) {
            populateStatesTable(db);
        }

        mkToast("First: Select State or Capital");

        radio_sc = (RadioButton)findViewById(R.id.state_to_capital);
        radio_cs = (RadioButton)findViewById(R.id.capital_to_state);

        // get Radio Button selected in RadioGroup
        rg_cs = (RadioGroup)findViewById(R.id.rg_cs);
        int checkedId = rg_cs.getCheckedRadioButtonId();

        rg_cs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            /** Called when a RadioButton in a RadioGroup is selected */
            @Override
            public void onCheckedChanged(RadioGroup arg0, int checkedId)
            {
                // Create a Random Number between 1 - 50 (states)
                // to set EdiText with random state or capital
                randomNum = (int)(Math.random() * 50 + 1);

                switch(checkedId)
                {
                    case -1: // Nothing Checked
                        mkToast("Select State or Capital");
                        break;
                    case R.id.state_to_capital: // State picked
                        sc = getState(db,randomNum);
                        et_sc_guess.setHint("Your Answer Here (Capital)");
                        isState = true;
                        radio_sc.setChecked(false);
                        break;
                    case R.id.capital_to_state: // Capital to State
                        // Pick a capital to enter in EditText using randomNum
                        sc = getCapital(db, randomNum);
                        et_sc_guess.setHint("Your Answer Here (State)");
                        isState = false;
                        radio_cs.setChecked(false);
                        break;
                    default:
                        break;

                }
                String question;

                if(isState) {
                    question = "What is the Capital of " + sc + " ?";
                }
                else{
                    question = sc + " is the Capital of?";
                }
                et_sc.setTextColor(getResources().getColor(R.color.navy));
                et_sc.setText(question);
                et_sc_guess.setTextColor(getResources().getColor(R.color.navy));

                mkToast("Type your Answer and Click Enter");
            }}); // END (call to onCheckedChanged)
    }

    /**
     * ****************************************************************
     * createStatesTable(SQLiteDatabase db)
     * Method to create States table
     * *****************************************************************
     */
    public void createStatesTable(SQLiteDatabase db)
    {
        String sql =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                        + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + STATE + " TEXT NOT NULL, "
                        + CAPITAL + " TEXT NOT NULL"
                        + ");";

        // Create states table
        try{
            db.execSQL(sql);
        }
        catch (Exception e){
            Log.e(TAG, "Error Creating StateCaps.db " + e);
        }

    }
    /**
     * ****************************************************************
     * boolean tableHasData(SQLiteDatabase db)
     * Method to determine whether the States table has been populated.
     * Returns true or false.
     * *****************************************************************
     */
    public boolean tableHasData(SQLiteDatabase db)
    {
        boolean hasData = false;

        try {
            Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME, null);
            cursor.moveToFirst();
            if (cursor.getInt(0) > 0)
                hasData = true; // there are rows in the table
            cursor.close();
        }
        catch (Exception e){
            Log.e(TAG, "Error occurred on States Table " + e);
        }

        return(hasData);
    }

    // New read from /res/raw
    /**
     * ****************************************************************
     * populateStatesTable(SQLiteDatabase db)
     * Method to populate the States with data from us_states file in
     * /res/raw.
     * *****************************************************************
     */


    public void populateStatesTable(SQLiteDatabase db) {
        int lineCount = 0;
        ContentValues values = new ContentValues();

        // Get an InputStream from /res/raw R.id
        InputStream in = getResources().openRawResource(
                getResources().getIdentifier("us_states",
                        "raw", getPackageName()));
        try {
            Scanner scanner = new Scanner(in);

            while (scanner.hasNext()) {
                // Bypass Header lines on file
                while (lineCount++ < 2) {
                    scanner.nextLine();
                }

                String line = scanner.nextLine();
                Log.d(TAG, "*** In populateStatesTable line = " + line +" ***" );
                String stateCap[] = line.split("\\s{2,}");
                String state = stateCap[0];
                String cap = stateCap[1];

                // Put state, capital from file to States table
                values.put(STATE, state);
                values.put(CAPITAL, cap);
                db.insert(TABLE_NAME, null, values);
            }
            in.close();
        } catch (Exception e) {
            Log.e(TAG, "Error occurred writing to States table " + e);
        }

    }

    /****************************************************************
     * doClick(View v)
     * Method referenced in screen2.xml Button android:onClick="doClick"
     * Replaces the onClick method.
     ******************************************************************/
    public void doClick(View v)
    {
        String gameReturnString;
        String guess;

        switch(v.getId())
        {
            case R.id.guessBtn: // Now "Guess"
                guessCount++;
                // Get guess compare to answer
                guess = et_sc_guess.getText().toString();
                correctAnswer = getCorrectAnswer(sc);

                if(guess.trim().equalsIgnoreCase(correctAnswer))
                {
                    mkToast("Your answer is correct " + playerName + "!");
                    score += 10;
                }
                else
                {
                    mkToast("Sorry " + playerName + ", Your answer is wrong!\n" +
                            "The correct answer is: " + correctAnswer + "." );
                }
                break;
            default:
                break;
        }
        // Clear last guess
        et_sc_guess.setText("");
        // Clear last state or capital
        et_sc.setText("");
        // Clear Hint
        et_sc_guess.setHint("");

        // Check if 10 Guesses are up
        if(guessCount > 9){
            mkToast(text10Guesses, Toast.LENGTH_LONG);
            gameReturnString = playerName + " " + score;
            guessCount = 0; // reset guessCount
            backToLauncher(gameReturnString);
        }
        else{
            mkToast("Select State or Capital");
        }
    }

    /*******************************************************************
     * backToLauncher()
     * Return to Launcher Activity with Player Name and Score
     *******************************************************************/
    public void backToLauncher(String gameReturnString) {
        // Back to Launcher GuessStatesAndCapsActivity with player name and score
        Intent in = new Intent();
        in.putExtra("NameAndScore", gameReturnString);

        setResult(RESULT_OK, in);
        finish();

    }
    /*******************************************************************
     * getState(int randomNum)
     * Method to get state from the Database using a random Number
     *******************************************************************/
    public String getState(SQLiteDatabase db, int randNum) {
        String state = "";
        Cursor c;

        try {
            c = db.rawQuery("SELECT state from States where _ID = ?",
                    new String[]{Integer.toString(randNum)});
            c.moveToFirst();
            //if(c != null && c.getCount() > 0)
            if(c.getCount() > 0)
                state = c.getString(0);
        }
        catch (Exception e) {
            Log.e(TAG, "Error occurred getting State from States table " + e);
        }
        return(state);
    }

    /*******************************************************************
     * getCapital(SQLiteDatabase db, int randomNum)
     * Method to get capital from the Database using a random Number
     * to set EditText capital.
     *******************************************************************/
    public String getCapital(SQLiteDatabase db, int randNum)
    {
        String capital = "";
        Cursor c;
        try {
            c = db.rawQuery("SELECT capital from States where _ID = ?",
                    new String[]{Integer.toString(randNum)});

            c.moveToFirst();

            if (c.getCount() > 0)
                capital = c.getString(0);
        }
        catch (Exception e) {
            Log.e(TAG, "Error occurred getting Capital from States table " + e);
        }

        return(capital);
    }

    /*******************************************************************
     * getCorrectAnswer(String stateOrCap)
     * Takes String arg either state or Capital
     * If state selected return corresponding capital from map
     * If capital selected return corresponding state from map
     * as correctAnswer
     *******************************************************************/
    public String getCorrectAnswer(String stateOrCap) {
        if(isState)
        {
            // State - get Capital
            correctAnswer = getCapitalForState(db, stateOrCap);
        }
        else // is Capital
        {
            // Capital - get State
            correctAnswer = getStateForCapital(db, stateOrCap);

        }
        return(correctAnswer);
    }

    /*******************************************************************
     * getStateForCapital(SQLiteDatabase db, String capital)
     * Takes String state
     * Returns state corresponding to capital
     *******************************************************************/
    public String getStateForCapital(SQLiteDatabase db, String capital)
    {
        String state = "";
        Cursor c;

        try {
            c = db.rawQuery("SELECT state from States where capital = ?",
                    new String[]{capital});
            //for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
            if (c != null) {
                if (!c.isAfterLast()) {
                    c.moveToFirst();
                    state = c.getString(0);
                }
            }

        }
        catch (Exception e) {
            Log.e(TAG, "Error occurred Querying States table " + e);
        }
        return(state);

    }

    /*******************************************************************
     * getCapitalForState(SQLiteDatabase db, String state)
     * Takes String state
     * Returns capital corresponding to state
     *******************************************************************/
    public String getCapitalForState(SQLiteDatabase db, String state) {
        Cursor c;
        String capital = "";

        try {


            c = db.rawQuery("SELECT capital from States where state = ?",
                    new String[]{state});
            if (c != null) {
                if (!c.isAfterLast()) {
                    c.moveToFirst();
                    capital = c.getString(0);
                }
            }
        }
        catch (Exception e){
            Log.e(TAG, "Error occurred Querying States table " + e);
        }

        return(capital);
    }

    /****************************************************************
     * MkToast(String text)
     * Used to display directions and other messages to user.
     ******************************************************************/
    private void mkToast(String text) {
        Context con = getApplicationContext();
        Toast t =
                Toast.makeText(con, text, Toast.LENGTH_SHORT);
        t.show();
    }

    /****************************************************************
     * MkToast(String text, int duration)
     * Used to display directions and other messages to user.
     ******************************************************************/
    private void mkToast(String text, int toastLength) {
        Context con = getApplicationContext();
        Toast t =
                Toast.makeText(con, text, toastLength);
        t.show();
    }

}