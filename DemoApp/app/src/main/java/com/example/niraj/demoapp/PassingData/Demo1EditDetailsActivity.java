package com.example.niraj.demoapp.PassingData;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.niraj.demoapp.R;

public class Demo1EditDetailsActivity extends AppCompatActivity {
    final String TAG = "EditActivity1";
    EditText name1;
    EditText email1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1_edit_details);

        findAllViewsById();

        Intent intent = getIntent();
        name1.setText(intent.getStringExtra("name"));
        email1.setText(intent.getStringExtra("email"));

        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    private void findAllViewsById() {
        name1 = (EditText) findViewById(R.id.et_name);
        email1 = (EditText) findViewById(R.id.et_email);
    }

    public void onClickSubmit(View view) {
        Intent intent = new Intent();
        findAllViewsById();
        intent.putExtra("name", name1.getText().toString());
        intent.putExtra("email", email1.getText().toString());
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
