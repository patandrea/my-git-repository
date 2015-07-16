package com.andrea.sendmaildemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class SendMailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_send_mail);
        sendEmail();
    }

    public void sendEmail()
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        String subject = "Hello";
        String name = "Andrea";
        String body = name + ", " + "Long time no  see";
        String[] cc = new String[] {"apattson@earthlink.net"};
        String[] bcc = new String[] {"apattson@earthlink.net"};
        // Recipients addresses
        String[] recipients = new String[]{"apattson@gmail.com"};
        i.putExtra(Intent.EXTRA_EMAIL, recipients);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        i.putExtra(Intent.EXTRA_CC, cc);
        i.putExtra(Intent.EXTRA_BCC, bcc);

        // specify type of email
        i.setType("message/rfc822"); // Mime Type - rfc822"covers more
        //"Plain/text"

        startActivity(i); // email is sent

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_mail, menu);
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
}
