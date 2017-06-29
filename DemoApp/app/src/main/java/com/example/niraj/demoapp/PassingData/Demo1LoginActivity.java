package com.example.niraj.demoapp.PassingData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.niraj.demoapp.R;

public class Demo1LoginActivity extends AppCompatActivity {
    final String TAG = "LoginActivity1";

    EditText username;
    EditText password;
    Button button;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String name = "nameKey";
    public static final String pass = "passwordKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1_login);

        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        button = (Button) findViewById(R.id.btn_login);

        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void onClickLogin(View v) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String username1 = username.getText().toString();
        String password1 = password.getText().toString();
        editor.putString(name, username1);
        editor.putString(pass, password1);
        editor.commit();

        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            Intent intent = new Intent(Demo1LoginActivity.this, Demo1UserDetailsActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        if (sharedpreferences.contains(name)) {
            if (sharedpreferences.contains(pass)) {
                Intent i = new Intent(getApplicationContext(), Demo1UserDetailsActivity.class);
                startActivity(i);
            }
        }
        super.onResume();
    }
}