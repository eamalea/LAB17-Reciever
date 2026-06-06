package com.example.recieverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AirplaneModeReciever extends BroadcastReceiver {

    private static final String TAG = "AirplaneReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            // Récupération de l'état
            boolean isEnabled = intent.getBooleanExtra("state", false);
            String message = isEnabled ?
                    "✈️ Mode Avion ACTIVÉ" :
                    "📡 Mode Avion DÉSACTIVÉ";

            Log.d(TAG, message);
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

            // On peut également envoyer un broadcast local ou stocker l'état
            // pour que l'Activity l'affiche (mais l'Activity n'est pas forcément active)
        }
    }
}