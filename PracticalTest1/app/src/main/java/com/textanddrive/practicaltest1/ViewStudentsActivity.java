package com.textanddrive.practicaltest1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.textanddrive.practicaltest1.adapters.StudentAdapter;
import com.textanddrive.practicaltest1.models.Student;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentsActivity extends AppCompatActivity {

    private List<Student> studentList = new ArrayList<>();
    List<Student> tempList = new ArrayList<>();
    private StudentAdapter studentAdapter;
    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);
        final AppCompatButton btnSubmit = (AppCompatButton) findViewById(R.id.btn_submit_attendance);
        final Intent intent = getIntent();
        if (intent.hasExtra("viewStudent")) {
            btnSubmit.setVisibility(View.INVISIBLE);
            Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
            setSupportActionBar(t1);
            getSupportActionBar().setTitle("View Students");
        } else if (intent.hasExtra("takeAttendance")) {
            Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
            setSupportActionBar(t1);
            getSupportActionBar().setTitle("Take Attendance");
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentAdapter.activateButtons(true);
                Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
                setSupportActionBar(t1);
                getSupportActionBar().setTitle("Present Students");
                btnSubmit.setText("Confirm");
                List<Student> studentList1 = studentAdapter.getStudentist();
                for (int i = 0; i < studentList1.size(); i++) {
                    Student singleStudent = studentList1.get(i);
                    if (singleStudent.getAttendance()) {
                        data = singleStudent.getId();
                        Student student = new Student("" + data, "14118310700" + data, "Student " + data, "01-01-1995", true);
                        tempList.add(student);
                    }
                }
                studentList.clear();
                studentList.addAll(tempList);
                studentAdapter.notifyDataSetChanged();
                for (Student student1 : tempList) {
                    Log.e("TEST", "Checked : " + student1.toString());
                }
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewStudentsActivity.this);
                        alertDialog.setTitle("Confirm Submit");
                        alertDialog.setMessage("Are you sure?");
                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ViewStudentsActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "Attendence Submitted", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
            }
        });
        for (int i = 0; i < 50; i++) {
            Student student = new Student("" + (i + 1), "14118310700" + (i + 1), "Student " + (i + 1), "01-01-1995", false);
            studentList.add(student);
        }
        for (Student student2 : studentList) {
            Log.e("TEST", "Students : " + student2.toString());
        }
        studentAdapter = new StudentAdapter(ViewStudentsActivity.this, studentList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_student_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(studentAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ViewStudentsActivity.this, HomeActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
