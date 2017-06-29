package com.example.niraj.demoapp.PassingData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.niraj.demoapp.R;

import static com.example.niraj.demoapp.PassingData.Demo2SignupActivity.DEMO_FOUR_ACTIVITY;

public class Demo2NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2_next);
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void onClickLogout(View view) {

        SharedPreferences.Editor editor = getSharedPreferences(DEMO_FOUR_ACTIVITY, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, Demo2WelcomeActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
