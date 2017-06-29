package com.example.niraj.demoapp.LayoutDemos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.example.niraj.demoapp.R;

public class Layouts2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouts2);
        Toolbar t1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(t1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public void loginlayout(View v) {
        Intent intent = new Intent(Layouts2Activity.this, RelativeLayoutLoginActivity.class);
        startActivity(intent);
    }

    public void buyticketlayout(View v) {
        Intent intent = new Intent(Layouts2Activity.this, TableLayoutBuyticketActivity.class);
        startActivity(intent);
    }

    public void booktaxilayout(View v) {
        Intent intent = new Intent(Layouts2Activity.this, RelativeLayoutTaxibookingActivity.class);
        startActivity(intent);
    }

    public void amenitieslayouts(View v) {
        Intent intent = new Intent(Layouts2Activity.this, LinearLayoutAmenitiesActivity.class);
        startActivity(intent);
    }

    public void orvitolayouts(View v) {
        Intent intent = new Intent(Layouts2Activity.this, GridLayoutOrvitoActivity.class);
        startActivity(intent);
    }

    public void taxidetailslayouts(View v) {
        Intent intent = new Intent(Layouts2Activity.this, FrameLayoutTaxidetailsActivity.class);
        startActivity(intent);
    }

    public void tileslayouts(View v) {
        Intent intent = new Intent(Layouts2Activity.this, GridLayoutTilesActivity.class);
        startActivity(intent);
    }
}
