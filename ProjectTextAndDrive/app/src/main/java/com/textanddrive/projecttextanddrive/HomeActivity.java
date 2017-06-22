package com.textanddrive.projecttextanddrive;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.TimeUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.telephony.SmsManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.DataTruncation;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import android.os.Handler;

import java.util.logging.LogRecord;
import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String MSG = "msg";
    private static final String FORMAT = "%02d";
    private Boolean chktoggle = false;
    private Toolbar toolbar;
    private ListView listView = null;
    private AppCompatTextView tvListMsg;
    private AppCompatTextView tvHour;
    private AppCompatTextView tvMinute;
    private AppCompatTextView tvSecond;
    private Handler handler = new Handler();
    private long milliSeconds = 0;
    private Runnable runnable;
    private int hour;
    private int minute;
    boolean blink;
    private boolean isStartTimer = false;
    private boolean isFromDefaultMsg;

    CountDownTimer countDownTimer;
    private long calculatedHours = 0;
    private long duration = 0;
    private long calculatedMinutes = 0;
    ToggleButton btnToggle;



    //#################################################


    SharedPreferences IsONAPP;
    SharedPreferences.Editor isOnOffEditor;


    SharedPreferences Selectedmsg;
    SharedPreferences.Editor editorSelectedmsg;
    //################################################


    /*private Context context;
    public static final String action = "android.provider.Telephony.SMS_RECEIVED";

    public HomeActivity(Context context){
        this.context = context;
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        toolbar = (Toolbar) findViewById(R.id.toolbar_text_and_drive);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_settings_white_36dp);
        toolbar.setOverflowIcon(drawable);

        tvListMsg = (AppCompatTextView) findViewById(R.id.tv_list_message);
        tvListMsg.setOnClickListener(this);
        tvHour = (AppCompatTextView) findViewById(R.id.tv_hour1);
        tvMinute = (AppCompatTextView) findViewById(R.id.tv_minute1);
        tvSecond = (AppCompatTextView) findViewById(R.id.tv_second1);

       IsONAPP = getSharedPreferences("isONOff", MODE_PRIVATE);
        isOnOffEditor = IsONAPP.edit();

        Selectedmsg = getSharedPreferences("sentMsg",MODE_PRIVATE);
        editorSelectedmsg = Selectedmsg.edit();
        /*Intent intent = getIntent();
        String mesg = intent.getStringExtra("message");
        Toast.makeText(this, "Msg :" + mesg, Toast.LENGTH_SHORT).show();*/

        SharedPreferences pref = getSharedPreferences("selectedMsg",MODE_PRIVATE);
        String mesg = pref.getString("msg",null);
        Toast.makeText(this, "sss: " + mesg, Toast.LENGTH_SHORT).show();
        if(mesg != null){
            Toast.makeText(getApplicationContext(), ""+mesg, Toast.LENGTH_SHORT).show();
            tvListMsg.setText(mesg);
            editorSelectedmsg.putString("SelectMessagesent",mesg);
            editorSelectedmsg.apply();
        }else{

        }
        killBroadCastReceiver();
        btnToggle = (ToggleButton) findViewById(R.id.toggle_button);
        if(btnToggle.isChecked() == true){
            startBroadCastReceiver();
        }
        else{
            killBroadCastReceiver();
        }
        // Default set Time

        SharedPreferences preferences = getSharedPreferences("my_db", MODE_PRIVATE);
        String msg = preferences.getString(MSG, null);
        int hh = preferences.getInt("HH", 0);
        int mm = preferences.getInt("MM", 0);

        if (hh != 0) {
            tvHour.setText(hh + "");
        } else {
            tvHour.setText("OO");
        }
        if (mm != 0) {
            tvMinute.setText(mm + "");
        } else {
            tvMinute.setText("OO");
        }

        if (msg != null) {
            Toast.makeText(getApplicationContext(), ""+msg, Toast.LENGTH_SHORT).show();
            tvListMsg.setText(msg);
            editorSelectedmsg.putString("SelectMessagesent",msg);
            editorSelectedmsg.apply();
        }
        startTimer(hh,mm,0);

        // Finish Default set Time


    }

    // Select Message OnClick Method
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_list_message:
                listView = new ListView(HomeActivity.this);
                final String[] messages = {"select message", "Can't talk now.I am driving and will be busy for "+ tvHour.getText().toString() +" hr " + tvMinute.getText().toString() + " min", "Hey,almost done with the task at hand.I'll get back to you in around " + tvHour.getText().toString() + " hr " + tvMinute.getText().toString() + "min"
                        , "It's though for me to talk now,I'm on my commute. I'll get back to you in " + tvHour.getText().toString() + " hr " + tvMinute.getText().toString() + "min"};

                final AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.popup_window, null);
                final ListView listView = (ListView) view.findViewById(R.id.listview1);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, messages);
                listView.setAdapter(adapter);
                alertDialog.setView(view);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(HomeActivity.this, messages[i], Toast.LENGTH_SHORT).show();
                        String msg = messages[i].toString();
                        tvListMsg.setText(msg);
                        editorSelectedmsg.putString("SelectMessagesent",msg);
                        editorSelectedmsg.apply();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;

            default:
                break;
        }
    }

    //Finish Select Message OnClick Method


    // Set Time Manually in front page

    public void onClickTimer(View view) {

        if (isStartTimer == false) {

            Calendar mcurrentTime = Calendar.getInstance();
            final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            final int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(HomeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    Toast.makeText(getApplicationContext(), selectedHour + "", Toast.LENGTH_SHORT).show();

                    tvHour.setText(selectedHour + "");
                    tvMinute.setText(selectedMinute + "");
                /*timerStart();*/

                    Toast.makeText(getApplicationContext(), "false", Toast.LENGTH_SHORT).show();
                    isStartTimer = true;
                    startTimer(selectedHour, selectedMinute, 0);
                }


            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        } else {
            final AlertDialog builder1 = new AlertDialog.Builder(HomeActivity.this).create();
            View view1 = LayoutInflater.from(HomeActivity.this).inflate(R.layout.right_wrong, null);
            builder1.setView(view1);
            AppCompatTextView tvmessage = (AppCompatTextView) view1.findViewById(R.id.txt_dia);
            tvmessage.setText("Do you want to Reset Timer?");

            AppCompatImageButton imgYes = (AppCompatImageButton) view1.findViewById(R.id.btn_yes);
            imgYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ToggleButton btnToggle = (ToggleButton) findViewById(R.id.toggle_button);
                    isStartTimer = false;
                    tvHour.setText("OO");
                    tvMinute.setText("OO");
                    tvSecond.setText("OO");
                    tvListMsg.setText("select message");
                    btnToggle.setChecked(false);
                    chktoggle = false;
                    startTimer(00, 00, 0);
                    builder1.dismiss();
                    killBroadCastReceiver();
                }
            });

            AppCompatImageButton imgNo = (AppCompatImageButton) view1.findViewById(R.id.btn_no);
            imgNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder1.dismiss();
                }
            });
            builder1.show();
        }
    }

    //Finish Set Time

    //Start Timer for set Our Selected Time And CountDown

    int seconds = 0;
    int minutes = 0;
    int hours = 0;

    private void startTimer(final int selectedHour, final int selectedMinute, final int selectedSeconds) {

        if(isStartTimer == false) {
            seconds = 0;
            minute = 0;
            hour = 0;
            //second element

            calculatedHours = 0;
            duration = 0;
            calculatedMinutes = 0;
        }
        calculatedHours = selectedHour * 60 * 60;

        calculatedMinutes = 60 * selectedMinute;

        duration = calculatedHours * 1000 + calculatedMinutes * 1000 + selectedSeconds * 1000; //add up our values

        countDownTimer = new CountDownTimer(duration, 1000) {

            @Override
            public void onTick(long tick) {
                if (isStartTimer != false) {
                    Log.e("tick", "" + tick);
                    seconds = (int) ((tick / 1000) % 60);
                    minutes = (int) ((tick / (1000 * 60)) % 60 + 1);
                    hours = (int) ((tick / (1000 * 60 * 60)) % 24);
                    tvSecond.setText("" + seconds);
                    tvMinute.setText("" + (minutes - 1));
                    tvHour.setText("" + hours);
                } else {
                    tvSecond.setText("OO");
                    tvMinute.setText("OO");
                    tvHour.setText("OO");
                    seconds = 0;
                    minute = 0;
                    hour = 0;
                    cancel();
                }

            }

            @Override
            public void onFinish() {
                Log.e("Time is finished", "yessss");
                tvSecond.setText("OO");
                tvMinute.setText("OO");
                tvHour.setText("OO");
                cancel();
                killBroadCastReceiver();
            }

        };

    }

    // Finish Start Timer for set Our Selected Time And CountDown

    //Alert Dialog for Yes Or No Handle Event

    public class RightWrong {
        public void showDialog(Activity activity, String msg) {

            final ToggleButton btnToggle = (ToggleButton) findViewById(R.id.toggle_button);
            AppCompatTextView tvMsg = (AppCompatTextView) findViewById(R.id.tv_list_message);
            String selectedMsg = tvMsg.getText().toString();

            if (selectedMsg.equals("select message")) {
                btnToggle.setChecked(false);
                final AlertDialog builder = new AlertDialog.Builder(HomeActivity.this).create();
                View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.right_wrong, null);
                builder.setView(view);
                AppCompatTextView tvmessage = (AppCompatTextView) view.findViewById(R.id.txt_dia);
                tvmessage.setText("Choose Message");

                AppCompatImageButton imgYes = (AppCompatImageButton) view.findViewById(R.id.btn_yes);
                imgYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvListMsg.callOnClick();
                        btnToggle.setChecked(false);
                        builder.dismiss();
                    }
                });

                AppCompatImageButton imgNo = (AppCompatImageButton) view.findViewById(R.id.btn_no);
                imgNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.dismiss();
                    }
                });
                builder.show();

            }
            else if(tvHour.getText()== "OO" && tvMinute.getText() == "OO"){
                btnToggle.setChecked(false);
                final AlertDialog builder = new AlertDialog.Builder(HomeActivity.this).create();
                View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.right_wrong, null);
                builder.setView(view);

                AppCompatTextView tvmessage = (AppCompatTextView) view.findViewById(R.id.txt_dia);
                tvmessage.setText("Select Time First");

                AppCompatImageButton imgYes = (AppCompatImageButton) view.findViewById(R.id.btn_yes);
                imgYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvHour.performClick();
                        btnToggle.setChecked(false);
                        builder.dismiss();
                    }
                });

                AppCompatImageButton imgNo = (AppCompatImageButton) view.findViewById(R.id.btn_no);
                imgNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.dismiss();
                    }
                });
                builder.show();
            }
            else if (chktoggle == false) {
                btnToggle.setChecked(false);
                final AlertDialog builder = new AlertDialog.Builder(HomeActivity.this).create();
                View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.right_wrong, null);
                builder.setView(view);
                AppCompatTextView tvmessage = (AppCompatTextView) view.findViewById(R.id.txt_dia);
                tvmessage.setText("Do you want to turn app On?");

                AppCompatImageButton imgYes = (AppCompatImageButton) view.findViewById(R.id.btn_yes);
                imgYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = null;
                        btnToggle.setChecked(true);
                        isStartTimer = true;
                        countDownTimer.start();
                        builder.dismiss();
                        isOnOffEditor.putBoolean("IsONOff",true);
                        isOnOffEditor.apply();
                        chktoggle = true;
                        startBroadCastReceiver();
                    }
                });

                AppCompatImageButton imgNo = (AppCompatImageButton) view.findViewById(R.id.btn_no);
                imgNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.dismiss();
                    }
                });
                builder.show();
            } else if (chktoggle == true) {

                btnToggle.setChecked(true);
                final AlertDialog builder = new AlertDialog.Builder(HomeActivity.this).create();
                View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.right_wrong, null);
                builder.setView(view);
                AppCompatTextView tvmessage = (AppCompatTextView) view.findViewById(R.id.txt_dia);
                tvmessage.setText("Do you want to turn app Off?");

                AppCompatImageButton imgYes = (AppCompatImageButton) view.findViewById(R.id.btn_yes);
                imgYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnToggle.setChecked(false);
                        tvListMsg.setText("select message");
                        SharedPreferences preferences = getSharedPreferences("my_db", MODE_PRIVATE);
                        String msg = preferences.getString(MSG, null);
                        int hh = preferences.getInt("HH", 0);
                        int mm = preferences.getInt("MM", 0);
                        if (hh != 0) {
                            tvHour.setText(hh + "");
                        } else {
                            tvHour.setText("OO");
                        }
                        if (mm != 0) {
                            tvMinute.setText(mm + "");
                        } else {
                            tvMinute.setText("OO");
                        }
                        tvSecond.setText("OO");
                        if (msg != null) {
                            tvListMsg.setText(msg);
                        }
                        countDownTimer.cancel();
                        countDownTimer = null;
                        startTimer(hh,mm,0);
                        builder.dismiss();
                        chktoggle = false;
                        isOnOffEditor.putBoolean("IsONOff",false);
                        isOnOffEditor.apply();
                        killBroadCastReceiver();
                    }
                });

                AppCompatImageButton imgNo = (AppCompatImageButton) view.findViewById(R.id.btn_no);
                imgNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.dismiss();
                    }
                });
                builder.show();
            }
        }
    }
    //Finish Alert Dialog for Yes Or No Handle Event

    //Create Menu List
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }
    //Finish Menu List

    //OnMenu Item Selected Method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Aboutus:
                Intent intentAboutUs = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(intentAboutUs);
                break;

            case R.id.custom_message:
                Intent intentCustomMsg = new Intent(HomeActivity.this, CustomMsgActivity.class);
                startActivity(intentCustomMsg);
                break;

            case R.id.send_message_for:
                Intent intentSendMsgFor = new Intent(HomeActivity.this, SendMsgForActivity.class);
                startActivity(intentSendMsgFor);
                break;

            case R.id.support:
                supportMsg();
                break;

            case R.id.default_setting:
                SharedPreferences preferences = getSharedPreferences("toggle",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                Intent intentDefault = new Intent(HomeActivity.this, DefaultMsgActivity.class);
                if(btnToggle.isChecked() == true){
                    editor.putBoolean("toggle",true);
                    editor.apply();
                }
                else{
                    editor.putBoolean("toggle",false);
                    editor.apply();
                }
                startActivity(intentDefault);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //Finish OnMenu Item Selected Method


    //Show Message Log
    public void onClickMessagelog(View view) {
        Intent intent = new Intent(HomeActivity.this, MessageLogActivity.class);
        startActivity(intent);
    }
    //Finish Message Log

    //Toggle Button For Turn On And Turn Off
    public void onClickTogleButton(View view) {
        RightWrong rightWrong = new RightWrong();
        rightWrong.showDialog(HomeActivity.this, "");
    }
    //Finish Toggle Button For Turn On And Turn Off


    //OnBackPressed Close The App
    @Override
    public void onBackPressed() {
        final AlertDialog builder = new AlertDialog.Builder(HomeActivity.this).create();
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.right_wrong, null);
        builder.setView(view);
        AppCompatTextView tvmessage = (AppCompatTextView) view.findViewById(R.id.txt_dia);
        tvmessage.setText("Are You Sure Want to Exit?");
        AppCompatImageButton imgYes = (AppCompatImageButton) view.findViewById(R.id.btn_yes);
        imgYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //killBroadCastReceiver();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                builder.dismiss();
               // finish();
            }
        });
        AppCompatImageButton imgNo = (AppCompatImageButton) view.findViewById(R.id.btn_no);
        imgNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });
        builder.show();
    }
    //Finish OnBackPressed Close The App

    //PopUp Screen For Feedback
    public void supportMsg() {
        final AlertDialog builder = new AlertDialog.Builder(HomeActivity.this).create();
        final View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.support, null);
        builder.setView(view);
        builder.getWindow().setGravity(Gravity.TOP | Gravity.RIGHT);
        AppCompatImageView imgBtn;
        imgBtn = (AppCompatImageView) view.findViewById(R.id.imageView_close);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });
        AppCompatImageButton btnMail;
        btnMail = (AppCompatImageButton) view.findViewById(R.id.btn_mail);
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatEditText etSubject;
                AppCompatEditText etTypeText;
                AppCompatEditText  etMail;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                etSubject = (AppCompatEditText) builder.findViewById(R.id.et_subject);
                etTypeText = (AppCompatEditText) builder.findViewById(R.id.et_type_here);
                etMail = (AppCompatEditText) builder.findViewById(R.id.et_email_address);

                String sub = etSubject.getText().toString();
                String typeText = etTypeText.getText().toString();
                String email = etMail.getText().toString();

                Toast.makeText(HomeActivity.this, "Hello :" + sub, Toast.LENGTH_SHORT).show();
                if(sub.length() == 0){
                    etSubject.setError("Please input subject");
                }else if(!(email.matches(emailPattern))){
                    etMail.setError("Please Corrct the Email");
                }
                else if(typeText.length() == 0){
                    etTypeText.setError("Please Input Some Text");
                }
                else{
                    Intent i = new Intent(android.content.Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
                    i.putExtra(Intent.EXTRA_SUBJECT, sub);
                    i.putExtra(Intent.EXTRA_TEXT   , typeText);
                    startActivity(Intent.createChooser(i, "agmistri@gmail.com"));
                }
                Toast.makeText(HomeActivity.this, "Hiii", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
    //Finish PopUp Screen For Feedback


    //START BroadcastReceiver
    private void startBroadCastReceiver() {
        PackageManager pm = this.getPackageManager();
        ComponentName componentName = new ComponentName(this, IncomingCall.class);
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        //Toast.makeText(getApplicationContext(), "BroadCast Receiver Started", Toast.LENGTH_LONG).show();
    }

    //Kill BroadcastReceiver
    private void killBroadCastReceiver() {
        PackageManager pm = this.getPackageManager();
        ComponentName componentName = new ComponentName(this, IncomingCall.class);
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        // Toast.makeText(getApplicationContext(), "BroadCast Receiver Killed", Toast.LENGTH_LONG).show();
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