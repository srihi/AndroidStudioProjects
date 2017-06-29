package com.example.niraj.demofouractivity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.niraj.demofouractivity.Demo2SignupActivity.DEMO_FOUR_ACTIVITY;
import static com.example.niraj.demofouractivity.Demo2SignupActivity.dbemail;
import static com.example.niraj.demofouractivity.Demo2SignupActivity.dbpassword;
import static com.example.niraj.demofouractivity.Demo2SignupActivity.dbusername;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        SharedPreferences sharedpreferences = getSharedPreferences(DEMO_FOUR_ACTIVITY, MODE_PRIVATE);

        if (sharedpreferences.contains(dbusername)) {
            TextView uname= (TextView) findViewById(R.id.nametv);
            uname.setText(sharedpreferences.getString(dbusername, ""));
        }
        if (sharedpreferences.contains(dbemail)) {
            TextView emai= (TextView) findViewById(R.id.emailtv);
            emai.setText(sharedpreferences.getString(dbemail, ""));
        }
        if (sharedpreferences.contains(dbpassword)) {
            TextView passw= (TextView) findViewById(R.id.passwordtv);
            passw.setText(sharedpreferences.getString(dbpassword, ""));
        }
    }
}
