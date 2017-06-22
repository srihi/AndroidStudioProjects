package com.textanddrive.projecttextanddrive;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v7.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Aemy on 08-May-17.
 */

public class IncomingCall extends BroadcastReceiver {

    Context Icontext;
    Intent IIntent;
    String name;
    private Context context;
    SharedPreferences Messageloglistpref;
    SharedPreferences.Editor editorMessageloglistpref;

    Set<String> incommingnumberMessagelogsetlist = new HashSet<String>();
    Set<String> outgoingMessagelogsetlist = new HashSet<String>();

    @Override
    public void onReceive(Context context, Intent intent) {

        Messageloglistpref = context.getSharedPreferences("MessagelogList",context.MODE_PRIVATE);
        editorMessageloglistpref = Messageloglistpref.edit();


        this.context = context;

        Toast.makeText(context, "Call Receive", Toast.LENGTH_SHORT).show();

        try{
            // TELEPHONY MANAGER class object to register one listner
            TelephonyManager tmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            IIntent = intent;
            Icontext = context;


            //Create Listener
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();

            // Register listener for LISTEN_CALL_STATE
            tmgr.listen(PhoneListener,PhoneListener.LISTEN_CALL_STATE);
        }catch (Exception e){
            Log.d("Phone Receive Error"," " + e);
        }
    }

    public class MyPhoneStateListener extends PhoneStateListener{
        public void onCallStateChanged(int State,String incomingNumber){
            Log.d("MyPhoneListener",State + " incoming Number: " + incomingNumber);
            if(State == 1){
                String ContactName = getContactDisplayNameByNumber(incomingNumber, Icontext);
                String msg = "New Phone Call Event. Incomming Name :" + ContactName + "  Incomming Number : " + incomingNumber;

                //Show the Toast for incoming Reject Call
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(Icontext, msg, duration);
                toast.show();

                Messageloglistpref = context.getSharedPreferences("MessagelogList",context.MODE_PRIVATE);
                editorMessageloglistpref = Messageloglistpref.edit();

                boolean isappon;

                SharedPreferences IsONAPP = context.getSharedPreferences("isONOff", context.MODE_PRIVATE);
                isappon = IsONAPP.getBoolean("IsONOff",false);


              //  Toast.makeText(context, "Trim :" + seperator[1] + "\n" + "Trim2 : " + seperator[3], Toast.LENGTH_SHORT).show();

                SharedPreferences getSelectedmsg = context.getSharedPreferences("sentMsg",context.MODE_PRIVATE);

                String Message;
                Message = getSelectedmsg.getString("SelectMessagesent","");

                if(isappon==true){
                    Log.d("Condition","True");
                    sendSMS(incomingNumber,Message);

                    SharedPreferences pref = context.getSharedPreferences("contact",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("number",incomingNumber);
                    editor.putString("msg",Message);
                    editor.apply();

                    incommingnumberMessagelogsetlist.add(""+incomingNumber);
                    outgoingMessagelogsetlist.add(""+Message);

                    editorMessageloglistpref.putStringSet("IncomiingNumber",incommingnumberMessagelogsetlist);
                    editorMessageloglistpref.putStringSet("OutgoingMessage",outgoingMessagelogsetlist);
                    editorMessageloglistpref.apply();

                    Log.d("Message Method","Complete");
                    disconnectCall();
                  //  Toast.makeText(context, "Trim : ON", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("Condition","False");
                  //  Toast.makeText(context, "Trim : OFF", Toast.LENGTH_SHORT).show();
                }




                int icon = R.drawable.phonecalls;

                int mNotificationId = 001;

                String title = "Text and Drive";

                Intent i = new Intent(Icontext, HomeActivity.class);

                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(Icontext, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);



                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        Icontext);
                Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                        .setAutoCancel(true)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("" + ContactName + "\n" + incomingNumber))
                        .setContentIntent(resultPendingIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setLargeIcon(BitmapFactory.decodeResource(Icontext.getResources(), R.drawable.phonecalls))
                        .setContentText("This is big message, This is big message, This is big message").build();

                NotificationManager notificationManager = (NotificationManager) Icontext.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(mNotificationId, notification);

            }
        }
    }


    public String getContactDisplayNameByNumber(String number, Context context) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        name = "Incoming call from";

        ContentResolver contentResolver = context.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, null, null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                // this.id =
                // contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.CONTACT_ID));
                // String contactId =
                // contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
            } else {
                name = "Unknown number";
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }

        return name;
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



    public void disconnectCall() {
        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("error 1",
                    "FATAL ERROR: could not connect to telephony subsystem");
            Log.d("error 2", "Exception object: " + e);
        }
    }

}
