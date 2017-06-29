package com.example.niraj.demoapp.LayoutDemos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.niraj.demoapp.R;

public class TableLayoutBuyticketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_layout_buyticket);
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
}
