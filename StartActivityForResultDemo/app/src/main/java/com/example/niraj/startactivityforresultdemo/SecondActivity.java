package com.example.niraj.startactivityforresultdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        EditText name1 = (EditText) findViewById(R.id.et1_name);
        EditText email1 = (EditText) findViewById(R.id.et1_email);
        EditText eno1 = (EditText) findViewById(R.id.et1_eno);
        EditText height1 = (EditText) findViewById(R.id.et1_height);
        Button save = (Button) findViewById(R.id.btn_save);
        Button edit = (Button) findViewById(R.id.btn_edit);

        Intent intent = getIntent();
        name1.setText(intent.getStringExtra("name"));
        email1.setText(intent.getStringExtra("email"));
        eno1.setText(intent.getStringExtra("eno"));
        height1.setText(intent.getStringExtra("height"));
        save.setEnabled(false);
        edit.setEnabled(true);
    }

    public void onEdit(View v) {
        Bundle b = getIntent().getExtras();
        EditText name1 = (EditText) findViewById(R.id.et1_name);
        EditText email1 = (EditText) findViewById(R.id.et1_email);
        EditText eno1 = (EditText) findViewById(R.id.et1_eno);
        EditText height1 = (EditText) findViewById(R.id.et1_height);
        Button save = (Button) findViewById(R.id.btn_save);
        Button edit = (Button) findViewById(R.id.btn_edit);

        name1.setEnabled(true);
        name1.setText(b.getCharSequence("name"));
        email1.setEnabled(true);
        email1.setText(b.getCharSequence("email"));
        eno1.setEnabled(true);
        eno1.setText(b.getCharSequence("eno"));
        height1.setEnabled(true);
        height1.setText(b.getCharSequence("height"));

        save.setEnabled(true);
        edit.setEnabled(false);
    }

    public void onSave(View v) {

        Intent intent = new Intent();
        EditText name1 = (EditText) findViewById(R.id.et1_name);
        EditText email1 = (EditText) findViewById(R.id.et1_email);
        EditText eno1 = (EditText) findViewById(R.id.et1_eno);
        EditText height1 = (EditText) findViewById(R.id.et1_height);

        intent.putExtra("name1", name1.getText().toString());
        intent.putExtra("email1", email1.getText().toString());
        intent.putExtra("eno1", eno1.getText().toString());
        intent.putExtra("height1", height1.getText().toString());

        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();

        super.onBackPressed();
    }
}
