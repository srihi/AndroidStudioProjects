package com.textanddrive.countdowntimerdemo;

import android.app.TimePickerDialog;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static CountDownTimer countDownTimer;
    private LinearLayoutCompat linearLayoutCompat;
    private TimePickerDialog mTimePicker;
    private TextView mTvTimerHours;
    private TextView mTvTimerMinutes;
    private TextView mTvTimerSeconds;
    private ToggleButton toggleButton;
    private String time;
    private String selectedHours;
    private String selectedMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initId();
        setListeners();
    }

    private void initId() {
        linearLayoutCompat = (LinearLayoutCompat) findViewById(R.id.linear_layout_time);
        mTvTimerHours = (TextView) findViewById(R.id.tv_timer_hours);
        mTvTimerMinutes = (TextView) findViewById(R.id.tv_timer_minute);
        mTvTimerSeconds = (TextView) findViewById(R.id.tv_timer_second);
        toggleButton = (ToggleButton) findViewById(R.id.toggle_button);
    }

    private void setListeners() {
        linearLayoutCompat.setOnClickListener(this);
        toggleButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_layout_time:
                mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time = String.valueOf((selectedHour * 60) + selectedMinute);
                        Log.e("TEST", "onTimeSet: " + selectedHour + ":" + selectedMinute);
                        selectedHours = String.valueOf(selectedHour);
                        selectedMinutes = String.valueOf(selectedMinute);
                        mTvTimerHours.setText(selectedHours);
                        mTvTimerMinutes.setText(selectedMinutes);
                        mTvTimerSeconds.setText("00");
                    }
                }, 0, 0, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;

            case R.id.toggle_button:
                if (toggleButton.isChecked()) {
                    if (countDownTimer == null) {
                        int noOfMinutes = Integer.parseInt(time) * 60 * 1000;
                        startTimer(noOfMinutes);
                    } else
                        Toast.makeText(MainActivity.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();
                } else {
                    stopCountdown();
                    mTvTimerHours.setText("00");
                    mTvTimerMinutes.setText("00");
                    mTvTimerSeconds.setText("00");
                }
                break;
        }
    }

    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                mTvTimerHours.setText("" + String.format("%02d", TimeUnit.MILLISECONDS.toHours(millis)));
                mTvTimerMinutes.setText("" + String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))));
                mTvTimerSeconds.setText("" + String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
            }

            public void onFinish() {
                countDownTimer = null;
            }
        }.start();
    }

    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}