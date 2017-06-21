package com.textanddrive.assesmentdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.textanddrive.assesmentdemo.models.User;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private AppCompatEditText etEmail;
    private AppCompatEditText etPassword;
    private AppCompatRadioButton rbTeacher;
    private AppCompatRadioButton rbStudent;
    private AppCompatCheckBox cbRemember;
    private AppCompatButton btnLogin;
    private AppCompatTextView tvSignup;
    String email;
    String password;
    String identity;
    SQLiteDatabaseHelper helper;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    private Boolean isTeacher;

    private void initViews() {
        etEmail = (AppCompatEditText) findViewById(R.id.et_email1);
        etPassword = (AppCompatEditText) findViewById(R.id.et_password1);
        rbTeacher = (AppCompatRadioButton) findViewById(R.id.rb_teacher1);
        rbStudent = (AppCompatRadioButton) findViewById(R.id.rb_student1);
        cbRemember = (AppCompatCheckBox) findViewById(R.id.cb_remember);
        btnLogin = (AppCompatButton) findViewById(R.id.btn_login);
        tvSignup = (AppCompatTextView) findViewById(R.id.tv_signup);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        helper = new SQLiteDatabaseHelper(this);
        sharedpreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        editor = sharedpreferences.edit();

        initViews();

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                identity = "";
                if (rbTeacher.isChecked()) {
                    identity = "teacher";
                    isTeacher=true;
                    intent.putExtra("isTeacher", "isTeacher");
                } else if (rbStudent.isChecked()) {
                    identity = "student";
                    isTeacher=false;
                    intent.putExtra("isStudent", "isStudent");
                }
                if (helper.isExist(email, password, identity)) {
                    if (cbRemember.isChecked()) {
                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putString("identity", identity);
                        editor.commit();
                    } else {
                        editor.clear();
                        editor.commit();
                    }

                    startActivity(intent);
                    LoginActivity.this.finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Invalid Credentials");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            }
        });

        List<User> list = helper.viewUser();
        for (User user1 : list) {
            Log.e("TEST", "Users : " + user1.toString());
        }
    }

    @Override
    protected void onResume() {
        sharedpreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        if (sharedpreferences.contains("email") && sharedpreferences.contains("password") && sharedpreferences.contains("identity")) {
            Intent i = new Intent(getApplicationContext(), DrawerActivity.class);
            startActivity(i);
            LoginActivity.this.finish();
        }
        super.onResume();
    }
}
