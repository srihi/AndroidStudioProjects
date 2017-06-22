package com.textanddrive.projecttextanddrive;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.net.Inet4Address;
import java.util.Calendar;

public class DefaultMsgActivity extends AppCompatActivity {

    Toolbar toolbarDefaultMsg;
    private ListView listView = null;
    private AppCompatTextView tvListMsg;
    public static final String MSG = "msg";
    private AppCompatTextView tvHour;
    private AppCompatTextView tvMinute;
    private AppCompatTextView tvSecond;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_msg);

        toolbarDefaultMsg = (Toolbar) findViewById(R.id.toolbar_default_msg);
        toolbarDefaultMsg.setTitle("");
        setSupportActionBar(toolbarDefaultMsg);

        tvHour = (AppCompatTextView) findViewById(R.id.tv_def_hour);
        tvMinute = (AppCompatTextView) findViewById(R.id.tv_def_minute);
        tvSecond = (AppCompatTextView) findViewById(R.id.tv_def_second);
        tvListMsg = (AppCompatTextView) findViewById(R.id.tv_list_message1);


        SharedPreferences preferences = getSharedPreferences("my_db",MODE_PRIVATE);
        String msg= preferences.getString(MSG,null);
        int hrs = preferences.getInt("HH",0);
        int mins = preferences.getInt("MM",0);
        if(hrs != 0){
            tvHour.setText(hrs+"");
        }else{
            tvHour.setText("OO");
        }
        if(mins !=0){
            tvMinute.setText(mins+"");
        }else{
            tvMinute.setText("OO");
        }
        if(msg!=null){
            tvListMsg.setText(msg);
        }

        tvListMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView = new ListView(DefaultMsgActivity.this);
                final String[] messages = {"select message", "Can't talk now.I am driving and will be busy for " + tvHour.getText().toString() +" hr " + tvMinute.getText().toString() + " min", "Hey,almost done with the task at hand.I'll get back to you in around " + tvHour.getText().toString() + " hr " + tvMinute.getText().toString() +" min"
                        , "It's though for me to talk now,I'm on my commute. I'll get back to you in " + tvHour.getText().toString() + " hr " + tvMinute.getText().toString() + " min"};

                final AlertDialog alertDialog = new AlertDialog.Builder(DefaultMsgActivity.this).create();
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.popup_window, null);
                ListView listView = (ListView) view.findViewById(R.id.listview1);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DefaultMsgActivity.this, android.R.layout.simple_list_item_1, messages);
                listView.setAdapter(adapter);
                alertDialog.setView(view);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(DefaultMsgActivity.this, messages[i], Toast.LENGTH_SHORT).show();
                        String msg = messages[i].toString();
                        tvListMsg.setText(msg);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });



    }


    public void onClickTimer(View view) {
        tvHour = (AppCompatTextView) findViewById(R.id.tv_def_hour);
        tvMinute = (AppCompatTextView) findViewById(R.id.tv_def_minute);
        tvSecond = (AppCompatTextView) findViewById(R.id.tv_def_second);
        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(DefaultMsgActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Toast.makeText(getApplicationContext(), selectedHour + "", Toast.LENGTH_SHORT).show();

                tvHour.setText(selectedHour+"");
                tvMinute.setText(selectedMinute+"");
                /*timerStart();*/
                Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void onClickSaveSettings(View view) {

        SharedPreferences preferences1 = getSharedPreferences("toggle",MODE_PRIVATE);
        Boolean toggle = preferences1.getBoolean("toggle",false);

        if(toggle){
            String msg = tvListMsg.getText().toString();
        /*intent.putExtra("isFromDefaultMsg","isFromDefaultMsg");
        intent.putExtra(MSG,msg);*/
            SharedPreferences preferences = getSharedPreferences("my_db",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(MSG,msg);

            if(!tvHour.equals("OO") && !tvMinute.equals("OO") ){
                if(tvHour.equals("OO")){
                    editor.putInt("HH",0);
                }else{
                    editor.putInt("HH",Integer.parseInt(tvHour.getText().toString()));
                }
                if(tvMinute.equals("OO")){
                    editor.putInt("MM",0);
                }else{
                    editor.putInt("MM",Integer.parseInt(tvMinute.getText().toString()));
                }
            }
            editor.commit();
            finish();
        }
        else{

            Intent intent = new Intent(DefaultMsgActivity.this,HomeActivity.class);


            String msg = tvListMsg.getText().toString();
        /*intent.putExtra("isFromDefaultMsg","isFromDefaultMsg");
        intent.putExtra(MSG,msg);*/
            SharedPreferences preferences = getSharedPreferences("my_db",MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(MSG,msg);

            if(!tvHour.equals("OO") && !tvMinute.equals("OO") ){
                if(tvHour.equals("OO")){
                    editor.putInt("HH",0);
                }else{
                    editor.putInt("HH",Integer.parseInt(tvHour.getText().toString()));
                }
                if(tvMinute.equals("OO")){
                    editor.putInt("MM",0);
                }else{
                    editor.putInt("MM",Integer.parseInt(tvMinute.getText().toString()));
                }
            }
            editor.commit();
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
