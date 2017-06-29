package com.textanddrive.expensemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
    }

    public void onClickViewExpense(View view) {
        Intent intent = new Intent(MainActivity.this, ViewExpenseActivity.class);
        startActivity(intent);
    }

    public void onClickAddExpense(View view) {
        Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
        startActivity(intent);
    }
}
