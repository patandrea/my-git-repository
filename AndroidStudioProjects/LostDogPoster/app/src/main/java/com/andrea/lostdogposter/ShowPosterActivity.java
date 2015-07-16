package com.andrea.lostdogposter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class ShowPosterActivity extends Activity {
    private static final String TAG = "ShowPosterActivity";
    public static final int LOAD_IMAGE_RESULTS = 1; // request code
    public static final int DOG_INFO_REQUEST_CODE = 2;
    private Button choosePicBtn, saveBtn;
    private ImageView image;// ImageView
    private EditText et_name, et_description, et_microchip, et_owner_info;
    private TextView tvLostDog;

    private String dogsName;
    private HashMap<String, String> hmDogInfo;
    private SQLiteDatabase db;

    private static final String TABLE_NAME = "Dog";
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String PICTURE = "picture";
    public static final String DESCRIPTION = "description";
    public static final String MICROCHIP_NUMBER = "microchip_number";
    public static final String OWNER_NAME = "owner_name";
    public static final String OWNER_PHONE = "owner_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "*** In onCreate ***");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_show_poster);

        Log.d(TAG, "*** In onCreate() db = " + db + " ***");

        // Instruct user to select a picture of their dog from the Gallery
        mkToast("First click Choose Picture to select a picture of your dog from the gallery",
                Toast.LENGTH_LONG);
        // Get references to the GUI objects
        choosePicBtn = (Button) findViewById(R.id.btnChoosePic);
        saveBtn = (Button) findViewById(R.id.btnSave);
        image = (ImageView) findViewById(R.id.image);

        // Get Hashmap from Intent
        Intent intent = getIntent();
        hmDogInfo = (HashMap <String, String>) intent.getSerializableExtra("htDogInfo");
        if(hmDogInfo == null)
            Log.d(TAG, "*** In  onCreate hmDogInfo is NULL ***");
        Log.d(TAG, "*** In  onCreate name = " + hmDogInfo.get("Name") + " ***");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "*** In onStart ***");
        super.onStart();
        setDogInfo();
    }
        // Set dog info with data saved in the Model from CreatePosterActivity
    public void setDogInfo() {
        //LostDogModel ldm = new LostDogModel();

        tvLostDog = (TextView) findViewById(R.id.tvLostDog);
        String title = hmDogInfo.get("Name") + " is Lost";
        Log.d(TAG, "*** In  setDogInfo title = " + title + " ***");
        tvLostDog.setText(title);

        et_name = (EditText) findViewById(R.id.etDogsName);
        //String name = ldm.getDogsName();
        String name = hmDogInfo.get("Name");
        Log.d(TAG, "*** In setDogInfo dogsName = " + name + " ***");
        //et_name.setText(ldm.getDogsName());
        et_name.setText(name);

        et_description = (EditText) findViewById(R.id.etDescription);
        String description = hmDogInfo.get("Description");
        et_description.setText(description);
        Log.d(TAG, "*** In setDogsInfo description = " + description + " ***");

        // Can be blank if not applicable
        et_microchip = (EditText) findViewById(R.id.etMicrochip);
        String microchipNum = hmDogInfo.get("MicrochipNumber");
        et_microchip.setText("Microchip Number: " + microchipNum);


        et_owner_info = (EditText) findViewById(R.id.etOwnerInfo);
        String ownerName = hmDogInfo.get("OwnerName");
        String ownerPhone = hmDogInfo.get("OwnerPhone");
// Change this to 2 fields
        et_owner_info.setText("Call " + ownerName + " at " + ownerPhone);
    }

    public void doClick(View v) {
        Log.d(TAG, "*** In doClick ***");
        switch (v.getId()) {
            case R.id.btnChoosePic:
                Log.d(TAG, "*** In doClick Choose Picture Btn clicked ***");
                // Create the Intent for Image Gallery.
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, LOAD_IMAGE_RESULTS);
                break;

            case R.id.btnSave:
                Log.d(TAG, "*** In doClick Save Btn clicked ***");
                saveDogInfoToDB();
                break;

            default:
                Log.e(TAG, "*** Unknown Button Clicked ***");
                break;
        }
    }

    public void saveDogInfoToDB(){
        Log.d(TAG, "*** In saveDogInfoToDB() ***");
        /*
        boolean dbIsOpen = false;
        if(db.isOpen())
            dbIsOpen = true;
        */
        // Create or Open LostDogPoster database if necessary
        try {
            db = this.openOrCreateDatabase("LostDogPoster.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        } catch (Exception e) {
            Log.d(TAG, "*** ERROR opening or creating LostDogPoster.db e = " + e + " ***");
                //e.printStackTrace();
        }
        Log.d(TAG, "*** In saveDogInfoToDB() db = " + db + " ***");
        // Create States table if it does not exist
        createDogTable(db);
        //if (!tableHasData(db)) {
            //Log.d(TAG, "*** In onCreate() table does NOT have data ***");
            populateDogTable(db);
        //}

    }
    /*
    private static final String TABLE_NAME = "Dog";
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String MICROCHIP_NUMBER = "microchip_number";
    public static final String OWNER_NAME = "owner_name";
    public static final String OWNER_NUMBER = "owner_number";
    */
    public void createDogTable(SQLiteDatabase db)
    {
        Log.d(TAG, "*** In createDogTable() db = " + db + " ***");
        String sql =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                        + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + NAME + " TEXT NOT NULL, "
                        + DESCRIPTION + " TEXT NOT NULL,"
                        + MICROCHIP_NUMBER + " TEXT,"
                        + OWNER_NAME + " TEXT NOT NULL,"
                        + OWNER_PHONE + " TEXT NOT NULL"
                        + ");";
        Log.d(TAG, "*** In createDogTable() sql = " + sql + " ***");
        // Create dog table
        try{
            db.execSQL(sql);
        }
        catch (Exception e){
            Log.d(TAG, "*** ERROR In createDogTable() e = " + e + " ***");
            //e.printStackTrace();
        }
    }
    // Save data to DB
    public void populateDogTable(SQLiteDatabase db) {

        Log.d(TAG, "*** In populateDogTable() ***");
        ContentValues values = new ContentValues();

        String name = hmDogInfo.get("Name");
        Log.d(TAG, "In populateDogTable name = " + name);
        String description = hmDogInfo.get("Description");
        Log.d(TAG, "In populateDogTable description = " + description);
        String microchipNumber = hmDogInfo.get("MicrochipNumber");
        Log.d(TAG, "In populateDogTable  microchipNumber = " + microchipNumber);
        String ownerName = hmDogInfo.get("OwnerName");
        Log.d(TAG, "In populateDogTable ownerName = " + ownerName);
        String ownerPhone = hmDogInfo.get("OwnerPhone");
        Log.d(TAG, "In populateDogTable ownerPhone = " + ownerPhone);

        try {
            // ADD PATH to IMAGE
            // Put dog info in Dog table
            values.put(NAME, name);
            values.put(DESCRIPTION, description);
            if(microchipNumber != null && microchipNumber.length() > 0)
                values.put(MICROCHIP_NUMBER, microchipNumber);
            values.put(DESCRIPTION, description);
            values.put(OWNER_NAME, ownerName);
            values.put(OWNER_PHONE, ownerPhone);

            db.insert(TABLE_NAME, null, values);
            Log.d(TAG, "*** In populateDogTable data has been saved ***");
        } catch (Exception e) {
            Log.d(TAG, "*** Error populating Dog table e = "  + e + " ***");
            e.printStackTrace();
        }

  }

    /**
     * ****************************************************************
     * boolean tableHasData(SQLiteDatabase db)
     * Method to determine whether the Dog table has been populated.
     * Returns true or false.
     * *****************************************************************
     */
    public boolean tableHasData(SQLiteDatabase db)
    {
        boolean hasData = false;

        Log.d(TAG, "*** In tableHasData() ***");

        try {
            Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME, null);
            cursor.moveToFirst();
            if (cursor.getInt(0) > 0)
                hasData = true; // there are rows in the table
            Log.d(TAG, "*** In tableHasData() hasData = " + hasData + " ***");
            cursor.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return(hasData);
    }


    public Bitmap getResizedBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        //int newWidth = width / 3; // Orig
        //int newHeight = height / 3;
        int newWidth = width / 4; // Best
        int newHeight = height / 4;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "*** In onActivityResult() requestCode = " + requestCode + " ***");
        Log.d(TAG, "*** In onActivityResult() resultCode = " + resultCode + " ***");
        Log.d(TAG, "*** In onActivityResult() data = " + data + " ***");

        // Here we need to check if the activity that was triggered was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Read picked image data - its URI
            Uri pickedImage = data.getData();
            Log.d(TAG, "*** In onActivityResult() URi pickedImage  = " + pickedImage + " ***");

            // Read picked image path using content resolver
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

            // At the end close the cursor
            cursor.close();

            // Disable Choose Picture button
            choosePicBtn.setEnabled(false);

            mkToast("Click the Save Button to save your dog info",
                    Toast.LENGTH_SHORT);

        }
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