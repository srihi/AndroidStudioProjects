package com.textanddrive.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void onClickSendAlice(View view) {
        AppCompatEditText first_user_msg = (AppCompatEditText) findViewById(R.id.et_first);
        Intent intent= new Intent();
        intent.putExtra("Alice",first_user_msg.getText().toString());
        intent.setAction("android");
        sendBroadcast(intent);
        first_user_msg.setText("");
    }

    public void onClickSendBob(View view) {
        AppCompatEditText second_user_msg = (AppCompatEditText) findViewById(R.id.et_second);
        Intent intent2=new Intent();
        intent2.putExtra("Bob", second_user_msg.getText().toString());
        intent2.setAction("ios");
        sendBroadcast(intent2);
        second_user_msg.setText("");
    }

    public boolean isInternetAvailable(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }


    public class MyWififReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("TEST","OnReceive " + intent);
            Toast.makeText(context, "Intent : " + intent, Toast.LENGTH_SHORT).show();
            if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){



            }

        }
    }
}
