package com.example.niraj.demoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.niraj.demoapp.Fonts.FontsDemoActivity;
import com.example.niraj.demoapp.LayoutDemos.Layouts2Activity;
import com.example.niraj.demoapp.LifeCycle.LifeCycleFirstActivity;
import com.example.niraj.demoapp.PassingData.PassingDataActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void lifecycle(View v) {
        Intent intent = new Intent(this, LifeCycleFirstActivity.class);
        startActivity(intent);
    }

    public void layouts(View v) {

        Intent intent = new Intent(this, Layouts2Activity.class);
        startActivity(intent);
    }

    public void passdata(View v) {

        Intent intent = new Intent(this, PassingDataActivity.class);
        startActivity(intent);
    }

    public void fontsdemo(View view) {
        Intent intent = new Intent(this, FontsDemoActivity.class);
        startActivity(intent);
    }
}
