package com.example.recieverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class CustomEventReceiver extends BroadcastReceiver {

    private static final String TAG = "CustomReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.example.receiverdemo.CUSTOM_EVENT".equals(intent.getAction())) {
            String message = intent.getStringExtra("message");
            Log.d(TAG, "Broadcast custom reçu : " + message);
            Toast.makeText(context, "📨 Custom reçu : " + message, Toast.LENGTH_LONG).show();
        }
    }
}