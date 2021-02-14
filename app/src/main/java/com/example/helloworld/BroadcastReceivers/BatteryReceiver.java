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
        if(Intent.ACTION_BATTERY_LOW.equalsIgnoreCase((String)intent.getAction())){

           Toast toast =  Toast.makeText(context , "Battery low !!!", Toast.LENGTH_LONG);
           Log.i("INFO_TAG", "Battery level low");
           toast.show();
        }
        else{
            Toast toast =  Toast.makeText(context , "Battery okay !!!", Toast.LENGTH_LONG);
            Log.i("INFO_TAG" , "Battery level okay");
            toast.show();
        }





    }
}
