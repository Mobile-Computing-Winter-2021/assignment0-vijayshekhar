package com.example.helloworld.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ApplicationErrorReport;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;


import com.example.helloworld.BroadcastReceivers.BatteryReceiver;
import com.example.helloworld.Fragments.MusicPlayerFragment;
import com.example.helloworld.R;
import com.example.helloworld.Services.MusicService;

public class MainActivity extends AppCompatActivity {

    BatteryReceiver receiver = new BatteryReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment();

        IntentFilter intent = new IntentFilter();
        intent.addAction(BatteryManager.EXTRA_BATTERY_LOW);
//        intent.addAction(B);


        registerReceiver( receiver, intent);


    }


    private void loadFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new MusicPlayerFragment());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);
    }
}