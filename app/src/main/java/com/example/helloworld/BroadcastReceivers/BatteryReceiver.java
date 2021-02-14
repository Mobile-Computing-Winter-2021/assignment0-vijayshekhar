package com.example.helloworld.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class BatteryReceiver extends BroadcastReceiver {

    //TODO: POWER_DISCONNECTED, BATTERY_OKAY

    @Override
    public void onReceive(Context context, Intent intent) {

        String action_received = intent.getAction();

//        if(Intent.ACTION_BATTERY_LOW.equalsIgnoreCase((String)intent.getAction())){
//
//           Toast toast =  Toast.makeText(context , "Battery low !!!", Toast.LENGTH_LONG);
//           Log.i("INFO_TAG", "Battery level low");
//           toast.show();
//        }
//        else{
//            Toast toast =  Toast.makeText(context , "Battery okay !!!", Toast.LENGTH_LONG);
//            Log.i("INFO_TAG" , "Battery level okay");
//            toast.show();
//        }

        if(action_received.contains("BATTERY_LOW")){

            Toast.makeText(context , "Battery is low ", Toast.LENGTH_LONG).show();
            Log.i("INFO_TAG" , "battery level low");
        }

        if(action_received.contains("BATTERY_OKAY")){

            Toast.makeText(context , "Battery is low ", Toast.LENGTH_LONG).show();
            Log.i("INFO_TAG" , "battery level low");
        }

        if(action_received.contains("POWER_DISCONNECTED")){

            Toast.makeText(context , "Power is disconnected  ", Toast.LENGTH_LONG).show();
            Log.i("INFO_TAG" , "Power Disconnected ");
        }


    }
}
