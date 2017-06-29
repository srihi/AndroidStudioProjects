package com.example.niraj.demoapp.PassingData;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.niraj.demoapp.R;

public class PassingDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passing_data);
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void onClickDemo_1(View view) {

        Intent intent = new Intent(this, Demo1LoginActivity.class);
        startActivity(intent);

    }

    public void onClickDemo2(View view) {

        Intent intent = new Intent(this, Demo2LoginActivity.class);
        startActivity(intent);

    }
}
