package com.andrea.findmydog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class CreatePosterActivity extends Activity {
    private static final String TAG = "CreatePosterActivity";
    public static final String LOST_DOG_PREFERENCES = "LostDogPreferences";
    public static final int LOAD_IMAGE_RESULTS = 1; // request code
    public static final int DOG_INFO_REQUEST_CODE = 2;
    private Button choosePicBtn, continueBtn;
    private ImageView image;// ImageView
    private EditText et_name, et_description, et_contact_info;
    //private TextView tvLostDog, tvName, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ImageView image;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_create_poster);

        // Instruct user to select a picture of their dog from the Gallery
        //mkToast(selectPicture, Toast.LENGTH_LONG);
        // Get references to the GUI objects
        choosePicBtn = (Button)findViewById(R.id.btnChoosePic);
        continueBtn = (Button)findViewById(R.id.btnChoosePic);
        image = (ImageView)findViewById(R.id.image);


    }

    /****************************************************************
     * doClick(View v)
     * Method referenced in screen2.xml Button android:onClick="doClick"
     * Replaces the onClick method.
     ******************************************************************/
    public void doClick(View v)
    {
        Log.d(TAG, "*** In doClick ***");
        switch(v.getId())
        {
            case R.id.btnChoosePic:
                Log.d(TAG, "*** In doClick Choose Picture selected ***");
                // Create the Intent for Image Gallery.
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, LOAD_IMAGE_RESULTS);

                break;
            case R.id.btnContinue:
                Log.d(TAG, "*** In doClick Continue selected ***");
                Intent intent = new Intent(CreatePosterActivity.this, DogInfoActivity.class);
                startActivityForResult(intent, DOG_INFO_REQUEST_CODE);

                break;
            default:
                Log.e(TAG, "*** Unknown Button Clicked ***" );
                break;
        }
    }
/*
    // Save data collected so far to SharedPreferences
    public void saveInfo()
    {
        String name = et_name.getText().toString();
        String description = et_description.getText().toString();
        String contactInfo = et_contact_info.getText().toString();

        sharedPreferences = getSharedPreferences(LOST_DOG_PREFERENCES, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(Name, name);
        editor.putString(Description, description);
        editor.putString(ContactInfo, contactInfo);

        editor.commit();
    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "*** In onActivityResult() requestCode = " + requestCode + " ***");
        Log.d(TAG, "*** In onActivityResult() resultCode = " + resultCode + " ***");
        Log.d(TAG, "*** In onActivityResult() data = " + data + " ***");

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            Log.d(TAG, "*** In onActivityResult() filePath = " + filePath + " ***");
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            Log.d(TAG, "*** In onActivityResult() cursor = " + cursor + " ***");
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            Log.d(TAG, "*** In onActivityResult() imagePath = " + imagePath + " ***");

            // BitmapFactory.decodeFile returns Bitmap
            Bitmap origBitMap = BitmapFactory.decodeFile(imagePath);
            Bitmap resizedBitmap = getResizedBitmap(origBitMap);

            image.setImageBitmap(resizedBitmap);

            // At the end remember to close the cursor
            cursor.close();
            mkToast("Now Select Continue to enter dog info");
        }
        if (requestCode == DOG_INFO_REQUEST_CODE && resultCode == RESULT_OK) {
            String dogsName = data.getStringExtra("Name");
            String description = data.getStringExtra("Description");
            Log.d(TAG, "*** In onActivityResult() dogsName = " + dogsName + " ***");
            Log.d(TAG, "*** In onActivityResult() description = " + description + " ***");
            setTextFields(dogsName, description);

        }
    }

    public void setTextFields(String dogsName, String description){
        TextView tvLostDog, tvName, tvDescription;

        choosePicBtn.setVisibility(View.INVISIBLE);
        continueBtn.setVisibility(View.INVISIBLE);


        tvLostDog = (TextView)findViewById(R.id.tvLostDog);
        //tvName = (TextView)findViewById(R.id.tvDogsName);
        tvDescription = (TextView)findViewById(R.id.tvDescription);

        tvLostDog.setText(dogsName + " is Lost");
        //tvName.setText(dogsName);
        tvDescription.setText(description);

    }

    //public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
    public Bitmap getResizedBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int newWidth = width / 3;
        int newHeight = height / 3;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }


    /*******************************************************************
     * backToLauncher()
     * Return to Launcher Activity with Player Name and Score
     *******************************************************************/
    //public void backToLauncher(String gameReturnString)
    public void backToLauncher()
    {
        Log.d(TAG, "*** In backToLauncher()  ***" );
        // Back to Launcher GuessStatesAndCapsActivity with player name and score
        Intent in = new Intent();
        in.putExtra("PosterCreated", "Poster Created");

        setResult(RESULT_OK, in);
        finish();

    }

    /****************************************************************
     * MkToast(String text)
     * SHORT duration
     ******************************************************************/
    private void mkToast(String text)
    {
        Context con = getApplicationContext();
        Toast t =
                Toast.makeText(con, text, Toast.LENGTH_SHORT);
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
