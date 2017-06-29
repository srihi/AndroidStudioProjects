package com.textanddrive.broadcastdemo3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Niraj on 14-02-2017.
 */
public class NetworkCheckReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("NetworkCheckReceiver", "ConnectionChangeReceiver.onReceive()");
        String statusString = getConnectivityStatusString(context);
        Toast.makeText(context, statusString, Toast.LENGTH_SHORT).show();
    }

    public int TYPE_WIFI = 1;
    public int TYPE_MOBILE = 2;
    public int TYPE_NOT_CONNECTED = 0;

    public int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }

        return TYPE_NOT_CONNECTED;
    }

    public String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
}

