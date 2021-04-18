package com.example.helloworld.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;
import com.example.helloworld.ROOM.WifiDB;
import com.example.helloworld.ROOM.WifiEntity1;
import com.example.helloworld.Threads.InsertDataThread;

import java.util.List;

public class wardriveActivity extends AppCompatActivity {

    EditText scanStatus,roomName;
    Button scanbt;
    public WifiDB wifiDB;

    List<ScanResult> scanResults;
    private static final String TAG = "wardriveActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardrive);

        final WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        initDb();
        scanStatus = findViewById(R.id.edittext2);
        roomName = findViewById(R.id.edittext1);
        wifimanager.setWifiEnabled(true);
        scanbt = findViewById(R.id.button);

        scanbt.setOnClickListener(v -> {

            // intent filter for broadcast receiver
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            scanStatus.setText("Scanning ....");

            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {


                    scanResults = wifimanager.getScanResults();
                    final int n = scanResults.size();

                    Log.d(TAG, "Wifi Scan Results Count: " + n);


                    int rss1=0,rss2=0;

                    for (ScanResult res : scanResults) {
                        int signalStrength = wifimanager.calculateSignalLevel(res.level);
                        Log.d(TAG, " SSID  =  " + res.SSID);
                        Log.d(TAG, " BSSID  =  " + res.BSSID);
                        Log.d(TAG, " level (RSS)  =  " + (res.level + 100));
                        Log.d(TAG, " strength  =  " + signalStrength);

                        if(res.SSID.contains("narayan_vani")){
                            rss1 = res.level + 100;
                        }
                        if(res.SSID.contains("uamsef")){
                            rss2 = res.level + 100;
                        }

                        Log.d(TAG, "\n \n");
                    }
                    WifiEntity1 wifiEntity1 = new WifiEntity1(String.valueOf(roomName.getText()), getOrientation() , rss1,rss2);
                    InsertDataThread insertDataThread = new InsertDataThread(wardriveActivity.this, wifiEntity1);
                    insertDataThread.start();

                    scanStatus.setText("Scanning Complete!");
                }

            }, intentFilter);
            wifimanager.startScan();

        });




    }

    public String getOrientation(){
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return "landscape";
        }
        else {
            return  "portrait";
        }
    }

    public void initDb(){

        wifiDB = Room.databaseBuilder(wardriveActivity.this, WifiDB.class, "WifiDataDB" ).build();
    }
}