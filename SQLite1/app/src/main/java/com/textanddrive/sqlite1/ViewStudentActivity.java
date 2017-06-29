package com.textanddrive.sqlite1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.textanddrive.sqlite1.adapters.StudentAdapter;
import com.textanddrive.sqlite1.models.Student;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentActivity extends AppCompatActivity {
    private List<Student> studentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    SQLiteDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);

        helper = new SQLiteDatabaseHelper(this);
        studentList = helper.viewStudent();
        recyclerView = (RecyclerView) findViewById(R.id.rv_student_list);
        studentAdapter = new StudentAdapter(this, studentList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(studentAdapter);

        List<Student> list = helper.viewStudent();
        for (Student student1 : list) {
            Log.e("TEST", "Record : " + student1.toString());
        }
    }
}
