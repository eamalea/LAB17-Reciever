package com.example.recieverdemo;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatus;
    private Button btnToggleAirplane, btnCheckAirplane, btnSendCustom;

    // Receveurs
    private AirplaneModeReciever airplaneReceiver;
    private CustomEventReceiver customReceiver;
    private boolean isAirplaneReceiverRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        btnToggleAirplane = findViewById(R.id.btnToggleAirplane);
        btnCheckAirplane = findViewById(R.id.btnCheckAirplane);
        btnSendCustom = findViewById(R.id.btnSendCustom);

        airplaneReceiver = new AirplaneModeReceiver();
        customReceiver = new CustomEventReceiver();

        btnToggleAirplane.setOnClickListener(v -> toggleAirplaneReceiver());
        btnCheckAirplane.setOnClickListener(v -> checkAirplaneModeStatus());
        btnSendCustom.setOnClickListener(v -> sendCustomBroadcast());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // On réaffiche l'état dans le TextView (optionnel)
        updateStatusText();
    }

    private void toggleAirplaneReceiver() {
        if (!isAirplaneReceiverRegistered) {
            // Enregistrement dynamique
            IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            registerReceiver(airplaneReceiver, filter);
            isAirplaneReceiverRegistered = true;
            btnToggleAirplane.setText("Désactiver récepteur mode avion");
            tvStatus.setText("✅ Récepteur mode avion activé");
        } else {
            unregisterReceiver(airplaneReceiver);
            isAirplaneReceiverRegistered = false;
            btnToggleAirplane.setText("Activer récepteur mode avion");
            tvStatus.setText("❌ Récepteur mode avion désactivé");
        }
    }

    private void checkAirplaneModeStatus() {
        // Lecture directe de l'état du mode avion via Settings.Global
        boolean isAirplaneModeOn = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isAirplaneModeOn = Settings.Global.getInt(
                    getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
        } else {
            isAirplaneModeOn = Settings.System.getInt(
                    getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) == 1;
        }
        String msg = isAirplaneModeOn ? "✈️ Mode Avion ACTIVÉ" : "📡 Mode Avion DÉSACTIVÉ";
        tvStatus.setText("État actuel : " + msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void sendCustomBroadcast() {
        // Création de l'intent avec action personnalisée
        Intent intent = new Intent("com.example.receiverdemo.CUSTOM_EVENT");
        intent.putExtra("message", "Hello depuis l'Activity ! Timestamp : " + System.currentTimeMillis());

        // Pour Android 14+ (API 34), les broadcasts implicites sont limités.
        // On rend le broadcast explicite en spécifiant le package de notre application
        // Cela garantit que seul notre CustomEventReceiver (dynamique) le recevra.
        intent.setPackage(getPackageName());

        // Envoi du broadcast
        sendBroadcast(intent);

        tvStatus.setText("📤 Broadcast custom envoyé (explicite intra-app)");
        Toast.makeText(this, "Broadcast custom envoyé", Toast.LENGTH_SHORT).show();
    }

    private void updateStatusText() {
        if (isAirplaneReceiverRegistered) {
            tvStatus.setText("✅ Récepteur mode avion actif");
        } else {
            tvStatus.setText("❌ Récepteur mode avion inactif");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Enregistrer le CustomEventReceiver dynamiquement (il peut être enregistré ici ou dans onCreate)
        // Pour l'exemple, on l'enregistre maintenant pour qu'il écoute les broadcasts custom
        IntentFilter customFilter = new IntentFilter("com.example.receiverdemo.CUSTOM_EVENT");
        registerReceiver(customReceiver, customFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Désenregistrer le custom receiver pour économiser les ressources (optionnel)
        // Attention : si vous le désenregistrez ici, vous ne recevrez pas de broadcast lorsque l'app est en arrière-plan.
        // Pour ce lab, on garde le custom receiver actif tout le temps de l'activité.
        // On le désenregistre dans onDestroy pour éviter les fuites.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Nettoyage : désenregistrer les deux receivers
        if (isAirplaneReceiverRegistered) {
            unregisterReceiver(airplaneReceiver);
        }
        try {
            unregisterReceiver(customReceiver);
        } catch (IllegalArgumentException e) {
            // Receiver déjà désenregistré
        }
    }
}