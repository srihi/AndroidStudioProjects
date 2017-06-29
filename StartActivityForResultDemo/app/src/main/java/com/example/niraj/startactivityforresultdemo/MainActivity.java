package com.example.niraj.startactivityforresultdemo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText eno;
    EditText height;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.et_name);
        email = (EditText) findViewById(R.id.et_email);
        eno = (EditText) findViewById(R.id.et_eno);
        height = (EditText) findViewById(R.id.et_height);
        button = (Button) findViewById(R.id.button1);
    }

    public void onClick(View v) {

        Intent intent = new Intent(this, SecondActivity.class);

        intent.putExtra("name", name.getText().toString());
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("eno", eno.getText().toString());
        intent.putExtra("height", height.getText().toString());

        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                name.setText(data.getStringExtra("name1"));
                email.setText(data.getStringExtra("email1"));
                eno.setText(data.getStringExtra("eno1"));
                height.setText(data.getStringExtra("height1"));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                return;
            }
        }
    }
}
