package com.example.niraj.demoapp.LayoutDemos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.niraj.demoapp.R;

public class LinearLayoutAmenitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout_amenities);
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}
