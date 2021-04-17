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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;
import com.example.helloworld.ROOM.WifiDB;
import com.example.helloworld.ROOM.WifiDao;
import com.example.helloworld.ROOM.WifiEnity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView tv;
    private EditText et;
    WifiDB wifiDB;



    final String CoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;
    final String AccessWifi = Manifest.permission.ACCESS_WIFI_STATE;
    final String ChangeWifi = Manifest.permission.CHANGE_WIFI_STATE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

            initDb();

        et = (EditText) findViewById(R.id.editText);

        Button scanbt = (Button) findViewById(R. id.button);

        //scan button listener
        scanbt.setOnClickListener(v -> {

            // check the permission granted or not.
            checkPermissionAccess();

            // switch on the wifi sensor
            wifimanager.setWifiEnabled(true);

            // intent filter for broadcast receiver
            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);


            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    List<ScanResult> results = wifimanager.getScanResults();
                    final int n = results.size();

                    Log.d(TAG, "Wifi Scan Results Count: " + n);
                    Toast.makeText(MainActivity.this, "No. of results:  "+n, Toast.LENGTH_SHORT).show();

                    //fetching room name
                    String rname = String.valueOf(et.getText());
                    String orientation  = getOrientation();

                    for (ScanResult res : results)
                    {   int sigstren  = wifimanager.calculateSignalLevel(res.level);
                        Log.d(TAG, " SSID  =  " + res.SSID);
                        Log.d(TAG, " BSSID  =  " + res.BSSID);
                        Log.d(TAG, " level (RSS)  =  " + (res.level + 100));
                        Log.d(TAG, " strength  =  " + sigstren);


                        Log.d(TAG, "\n \n");

                        //setting ap configurations
                        if(String.valueOf(res.SSID).contains("narayan_vani")){

                            Log.d(TAG, "onReceive: room insert");
                            Toast.makeText(MainActivity.this, " SSID = " + res.SSID + " level = " +sigstren
                                    , Toast.LENGTH_SHORT).show();

                            new Thread(new Runnable(){
                                @Override
                                public void run() {
                                    WifiEnity wifiEnity = new WifiEnity(res.SSID, res.BSSID,rname, orientation,res.level, sigstren);
                                    wifiDB.dao().dataInsert(wifiEnity);

                                    Log.d(TAG, "onReceive: room insert ---> onUIThread");
                                    et.setText("on UI thread");
                                }
                            }).start();
                        }
                    }

                }
            }, filter);
            wifimanager.startScan();

        });

    }


    private String getOrientation(){
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return "landscape";
        }
        else {
            return  "portrait";
        }
    }

    private void checkPermissionAccess(){
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


    private void initDb(){
//        wifiLocDatabase = Room.databaseBuilder(MainActivity.this , WifiLocDatabase.class , "SensorDB").build();
        wifiDB = Room.databaseBuilder(MainActivity.this, WifiDB.class, "WifiDataDB" ).build();
    }



}

