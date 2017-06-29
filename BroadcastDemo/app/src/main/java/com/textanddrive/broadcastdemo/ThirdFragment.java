package com.textanddrive.broadcastdemo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThirdFragment extends Fragment {
    AppCompatTextView text_first_user;
    AppCompatTextView text_second_user;

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("TEST", "ThirdFragment Intent : " + intent);

            text_first_user = (AppCompatTextView) getView().findViewById(R.id.tv_first);
            text_second_user = (AppCompatTextView) getView().findViewById(R.id.tv_second);

            String msg_first_user = intent.getStringExtra("Alice");
            String msg_second_user = intent.getStringExtra("Bob");

            text_first_user.setText(msg_first_user);
            text_second_user.setText(msg_second_user);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android");
        intentFilter.addAction("ios");
        getActivity().registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(myReceiver);
    }
}
