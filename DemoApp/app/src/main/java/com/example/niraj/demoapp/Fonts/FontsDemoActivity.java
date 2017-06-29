package com.example.niraj.demoapp.Fonts;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.example.niraj.demoapp.R;

public class FontsDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fonts_demo);

        TextView textViewregular = (TextView) findViewById(R.id.tv_regular);
        TextView textViewbold = (TextView) findViewById(R.id.tv_bold);
        TextView textViewitalic = (TextView) findViewById(R.id.tv_italic);

        textViewbold.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Bold.ttf"));
        textViewitalic.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Italic.ttf"));
        textViewregular.setTypeface(Typeface.createFromAsset(getAssets(), "Fonts/Ubuntu-Regular.ttf"));
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}
