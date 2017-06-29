package com.example.niraj.demofouractivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static com.example.niraj.demofouractivity.Demo2SignupActivity.DEMO_FOUR_ACTIVITY;
import static com.example.niraj.demofouractivity.Demo2SignupActivity.dbemail;

public class Demo2WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2_welcome);

        SharedPreferences sharedpreferences = getSharedPreferences(DEMO_FOUR_ACTIVITY, MODE_PRIVATE);

        if (sharedpreferences.contains(dbemail)) {
            TextView email = (TextView) findViewById(R.id.tv_email);
            email.setText(sharedpreferences.getString(dbemail, ""));
        }
    }

    public void onClickNext(View view) {
        Intent i = new Intent(getApplicationContext(), Demo2NextActivity.class);
        startActivity(i);
        finish();
    }
}
