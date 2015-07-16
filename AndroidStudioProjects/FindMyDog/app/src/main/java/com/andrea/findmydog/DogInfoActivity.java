package com.andrea.findmydog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DogInfoActivity extends Activity{
    private static final String TAG = "DogInfoActivity";
    private TextView tvDirections;
    private EditText etName, etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "*** In onCreate() ***");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dog_info);
    }

    public void getDogInfo(){
        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);

        String name = etName.getText().toString();
        String description = "Description:\n" + etDescription.getText().toString();
        Log.d(TAG, "*** In getDogInfo() name = " + name + " ***");
        Log.d(TAG, "*** In getDogInfo() description = " + description + " ***");

        backToLauncher(name, description);

    }

    public void backToLauncher(String name, String desc) {
        Log.d(TAG, "*** In backToLauncher name = " + name + " ***");
        Log.d(TAG, "*** In backToLauncher desc = " + desc + " ***");
        Intent data = new Intent();
        data.putExtra("Name", name);
        data.putExtra("Description", desc);
        //etName.setText("");

        setResult(RESULT_OK, data);
        finish();

    }

    public void doClick(View v) {
        Log.d(TAG, "*** In doClick ***");
        switch (v.getId()) {
            case R.id.btnSubmit:
                getDogInfo();
                break;
            case R.id.btnCancel: // Save Poster
                // save info to shared preferences
                //saveInfo();
                // Launch Create Contacts

                break;
            default:
                Log.e(TAG, "*** Unknown Button Clicked ***");
                break;
        }
    }

 }
