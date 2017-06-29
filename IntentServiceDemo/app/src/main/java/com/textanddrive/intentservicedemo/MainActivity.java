package com.textanddrive.intentservicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickStart(View view) {
        EditText input = (EditText) findViewById(R.id.edittext);
        String strInputMsg = input.getText().toString();
        Intent msgIntent = new Intent(this,MyIntentService.class);
        msgIntent.putExtra("msg",strInputMsg);
        startService(msgIntent);
    }
}
