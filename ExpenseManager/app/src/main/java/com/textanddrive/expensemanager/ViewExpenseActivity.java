package com.textanddrive.expensemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.textanddrive.expensemanager.adapters.ExpenseAdapter;
import com.textanddrive.expensemanager.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class ViewExpenseActivity extends AppCompatActivity {

    private List<Expense> expenseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    SQLiteDatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        helper = new SQLiteDatabaseHelper(this);
        expenseList = helper.getDataFromDB();
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        expenseAdapter = new ExpenseAdapter(this, expenseList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(expenseAdapter);

        List<Expense> list = helper.getDataFromDB();
        for (Expense expense : list) {
            Log.e("TEST", "Record : " + expense.toString());
        }
    }
}
