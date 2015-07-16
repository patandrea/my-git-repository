package com.andrea.insertretreiveimagefromdb;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class InsertRetrievePhotoActivity extends Activity {
    private static final String TAG = "InsertRetrievePhotoActivity";
    private Button insertBtn, retrieveBtn;
    private ImageView image;// ImageView
    private TextView tvTitle;
    private SQLiteDatabase db;
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String PICTURE = "picture";
    public static final String TABLE_NAME = "Photo";
    private byte[] img = null;
    private Cursor c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_retrieve_photo);

        // Get references to the GUI objects
        insertBtn = (Button) findViewById(R.id.btnInsert);
        retrieveBtn = (Button) findViewById(R.id.btnRetrieve);
        image = (ImageView) findViewById(R.id.image);

    }

    public void doClick(View v) {
        Log.d(TAG, "*** In doClick ***");
        switch (v.getId()) {
            case R.id.btnInsert:
                Log.d(TAG, "*** In doClick Insert Btn clicked ***");
                saveToDB();
                break;

            case R.id.btnRetrieve:
                Log.d(TAG, "*** In doClick Retrieve Btn clicked ***");
                //retrieveFromDB();
                break;

            default:
                Log.e(TAG, "*** Unknown Button Clicked ***");
                break;
        }
    }


    public void saveToDB(){
        try {
            db = this.openOrCreateDatabase("InsertRetrievePhoto.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        }
        catch (Exception e) {
            Log.d(TAG, "*** ERROR opening or creating LostDogPoster.db e = " + e + " ***");
            //e.printStackTrace();
        }

        Bitmap b=BitmapFactory.decodeResource(getResources(), R.drawable.ziva_cow_palace);
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, bos);
        img = bos.toByteArray();

        // Create States table if it does not exist
        createPhotoTable(db);
        if (!tableHasData(db)) {
            Log.d(TAG, "*** In onCreate() table does NOT have data ***");
            populatePhotoTable(db);
        }

    }

    public void retrieveFromDB(){
        String[] col={PICTURE};
        c = db.query(TABLE_NAME, col, null, null, null, null, null);

        if(c!=null){
            c.moveToFirst();
            do{
                img = c.getBlob(c.getColumnIndex(PICTURE));
                Log.d(TAG, "*** In retrieveFromDB img = " + img + " ***");
            }while(c.moveToNext());
        }
        Bitmap b1=BitmapFactory.decodeByteArray(img, 0, img.length);

        image.setImageBitmap(b1);
        mkToast("Retrieved successfully");
        Log.d(TAG, "*** Retrieved successfully ***");
    }

    public void createPhotoTable(SQLiteDatabase db)
    {
        Log.d(TAG, "*** In createPhotoTable() db = " + db + " ***");
        String sql =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                        + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + PICTURE + " BLOB"
                        + ");";
        Log.d(TAG, "*** In createPhotoTable() sql = " + sql + " ***");
        // Create dog table
        try{
            db.execSQL(sql);
        }
        catch (Exception e){
            Log.d(TAG, "*** ERROR In createPhotoTable() e = " + e + " ***");
            //e.printStackTrace();
        }
    }
    public void populatePhotoTable(SQLiteDatabase db) {

        Log.d(TAG, "*** In populateDogTable() ***");
        ContentValues values = new ContentValues();

        String name = "Ziva";
        Log.d(TAG, "In populateDogTable name = " + name);

        try {
            // Put dog info in Dog table
            values.put(PICTURE, img);
            db.insert(TABLE_NAME, null, values);
            mkToast("Inserted Successfully");
            Log.d(TAG, "*** In populatePhotoTable Inserted Successfully ***");
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

    /****************************************************************
     * MkToast(String text)
     * SHORT duration
     ******************************************************************/
    private void mkToast(String text)
    {
        Context con = getApplicationContext();
        Toast t =
                Toast.makeText(con, text, Toast.LENGTH_LONG);
        t.show();
    }


}
