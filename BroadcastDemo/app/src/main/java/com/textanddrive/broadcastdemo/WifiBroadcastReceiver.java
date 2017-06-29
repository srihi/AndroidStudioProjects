package com.textanddrive.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Niraj on 09-02-2017.
 */

public class WifiBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("TEST","OnReceive " + intent);
        Toast.makeText(context, "Intent : " + intent, Toast.LENGTH_SHORT).show();
        if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){

            Intent intent1 = new Intent();
            intent1.setAction("android");
            intent1.putExtra("internet",true);
            context.sendBroadcast(intent1);


        }

    }
}
