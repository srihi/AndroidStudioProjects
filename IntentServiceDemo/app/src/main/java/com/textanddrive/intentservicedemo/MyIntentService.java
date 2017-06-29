package com.textanddrive.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.icu.text.DateFormat;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String msg = intent.getStringExtra("msg");
        Log.e("TEST", "onHandleIntent: "+msg );
        SystemClock.sleep(20000);
     //   String resultTxt = msg + " "+ DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());
    }
}
