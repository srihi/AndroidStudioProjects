package com.textanddrive.sqlite1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.textanddrive.sqlite1.models.Student;

import java.util.List;

public class AddStudentActivity extends AppCompatActivity {
    private EditText etName;
    private Button btnSave;
    SQLiteDatabaseHelper helper;
    private boolean isFromEdit;

    private void initView() {
        etName = (EditText) findViewById(R.id.et_name);
        btnSave = (Button) findViewById(R.id.btn_save);
    }

    private void setStudent(Intent i) {
        etName.setText(i.getStringExtra("name"));
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
            setStudent(i);
        } else {
            isFromEdit = false;
            btnSave.setText("Save");
        }
    }

    public void onClickSave(View view) {
        String name = etName.getText().toString();
        if (name.equals("")) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            Student student = new Student(name);
            if (isFromEdit) {
                helper.updateStudent(Integer.parseInt(getIntent().getStringExtra("id")), etName.getText().toString());
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
        etName.setText("");
        List<Student> list = helper.viewStudent();
        for (Student student1 : list) {
            Log.e("TEST", "Record : " + student1.toString());
        }
    }
}
