package com.textanddrive.sqlitecrudoperations;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.textanddrive.sqlitecrudoperations.models.Student;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddStudentActivity extends AppCompatActivity {
    private AppCompatEditText etName;
    private AppCompatEditText etEmail;
    private AppCompatEditText etPhoneNumber;
    private AppCompatEditText etBirthDate;
    private AppCompatRadioButton rbMale;
    private AppCompatRadioButton rbFemale;
    private AppCompatTextView tvFile;
    private AppCompatImageView imageView;
    private Button btnSelect;
    private Button btnSave;
    String name;
    String email;
    String phoneNumber;
    String birthDate;
    String gender;
    Bitmap image;
    SQLiteDatabaseHelper helper;
    private boolean isFromEdit;

    private void initView() {
        etName = (AppCompatEditText) findViewById(R.id.et_name);
        etEmail = (AppCompatEditText) findViewById(R.id.et_email);
        etPhoneNumber = (AppCompatEditText) findViewById(R.id.et_phone);
        etBirthDate = (AppCompatEditText) findViewById(R.id.et_birthdate);
        rbMale = (AppCompatRadioButton) findViewById(R.id.rb_male);
        rbFemale = (AppCompatRadioButton) findViewById(R.id.rb_female);
        tvFile = (AppCompatTextView) findViewById(R.id.tv_file);
        btnSelect = (Button) findViewById(R.id.btn_select);
        imageView = (AppCompatImageView) findViewById(R.id.imageView);
        btnSave = (Button) findViewById(R.id.btn_save);
    }

    private void setStudent(Intent i) {
        etName.setText(i.getStringExtra("name"));
        etEmail.setText(i.getStringExtra("email"));
        etPhoneNumber.setText(i.getStringExtra("phone"));
        etBirthDate.setText(i.getStringExtra("birthdate"));
        gender = i.getStringExtra("gender");
        if (gender.equals("male")) {
            rbMale.setChecked(true);
        } else if (gender.equals("female")) {
            rbFemale.setChecked(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);

        helper = new SQLiteDatabaseHelper(this);
        initView();

        Intent i = getIntent();
        if (i.hasExtra("isFoundEdit")) {
            isFromEdit = true;
            btnSave.setText("Update");
            setTitle("Update Student");
            setStudent(i);
        } else {
            isFromEdit = false;
            btnSave.setText("Save");
        }
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            etBirthDate.setText(sdf.format(myCalendar.getTime()));
        }
    };

    public void onClickDate(View view) {
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onClickSave(View view) {
        name = etName.getText().toString();
        email = etEmail.getText().toString();
        phoneNumber = etPhoneNumber.getText().toString();
        birthDate = etBirthDate.getText().toString();
        gender = "";

        if (name.equals("") || !(email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) || phoneNumber.length() != 10 || birthDate.equals("") || (!rbMale.isChecked() && !rbFemale.isChecked())) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            if (rbMale.isChecked()) {
                gender = "male";
            } else if (rbFemale.isChecked()) {
                gender = "female";
            }
            Student student = new Student(name, email, phoneNumber, birthDate, gender, image);
            if (isFromEdit) {
                helper.updateStudent(Integer.parseInt(getIntent().getStringExtra("id")), name, email, phoneNumber, birthDate, gender, image);
                Toast.makeText(this, "Record Updated", Toast.LENGTH_SHORT).show();
                Log.e("TEST", "updated");
                notify();
                finish();
            } else {
                helper.insertStudent(student);
                Toast.makeText(this, "New Record Added", Toast.LENGTH_SHORT).show();
                Log.e("TEST", "inserted");
                finish();
            }
        }
        List<Student> list = helper.viewStudent();
        for (Student student1 : list) {
            Log.e("TEST", "Record : " + student1.toString());
        }
    }
}
