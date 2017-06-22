package com.textanddrive.projecttextanddrive;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MessageLogActivity extends AppCompatActivity {

    private Toolbar toolbarMessagelog;

    private AppCompatTextView sendMsg;
    Set<String> incommingnumberMessagelogsetlist = new HashSet<String>();
    Set<String> outgoingMessagelogsetlist = new HashSet<String>();
    SharedPreferences Messageloglistpref;
    HashMap<String, String> MessageLOGSLists = new HashMap<String, String>();
    ArrayList<HashMap<String, String>> MesagelogList;

    private ListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_log);


        toolbarMessagelog = (Toolbar) findViewById(R.id.toolbar_message_log);
        toolbarMessagelog.setTitle("");
        setSupportActionBar(toolbarMessagelog);




        sendMsg = (AppCompatTextView) findViewById(R.id.incoming_msg);

        SharedPreferences pref = getSharedPreferences("contact",MODE_PRIVATE);
        String numberMsg = pref.getString("number",null);
        String msg = pref.getString("msg",null);
        if(numberMsg != null){
            sendMsg.setText("Mobile No. :" + numberMsg+"\nMessage :"+msg);
        }
        /*Intent intent = getIntent();
        String numberMsg = intent.getStringExtra("number");*/

        /*SharedPreferences pref = getSharedPreferences("contact",MODE_PRIVATE);
        String mblNo = pref.getString("mblno",null);
        String messageReceive = pref.getString("msg",null);
        String message = pref.getString("txtmsg",null);*/

        /*Intent intent = getIntent();
        *//*String mblNo = intent.getStringExtra("number");*//*
        String messageReceive = intent.getStringExtra("sms_Str");
        sendMsg.setText(messageReceive);*/

        /*Intent intent = getIntent();
        String mblNum = intent.getStringExtra("number");
        String message = intent.getStringExtra("message");*/

        /*sendSMS(mblNo,msgText);*/
        /*incomingMsg.setText("Mobile No. : " + mblNo + "\n" + "Message : " + msgText);*/

    }


    /*public void sendSMS(String phoneNo, String msg) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null, msg, sentPI, deliveredPI);
    }*/
}


