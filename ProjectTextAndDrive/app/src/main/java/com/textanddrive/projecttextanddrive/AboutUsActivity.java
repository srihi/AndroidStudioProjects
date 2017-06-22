package com.textanddrive.projecttextanddrive;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;

public class AboutUsActivity extends AppCompatActivity {

    Toolbar toolbarAboutus;
    private AppCompatTextView link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        toolbarAboutus = (Toolbar) findViewById(R.id.toolbar_aboutus);
        toolbarAboutus.setTitle("");
        setSupportActionBar(toolbarAboutus);

        //Create Link For about App
    }
}
