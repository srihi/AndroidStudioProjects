package com.textanddrive.projecttextanddrive;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aemy on 13-May-17.
 */

public class IncomingMsg extends BroadcastReceiver {
    /*private Context context;
    private static final String ACTION = "android.provider.Telephony.SEND_SMS";
    public static int MSG_TPE=0;*/

    String mblNo;
    String msg;
    public static final String MSG = "msg";

    private Context context;
    SharedPreferences Messageloglistpref;
    SharedPreferences.Editor editorMessageloglistpref;

    Set<String> incommingnumberMessagelogsetlist = new HashSet<String>();
    Set<String> outgoingMessagelogsetlist = new HashSet<String>();

    public static final String action = "android.provider.Telephony.SMS_RECEIVED";
    /*final SmsManager sms = SmsManager.getDefault();*/
    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;
        String sms_str ="";
        this.context = context;

        Messageloglistpref = context.getSharedPreferences("MessagelogList",context.MODE_PRIVATE);
        editorMessageloglistpref = Messageloglistpref.edit();



        if (bundle != null)
        {
            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsm = new SmsMessage[pdus.length];
            for (int i=0; i<smsm.length; i++){
                smsm[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                sms_str += "Sent From: " + smsm[i].getOriginatingAddress();
                sms_str += "\r\n:Message: ";
                sms_str += smsm[i].getMessageBody().toString();
                sms_str+= "\r\n";

                String[] seperator = sms_str.split(":");
                seperator[1] = seperator[1].trim();
                seperator[3] = seperator[3].trim();
                String Message;
                mblNo = seperator[1];


                boolean isappon;

                SharedPreferences IsONAPP = context.getSharedPreferences("isONOff", context.MODE_PRIVATE);
                isappon = IsONAPP.getBoolean("IsONOff",false);


                Toast.makeText(context, "Trim :" + seperator[1] + "\n" + "Trim2 : " + seperator[3], Toast.LENGTH_SHORT).show();
                SharedPreferences getSelectedmsg = context.getSharedPreferences("sentMsg",context.MODE_PRIVATE);


                Message = getSelectedmsg.getString("SelectMessagesent","");

                if(isappon==true){
                    Log.d("Condition","True");
                    sendSMS(mblNo,Message);
                    incommingnumberMessagelogsetlist.add(""+mblNo);
                    outgoingMessagelogsetlist.add(""+Message);

                    editorMessageloglistpref.putStringSet("IncomiingNumber",incommingnumberMessagelogsetlist);
                    editorMessageloglistpref.putStringSet("OutgoingMessage",outgoingMessagelogsetlist);
                    editorMessageloglistpref.apply();
                    Log.d("Message Method","Complete");
                    Toast.makeText(context, "Trim : ON", Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = context.getSharedPreferences("contact",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("number",seperator[1]);
                    editor.putString("msg",Message);
                    editor.apply();
                }else {
                    Log.d("Condition","False");
                    Toast.makeText(context, "Trim : OFF", Toast.LENGTH_SHORT).show();
                }


                /*Intent numintent = new Intent(context,MessageLogActivity.class);
                numintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                numintent.putExtra("number",seperator[1]);
                context.startActivity(numintent);*/
            }

         //   Toast.makeText(context, "SMS :" + sms_str, Toast.LENGTH_SHORT).show();

            // Start Application's  MainActivty activity
            /*Intent smsIntent=new Intent(context,MessageLogActivity.class);
            smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            smsIntent.putExtra("Number", sms_str);
            context.startActivity(smsIntent);*/

        /*if (intent.getAction().equals(action)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                for (SmsMessage message : messages) {

                    String strMessageFrom = message.getDisplayOriginatingAddress();
                    String strMessageBody = message.getDisplayMessageBody();
                    AppCompatTextView txtMsg = (AppCompatTextView) ((HomeActivity)context).findViewById(R.id.tv_list_message);
                    String msgTxt = txtMsg.getText().toString();

                    *//*SharedPreferences preferences = context.getSharedPreferences("contact", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("mblno", strMessageFrom);
                    editor.putString("msg", strMessageBody);
                    editor.putString("txtmsg",msgTxt);
                    editor.apply();*//*



                    *//*((MessageLogActivity)context).sendSMS(strMessageFrom,msgTxt);*//*
                    Intent smsIntent = new Intent(context, MessageLogActivity.class);
                    smsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    smsIntent.putExtra("number", strMessageFrom);
                    smsIntent.putExtra("message",strMessageBody);
                    context.startActivity(smsIntent);

                    Toast.makeText(context, "From : " + strMessageFrom + "\nBody : " + strMessageBody, Toast.LENGTH_LONG).show();
                }
            }*/

        /*final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                    // Show alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }*/

        /*String MSGTYPE = intent.getAction();
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;
        String sms_str ="";
        if (bundle != null)
        {
                // Get the SMS message
                Object[] pdus = (Object[]) bundle.get("pdus");
                smsm = new SmsMessage[pdus.length];
                for (int i = 0; i < smsm.length; i++) {
                    smsm[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    sms_str += "Sent From: " + smsm[i].getOriginatingAddress().toString();
                    sms_str += "\r\nMessage: ";
                    sms_str += smsm[i].getMessageBody().toString();
                    sms_str += "\r\n";

                    Toast.makeText(context, "STRING :" +sms_str, Toast.LENGTH_SHORT).show();
                     *//*try {
                        AppCompatTextView txtView = (AppCompatTextView) ((HomeActivity)context).findViewById(R.id.tv_list_message);
                        String ph = "8849937007";
                        String msg = "hii";
                        Log.d("Message","Message :" + msg);
                        Toast.makeText(context, "Message : " + msg, Toast.LENGTH_SHORT).show();

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(ph, null,msg, null, null);
                        Toast.makeText(context, "Message Sent",
                                Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(context, "Message not Sent",
                                Toast.LENGTH_LONG).show();
                    }*//*
                }*/

            // Start Application's  MainActivty activity
        }
    }

    public void sendSMS(String phoneNo, String msg) {
        Log.d("Method Call","Called");
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        context.getApplicationContext().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(context, "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(context, "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(context, "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        context.getApplicationContext().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(context, "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNo, null, msg, sentPI, deliveredPI);
    }

}


