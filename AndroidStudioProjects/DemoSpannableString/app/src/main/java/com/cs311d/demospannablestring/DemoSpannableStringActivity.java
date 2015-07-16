package com.cs311d.demospannablestring;

import android.app.Activity;
import android.os.Bundle;
import android.text.style.UnderlineSpan;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.SubscriptSpan;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.method.LinkMovementMethod;
import android.graphics.Typeface;
import android.widget.TextView;
import android.view.Gravity;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;
import android.util.Log;


public class DemoSpannableStringActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        makeFancyString();
    }


    public void makeFancyString()
    {
        SpannableString ss = new SpannableString("Large\n\n" + "Bold\n\n" + "UnderLined\n\n" +
                "Italic\n\n" + "Strikethrough\n\n" + "Colored\n\n" +
                "Highlighted\n\n" + "K Superscript\n\n" +  "K Subscript\n\n" +
                "URL\n\n" + "Clickable\n\n");

// Large
// float - twice bigger 0, 5 - start-end of Bold
// last 0 - flag how large should look very specialized
        //ss.setSpan(new RelativeSizeSpan(2f, 0, 5, 0));
        ss.setSpan(new RelativeSizeSpan(2f), 0, 5, 0);

// Bold
        //ss.setSpan(new StyleSpan(Typeface.BOLD, 7, 11, 0));
        ss.setSpan(new StyleSpan(Typeface.BOLD), 7, 11, 0);

// Underline
        ss.setSpan(new UnderlineSpan(), 13, 23, 0);

// Italic
        ss.setSpan(new StyleSpan(Typeface.ITALIC), 25, 31, 0);

// Strikethrough
        ss.setSpan(new StrikethroughSpan(), 33, 46, 0);

// colored
        ss.setSpan(new ForegroundColorSpan(Color.GREEN), 48, 55, 0);
        ss.setSpan(new BackgroundColorSpan(Color.CYAN), 57, 68, 0);

        ss.setSpan(new SuperscriptSpan(), 72, 83, 0);
        ss.setSpan(new RelativeSizeSpan(0.5f), 72, 83, 0);
        ss.setSpan(new SubscriptSpan(), 87, 96, 0);
        ss.setSpan(new RelativeSizeSpan(0.5f), 87, 96, 0);
        ss.setSpan(new URLSpan("http://www.google.com"), 98, 101, 0);

        ClickableSpan cs = new ClickableSpan()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getBaseContext(), "Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        };

        TextView tv = new TextView(this);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.WHITE);
        tv.setText(ss);
        setContentView(tv);
    }
}
