package com.example.niraj.demoapp.PassingData;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.niraj.demoapp.R;

public class Demo1UserDetailsActivity extends AppCompatActivity {
    final String TAG = "DetailsActivity1";
    TextView username;
    TextView uname;
    TextView email;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String name = "nameKey";
    public static final String pass = "passwordKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1_user_details);
        findAllViewsById();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        if (sharedpreferences.contains(name)) {
            username.setText("Logged in as : " + sharedpreferences.getString(name, ""));
        }

        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    private void findAllViewsById() {
        username = (TextView) findViewById(R.id.tv_username);
        uname = (TextView) findViewById(R.id.tv_name);
        email = (TextView) findViewById(R.id.tv_email);
    }

    public void onClickEdit(View v) {
        Intent intent = new Intent(Demo1UserDetailsActivity.this, Demo1EditDetailsActivity.class);
        intent.putExtra("name", uname.getText().toString());
        intent.putExtra("email", email.getText().toString());
        startActivityForResult(intent, 06);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 06) {
            if (resultCode == Activity.RESULT_OK) {
                uname.setText(data.getStringExtra("name"));
                email.setText(data.getStringExtra("email"));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                return;
            }
        }
    }

    public void onClickLogout(View view) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(Demo1UserDetailsActivity.this, Demo1LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
