package com.example.niraj.demofouractivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static com.example.niraj.demofouractivity.Demo2SignupActivity.DEMO_FOUR_ACTIVITY;

public class Demo2NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2_next);
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
