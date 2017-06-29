package com.example.niraj.demoapp.PassingData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.niraj.demoapp.R;

import static com.example.niraj.demoapp.PassingData.Demo2SignupActivity.DEMO_FOUR_ACTIVITY;
import static com.example.niraj.demoapp.PassingData.Demo2SignupActivity.dbemail;

public class Demo2WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2_welcome);

        SharedPreferences sharedpreferences = getSharedPreferences(DEMO_FOUR_ACTIVITY, MODE_PRIVATE);

        if (sharedpreferences.contains(dbemail)) {
            TextView emai= (TextView) findViewById(R.id.tv_email);
            emai.setText(sharedpreferences.getString(dbemail, ""));
        }

        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void onClickNext(View view) {
        Intent i = new Intent(getApplicationContext(), Demo2NextActivity.class);
        startActivity(i);
        finish();
    }
}
