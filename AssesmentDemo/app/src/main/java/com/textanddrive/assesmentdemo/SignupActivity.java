package com.textanddrive.assesmentdemo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.textanddrive.assesmentdemo.models.User;

public class SignupActivity extends AppCompatActivity {

    private AppCompatEditText etUserName;
    private AppCompatEditText etEmail;
    private AppCompatEditText etPassword;
    private AppCompatEditText etConfirmPassword;
    private AppCompatEditText etMobileNumber;
    private AppCompatRadioButton rbMale;
    private AppCompatRadioButton rbFemale;
    private AppCompatRadioButton rbTeacher;
    private AppCompatRadioButton rbStudent;
    private AppCompatButton btnSignUp;
    String userName;
    String email;
    String password;
    String confirmPassword;
    String mobileNumber;
    String gender;
    String identity;
    SQLiteDatabaseHelper helper;

    private void initViews() {
        etUserName = (AppCompatEditText) findViewById(R.id.et_username);
        etEmail = (AppCompatEditText) findViewById(R.id.et_email);
        etPassword = (AppCompatEditText) findViewById(R.id.et_password);
        etConfirmPassword = (AppCompatEditText) findViewById(R.id.et_confirm_password);
        etMobileNumber = (AppCompatEditText) findViewById(R.id.et_mobile_number);
        rbMale = (AppCompatRadioButton) findViewById(R.id.rb_male);
        rbFemale = (AppCompatRadioButton) findViewById(R.id.rb_female);
        rbTeacher = (AppCompatRadioButton) findViewById(R.id.rb_teacher);
        rbStudent = (AppCompatRadioButton) findViewById(R.id.rb_student);
        btnSignUp = (AppCompatButton) findViewById(R.id.btn_signup);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        helper = new SQLiteDatabaseHelper(this);
        initViews();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = etUserName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
                mobileNumber = etMobileNumber.getText().toString();
                gender = "";
                identity = "";

                if (userName.matches("")) {
                    etUserName.setError("Invalid Username");
                    etUserName.requestFocus();
                } else if (!(email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                    etEmail.setError("Invalid Email");
                    etEmail.requestFocus();
                } else if (password.length() < 6) {
                    etPassword.setError("Invalid Password");
                    etPassword.requestFocus();
                } else if (!(confirmPassword.isEmpty() || confirmPassword.matches(password))) {
                    etConfirmPassword.setError("Enter Same Password");
                    etConfirmPassword.requestFocus();
                } else if (mobileNumber.length() != 10) {
                    etMobileNumber.setError("Invalid Phone Number");
                    etMobileNumber.requestFocus();
                } else if (!rbMale.isChecked() && !rbFemale.isChecked()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage("Select Gender");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else if (!rbTeacher.isChecked() && !rbStudent.isChecked()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    builder.setMessage("Select Identity");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    if (rbMale.isChecked()) {
                        gender = "male";
                    } else if (rbFemale.isChecked()) {
                        gender = "female";
                    }
                    if (rbTeacher.isChecked()) {
                        identity = "teacher";
                    } else if (rbStudent.isChecked()) {
                        identity = "student";
                    }
                    User user = new User(userName, email, password, confirmPassword, mobileNumber, gender, identity);
                    helper.insertUser(user);
                    Toast.makeText(SignupActivity.this, "User Account Created.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
