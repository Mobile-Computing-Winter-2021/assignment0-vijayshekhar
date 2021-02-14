package com.example.helloworld.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class BatteryReceiver extends BroadcastReceiver {

    // Done : POWER_DISCONNECTED, BATTERY_OKAY

    @Override
    public void onReceive(Context context, Intent intent) {

        String action_received = intent.getAction();

        if(action_received.contains("BATTERY_LOW")){

            Toast.makeText(context , "Battery is low!! ", Toast.LENGTH_LONG).show();
            Log.d("INFO_TAG" , "battery level low");
        }

        if(action_received.contains("BATTERY_OKAY")){

            Toast.makeText(context , "Battery is okay! ", Toast.LENGTH_LONG).show();
            Log.d("INFO_TAG" , "battery level okay");
        }

        if(action_received.contains("POWER_DISCONNECTED")){

            Toast.makeText(context , "Power is disconnected! ", Toast.LENGTH_LONG).show();
            Log.d("INFO_TAG" , "Power Disconnected ");
        }


    }
}
