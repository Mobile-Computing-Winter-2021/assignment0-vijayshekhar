package com.example.helloworld.Threads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.helloworld.Activities.MainActivity;

import java.util.List;

public class ScanWifiThread extends Thread {

    MainActivity mainActivity;
    WifiManager wifiManager;
    public List<ScanResult> scanResultsThread;
    private static final String TAG = "ScanWifiThread";
    
    public ScanWifiThread(MainActivity mainActivity , WifiManager wifiManager){
        this.mainActivity = mainActivity;
        this.wifiManager = wifiManager;
    }

    public void run(){

        try{
            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);


            mainActivity.registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                     mainActivity.scanResultsMain = wifiManager.getScanResults();
                    scanResultsThread = wifiManager.getScanResults();

                    for (ScanResult res : scanResultsThread) {
                        int signalLevel = wifiManager.calculateSignalLevel(res.level);
                        Log.d(TAG, " SSID  =  " + res.SSID);
                        Log.d(TAG, " BSSID  =  " + res.BSSID);
                        Log.d(TAG, " level (RSS)  =  " + (res.level + 100));
                        Log.d(TAG, " strength  =  " + signalLevel);

                        Log.d(TAG, "\n \n"); }
                }

            },filter);


    }catch (Exception exception){

            Log.d(TAG, "run: "+ exception);
        
        }




    }


}
