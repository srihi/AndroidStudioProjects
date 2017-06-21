package com.textanddrive.practicaltest1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private AppCompatButton btnViewStudents;
    private AppCompatButton btnTakeAttendance;

    private void initViews() {
        btnViewStudents = (AppCompatButton) findViewById(R.id.btn_view_student);
        btnTakeAttendance = (AppCompatButton) findViewById(R.id.btn_take_attendance);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        initViews();
        btnViewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewStudentsActivity.class);
                intent.putExtra("viewStudent", "viewStudent");
                startActivity(intent);
                finish();
            }
        });
        btnTakeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewStudentsActivity.class);
                intent.putExtra("takeAttendance", "takeAttendance");
                startActivity(intent);
                finish();
            }
        });
    }
}
