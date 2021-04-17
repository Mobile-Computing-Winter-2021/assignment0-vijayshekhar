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
import com.example.helloworld.Threads.InsertDataThread;
import com.example.helloworld.Threads.ScanWifiThread;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView tv;
    private EditText et;
    public WifiDB wifiDB;
    public List<ScanResult> scanResultsMain;




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

        // check the permission granted or not.
        checkPermissionAccess();

        //scan button listener
        scanbt.setOnClickListener(v -> {

            // switch on the wifi sensor
            wifimanager.setWifiEnabled(true);

            ScanWifiThread scanWifiThread = new ScanWifiThread(MainActivity.this, wifimanager);

            // intent filter for broadcast receiver
            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);


            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

//                    ScanWifiThread scanWifiThread1 = new ScanWifiThread(MainActivity.this, wifimanager);
//                    scanWifiThread1.start();

                    List<ScanResult> results = wifimanager.getScanResults();
                    final int n = results.size();

                    Log.d(TAG, "Wifi Scan Results Count: " + n);
                    Toast.makeText(MainActivity.this, "No. of results:  "+n, Toast.LENGTH_SHORT).show();

                    //fetching room name
                    String roomName = String.valueOf(et.getText());
                    String orientation  = getOrientation();

                    for (ScanResult res : results)
                    {   int signalStrength  = wifimanager.calculateSignalLevel(res.level);
                        Log.d(TAG, " SSID  =  " + res.SSID);
                        Log.d(TAG, " BSSID  =  " + res.BSSID);
                        Log.d(TAG, " level (RSS)  =  " + (res.level + 100));
                        Log.d(TAG, " strength  =  " + signalStrength);


                        Log.d(TAG, "\n \n");

                        //setting ap configurations
                        if(String.valueOf(res.SSID).contains("narayan_vani") || String.valueOf(res.SSID).contains("OPPO F11")){

                            Log.d(TAG, "onReceive: room insert");
                            Toast.makeText(MainActivity.this, " SSID = " + res.SSID + " level = " +signalStrength
                                    , Toast.LENGTH_SHORT).show();

                            WifiEnity wifiEnity = new WifiEnity(res.SSID, res.BSSID,roomName, orientation,res.level, signalStrength);

                            InsertDataThread insertDataThread = new InsertDataThread(wifiEnity , MainActivity.this);
                            insertDataThread.start();



                        }
                    }

                }
            }, filter);
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

