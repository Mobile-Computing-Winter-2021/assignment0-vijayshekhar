package com.example.helloworld.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.Fragments.ListFragment;
import com.example.helloworld.R;
import com.example.helloworld.ROOM.WifiDB;
import com.example.helloworld.ROOM.WifiDao;
import com.example.helloworld.ROOM.WifiEnity;
import com.example.helloworld.Threads.APInfoRetrive;
import com.example.helloworld.Threads.InsertDataThread;
import com.example.helloworld.Threads.ScanWifiThread;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView scanStatus;
    Button scanbt,listbt,graphbt,wardrivebt,locationbt;
    public WifiDB wifiDB;
    public static List<ScanResult> scanResultsMain;




    final String CoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;
    final String AccessWifi = Manifest.permission.ACCESS_WIFI_STATE;
    final String ChangeWifi = Manifest.permission.CHANGE_WIFI_STATE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            initDb();

        scanStatus = findViewById(R.id.textView2);
        scanStatus.setText("Not started");

        scanbt = findViewById(R.id.button1);

        // check the permission granted or not.
        checkPermissionAccess();

        //scan button listener
        scanbt.setOnClickListener(v -> {

            // switch on the wifi sensor
            wifimanager.setWifiEnabled(true);

            // intent filter for broadcast receiver
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
            scanStatus.setText("Scanning ....");

            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {


                    scanResultsMain = wifimanager.getScanResults();
                    final int n = scanResultsMain.size();

                    Log.d(TAG, "Wifi Scan Results Count: " + n);
                    Toast.makeText(MainActivity.this, "No. of results:  " + n, Toast.LENGTH_SHORT).show();
                    scanStatus.setText("Scanning Complete!");

                    for (ScanResult res : scanResultsMain) {
                        int signalStrength = wifimanager.calculateSignalLevel(res.level);
                        Log.d(TAG, " SSID  =  " + res.SSID);
                        Log.d(TAG, " BSSID  =  " + res.BSSID);
                        Log.d(TAG, " level (RSS)  =  " + (res.level + 100));
                        Log.d(TAG, " strength  =  " + signalStrength);


                        Log.d(TAG, "\n \n");
                    }
                }

            }, intentFilter);
            wifimanager.startScan();
        });

        listbt = findViewById(R.id.button4);
        listbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, listActivity.class);

                Bundle args = new Bundle();
                args.putSerializable("scanlist",(Serializable)scanResultsMain);
                intent.putExtra("listresult",args);


                MainActivity.this.startActivity(intent);

            }
        });

        graphbt = findViewById(R.id.button3);
        graphbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, graphActivity.class);

                Bundle args = new Bundle();
                args.putSerializable("scanlist",(Serializable)scanResultsMain);
                intent.putExtra("listresult",args);

                MainActivity.this.startActivity(intent);

            }
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

    public void APInfoFetched(List<WifiEnity> wifiEnityList){
        Log.d(TAG, "APInfoFetched: inside");
        for(WifiEnity wifiEnity : wifiEnityList){
            Log.d(TAG, wifiEnity.getSsid() );
            Toast.makeText(MainActivity.this,"SSID:  "+ String.valueOf(wifiEnity.getSsid()), Toast.LENGTH_SHORT).show();
        }
    }

    public void checkPermissionAccess(){
        if (checkSelfPermission(CoarseLocation) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 55);
        }

        if (checkSelfPermission(AccessWifi) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_WIFI_STATE}, 55);
        }

        if (checkSelfPermission(ChangeWifi) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE}, 55);
        }

        LocationManager lman = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean network_enabled = false;

        try {
            network_enabled = lman.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {}

        if (!network_enabled) {
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
        }
    }


    public void InsertThreadFinished(){

    }

    public void initDb(){

        wifiDB = Room.databaseBuilder(MainActivity.this, WifiDB.class, "WifiDataDB" ).build();
    }



}

