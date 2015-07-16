package com.andrea.lostdogposter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class CreatePosterActivity extends Activity {
    private static final String TAG = "CreatePosterActivity";
    //public static final String LOST_DOG_PREFERENCES = "LostDogPreferences";
    public static final int LOAD_IMAGE_RESULTS = 1; // request code
    public static final int DOG_INFO_REQUEST_CODE = 2;
    private Button submitBtn;
    private ImageView image;// ImageView
    private EditText et_name, et_description, et_microchip, et_owner_info;
    private TextView tvLostDog;
    private HashMap<String, String> htDogInfo;

    private String name, description, mChipNumber,  ownerName, ownerPhone;

    private String dogsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "*** In onCreate ***");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_create_poster);

        // Instruct user to select a picture of their dog from the Gallery
        mkToast("First, enter the info about your dog - all but microchip number are required",
                Toast.LENGTH_LONG);
        submitBtn = (Button) findViewById(R.id.btnSubmit);
    }

    /**
     * *************************************************************
     * doClick(View v)
     * Method referenced in screen2.xml Button android:onClick="doClick"
     * Replaces the onClick method.
     * ****************************************************************
     */
    public void doClick(View v) {
        Log.d(TAG, "*** In doClick ***");
        switch (v.getId()) {
            /*
            case R.id.btnChoosePic:
                Log.d(TAG, "*** In doClick Choose Picture selected ***");
                // Create the Intent for Image Gallery.
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, LOAD_IMAGE_RESULTS);

                break;
            */
            case R.id.btnSubmit:
                Log.d(TAG, "*** In doClick Submit selected ***");
                saveDogInfo();
                //backToLauncher();

                Intent intent = new Intent(CreatePosterActivity.this, ShowPosterActivity.class);
                intent.putExtra("htDogInfo", htDogInfo);
                startActivity(intent);

                break;
        }
    }

    public void saveDogInfo() {
        et_name = (EditText) findViewById(R.id.etDogsName);
        //String name = et_name.getText().toString();
         name = et_name.getText().toString();
        if (name == null || name.length() == 0)
            mkToast("Name cannot be blank! Enter dogs name then Click Save", Toast.LENGTH_LONG);

        et_description = (EditText) findViewById(R.id.etDescription);
        //String description = et_description.getText().toString();
        description = et_description.getText().toString();
        if (description == null || description.length() == 0)
            mkToast("Description cannot be blank! Enter dogs description then Click Save", Toast.LENGTH_LONG);

        // Can be blank if not applicable
        //String mChipNumber = "";
        mChipNumber = "";
        et_microchip = (EditText) findViewById(R.id.etMicrochip);
        mChipNumber = et_microchip.getText().toString();
        et_microchip.setText("Microchip Number: " + mChipNumber);

        et_owner_info = (EditText) findViewById(R.id.etOwnerInfo);
        String ownerInfo = et_owner_info.getText().toString();
        Log.d(TAG, "*** In saveDogInfo() ownerInfo = " + ownerInfo + " ***");
        if (ownerInfo == null || ownerInfo.length() == 0)
            mkToast("Owner Info cannot be blank! Enter Owner Info then Click Save", Toast.LENGTH_LONG);

        String nameAndNumber[] = ownerInfo.split(",");
        String ownerName = nameAndNumber[0];

        Log.d(TAG, "*** In saveDogInfo() ownerName = " + ownerName + " ***");
        String ownerPhone = nameAndNumber[1].trim();
        Log.d(TAG, "*** In saveDogInfo() ownerPhone = " + ownerPhone + " ***");
        et_owner_info.setText("Call " + ownerName + " at " + ownerPhone);

        // Save to hashmap
        htDogInfo = new HashMap();
        htDogInfo.put("Name", name);
        htDogInfo.put("Description", description);
        htDogInfo.put("MicrochipNumber", mChipNumber);
        htDogInfo.put("OwnerName", ownerName);
        htDogInfo.put("OwnerPhone", ownerPhone);


    }

    /**
     * ****************************************************************
     * backToLauncher()
     * Return to Launcher Activity (LostDogActivity)
     * *****************************************************************
     *  NEVER CALLED
     */
    public void backToLauncher() {
        Log.d(TAG, "*** In backToLauncher()  ***");
        // Back to Launcher
        Intent in = new Intent();
        in.putExtra("htDogInfo", htDogInfo);
        setResult(RESULT_OK, in);
        //finish();

    }

    /**
     * *************************************************************
     * MkToast(String text)
     * SHORT duration
     * ****************************************************************
     */
    private void mkToast(String text) {
        Context con = getApplicationContext();
        Toast t =
                Toast.makeText(con, text, Toast.LENGTH_SHORT);
        t.show();
    }


    /**
     * *************************************************************
     * MkToast(String text, int duration)
     * arg duration
     * ****************************************************************
     */
    private void mkToast(String text, int toastLength) {
        Context con = getApplicationContext();
        Toast t =
                Toast.makeText(con, text, toastLength);
        t.show();
    }
}