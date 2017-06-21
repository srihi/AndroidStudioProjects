package com.textanddrive.jsonparsingdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.textanddrive.jsonparsingdemo.adapters.UserAdapter;
import com.textanddrive.jsonparsingdemo.models.User;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {
    SQLiteDatabaseHelper helper;
    private List<User> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        helper = new SQLiteDatabaseHelper(this);
        userList = helper.viewUser();
        for (User user1 : userList) {
            Log.e("TEST", "FROM DB : " + user1.toString());
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        userAdapter = new UserAdapter(userList, ViewActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);
    }
}
