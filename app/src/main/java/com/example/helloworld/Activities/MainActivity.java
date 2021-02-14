package com.example.helloworld.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.IntentFilter;
import android.os.Bundle;


import com.example.helloworld.BroadcastReceivers.BatteryReceiver;
import com.example.helloworld.Fragments.MusicPlayerFragment;
import com.example.helloworld.R;

public class MainActivity extends AppCompatActivity {

    BatteryReceiver receiver = new BatteryReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment();

//        IntentFilter intent = new IntentFilter();
////        intent.addAction(BatteryManager.EXTRA_BATTERY_LOW);
//        intent.addAction("android.intent.action.BATTERY_OKAY");
//        intent.addAction("android.intent.action.BATTERY_LOW");
//        intent.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
//
//        registerReceiver( receiver, intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intent = new IntentFilter();
        intent.addAction("android.intent.action.BATTERY_OKAY");
        intent.addAction("android.intent.action.BATTERY_LOW");
        intent.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");

        registerReceiver( receiver, intent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(receiver);
    }

    private void loadFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new MusicPlayerFragment());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }




}