package com.textanddrive.java;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int a[] = {5, 6, 8, 4, 2, 4, 1};
        int sum = a[0];
        Log.d(TAG, "Length: " + a.length);
        if (a[0] % 2 == 0) {
            Log.d(TAG, "Even");
            for (int i = 1; i <= a.length - 1; i++) {
                do {
                    sum += a[i];
                } while (a[i] % 2 == 0);
                Log.d(TAG, "Sum : " + sum);
            }
        }
        if (a[0] % 2 != 0) {
            Log.d(TAG, "Odd");
            for (int i = 1; i <= a.length - 1; i++) {
                do {
                    sum += a[i];
                } while (a[i] % 2 != 0);
                Log.d(TAG, "Sum : " + sum);
            }
        }
    }
}
