package com.example.niraj.demoapp.PassingData;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niraj.demoapp.R;

public class Demo2SignupActivity extends AppCompatActivity {

    public static final String DEMO_FOUR_ACTIVITY = "MyPref";
    public static final String dbusername = "nameKey";
    public static final String dbemail = "emailKey";
    public static final String dbpassword = "passwordKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2_signup);
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void onClickCreatenew(View view) {
        EditText username = (EditText) findViewById(R.id.et_newusername);
        EditText emailid = (EditText) findViewById(R.id.et_newemail);
        EditText password = (EditText) findViewById(R.id.et_newpassword);

        if (!(emailid.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
            emailid.setError("Invalid Email");
        } else {
            SharedPreferences.Editor editor = getSharedPreferences(DEMO_FOUR_ACTIVITY, MODE_PRIVATE).edit();
            editor.putString(dbusername, username.getText().toString());
            editor.putString(dbemail, emailid.getText().toString());
            editor.putString(dbpassword, password.getText().toString());
            editor.apply();
            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}

