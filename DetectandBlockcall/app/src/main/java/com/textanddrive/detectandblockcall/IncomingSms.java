package com.textanddrive.detectandblockcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Niraj on 13-05-2017.
 */

public class IncomingSms extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            String format = bundle.getString("format");

            final SmsMessage[] messages = new SmsMessage[pdus.length];
            for(int i = 0; i < pdus.length; i++) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                }else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                String senderPhoneNo = messages[i].getDisplayOriginatingAddress();
                Toast.makeText(context, "Message " + messages[0].getMessageBody() + ", from " + senderPhoneNo, Toast.LENGTH_SHORT).show();
            }
        }

/*      final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);


                    // Show alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: " + senderNum + ", message: " + message, duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }*/
    }
}
