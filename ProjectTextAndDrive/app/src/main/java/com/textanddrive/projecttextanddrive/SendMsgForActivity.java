package com.textanddrive.projecttextanddrive;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;

import java.util.HashSet;
import java.util.Set;

public class SendMsgForActivity extends AppCompatActivity {

    Toolbar toolbarSendMsgFor;
    AppCompatCheckBox cbIncomingText;
    AppCompatCheckBox cbIncomingCalls;
    AppCompatCheckBox cbBoth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg_for);

        toolbarSendMsgFor = (Toolbar) findViewById(R.id.toolbar_send_msg_for);
        toolbarSendMsgFor.setTitle("");
        setSupportActionBar(toolbarSendMsgFor);




        cbIncomingText = (AppCompatCheckBox) findViewById(R.id.ch_incoming_texts);
        cbIncomingCalls = (AppCompatCheckBox) findViewById(R.id.ch_incoming_calls);
        cbBoth = (AppCompatCheckBox) findViewById(R.id.ch_both);


        cbBoth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbBoth.isChecked()){
                    cbBoth.setChecked(true);
                    cbIncomingCalls.setChecked(false);
                    cbIncomingText.setChecked(false);
                }
                else{
                    cbBoth.setChecked(false);
                }
            }
        });

        cbIncomingText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbIncomingText.isChecked()){
                    cbIncomingText.setChecked(true);
                    cbBoth.setChecked(false);
                }
                else{
                    cbIncomingText.setChecked(false);
                }
            }
        });

        cbIncomingCalls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbIncomingCalls.isChecked()){
                    cbIncomingCalls.setChecked(true);
                    cbBoth.setChecked(false);
                }
                else{
                    cbIncomingCalls.setChecked(false);
                }
            }
        });
    }
}
