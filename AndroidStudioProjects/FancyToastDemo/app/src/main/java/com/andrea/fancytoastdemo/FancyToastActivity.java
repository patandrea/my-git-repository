package com.andrea.fancytoastdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class FancyToastActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fancy_toast);
        mkFancyToast("This is Fancy Toast Message\nSee Fancy Toast", R.color.teal);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fancy_toast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void mkFancyToast(String text, int c)
    {
        Context con = getApplicationContext();
        TextView tv = new TextView(con);
        tv.setTextColor(c);
        tv.setText(text);
        Toast t = new Toast(con);
        t.setDuration(Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        t.setView(tv);
        t.show();
    }
}
