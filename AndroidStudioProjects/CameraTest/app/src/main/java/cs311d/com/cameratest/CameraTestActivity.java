package cs311d.com.cameratest;

import android.widget.EditText;
import android.app.Activity;
import android.os.Bundle;
import android.hardware.Camera;
import android.content.Intent;
import android.provider.MediaStore;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class CameraTestActivity extends Activity
{
    public static final String TAG = "CameraTestActivity";
    private final int PICTURE_ACTIVITY_CODE = 1;
    private final String FILENAME = "photo.jpg";
    String fileName;
    File fname = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    private void launchTakePhoto()
    {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Log.d(TAG, "*** In launchTakePhoto() fileName = " + fileName + " ***");

        try
        {
            fname = new File(getFilesDir() + "/" + fileName);
            Log.d(TAG, "*** In launchTakePhoto() fname = " + fname + " ***");

            FileOutputStream fout = new FileOutputStream(fname);
            Log.d(TAG, "*** In launchTakePhoto() fout = " + fout + " ***");
            // if file doesn't exist create it
            if (!fname.exists())
            {
                Log.d(TAG, "*** In launchTakePhoto() fname does not exist ***");
                fname.createNewFile();
            }

        }catch (Exception e)
        {
            Log.d(TAG, "*** In onCreate() file error: file name = " + fname + " ***");
        }

        Uri outputFileUri = Uri.fromFile(fname);
        Log.d(TAG, "*** In launchTakePhoto() outputFileUri =  " + outputFileUri + " ***");
        i.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(i, PICTURE_ACTIVITY_CODE);
    }

    /***********************************************************
     * doClick(View v)
     * Called when a button is clicked (the button in the
     * layout file main.xml attaches to this method with the
     * android:onClick attribute).
     **********************************************************/
    public void doClick(View v)
    {
        // Get file name from EditText
        fileName = getFileName();
        Log.d(TAG, "*** In doClick() fileName = " + fileName + " ***");
        launchTakePhoto();
    }

    /*************************************************************
     * String getFileName()
     * Return String: Name of photo File
     * Entered via EditText box by the User.
     *************************************************************/
    public String getFileName()
    {
        EditText et_pic_name = (EditText) findViewById(R.id.et_pic_name);
        //String photoName = et_pic_name.getText().toString();
        String photoName = et_pic_name.getText().toString() + ".jpg";
        Log.d(TAG, "*** In getFileName() photoName = " + photoName + " ***");

        return(photoName);
    }

    // Taking care of the result
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d(TAG, "*** In onActivityResult() request code = " + requestCode + " ***");
        Log.d(TAG, "*** In onActivityResult() result code = " + resultCode + " ***");
        Log.d(TAG, "*** In onActivityResult() data = " + data + " ***");
        if (requestCode == PICTURE_ACTIVITY_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                Log.d(TAG, "*** In onActivityResult() result code = " + resultCode + " ***");
                // show the picture
                ImageView photo = (ImageView) findViewById(R.id.photo);
                Uri inputFileUri = Uri.fromFile(fname);
                photo.setImageURI(inputFileUri);
            }
        }
    }

}
