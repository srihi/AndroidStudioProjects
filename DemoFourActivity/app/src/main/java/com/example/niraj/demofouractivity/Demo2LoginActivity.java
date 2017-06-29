package com.example.niraj.demofouractivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.niraj.demofouractivity.Demo2SignupActivity.DEMO_FOUR_ACTIVITY;
import static com.example.niraj.demofouractivity.Demo2SignupActivity.dbemail;
import static com.example.niraj.demofouractivity.Demo2SignupActivity.dbpassword;
import static com.example.niraj.demofouractivity.Demo2SignupActivity.dbusername;

public class Demo2LoginActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2_login);

        sharedpreferences = getSharedPreferences(DEMO_FOUR_ACTIVITY, MODE_PRIVATE);
        sharedpreferences.getString(dbusername, "");
        sharedpreferences.getString(dbemail, "");
        sharedpreferences.getString(dbpassword, "");
    }

    public void onClickSignup(View view) {
        Intent i = new Intent(Demo2LoginActivity.this, Demo2SignupActivity.class);
        startActivity(i);

    }

    public void onClickSignin(View view) {

        EditText username = (EditText) findViewById(R.id.et_username);
        EditText password = (EditText) findViewById(R.id.et_password);

        String dbu = sharedpreferences.getString(dbusername, "");
        String dbp = sharedpreferences.getString(dbpassword, "");
        String u = username.getText().toString();
        String p = password.getText().toString();

        if (!u.matches(dbu) || !p.matches(dbp)) {
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(this, Demo2WelcomeActivity.class);
            startActivity(i);
        }
    }
}
