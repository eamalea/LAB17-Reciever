package com.example.recieverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            String msg = "📱 Téléphone démarré – BootReceiver statique a fonctionné !";
            Log.d(TAG, msg);
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

            // Ici vous pourriez lancer un service, programmer une alarme, etc.
        }
    }
}