package ru.arlen.androidlayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

class MyReceiver extends BroadcastReceiver {
    private static final String MY_RECEIVER = "MY_RECEIVER";
    private int counter;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w(MY_RECEIVER, "onReceive");
        Toast.makeText(context, "Receive broadcast: " + counter++, Toast.LENGTH_SHORT).show();
    }
}