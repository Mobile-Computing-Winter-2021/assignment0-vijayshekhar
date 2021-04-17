package com.example.helloworld.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";;
    private StringBuilder sb = new StringBuilder();
    private TextView tv;
    List<ScanResult> scanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final String CoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION;
        final String AccessWifi = Manifest.permission.ACCESS_WIFI_STATE;
        final String ChangeWifi = Manifest.permission.CHANGE_WIFI_STATE;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
        Button btn = (Button) findViewById(R. id. button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(CoarseLocation) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                }

                if (checkSelfPermission(AccessWifi) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_WIFI_STATE}, 123);
                }

                if (checkSelfPermission(ChangeWifi) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE}, 123);
                }

                LocationManager lman = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                boolean network_enabled = false;

                try
                {
                    network_enabled = lman.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch (Exception ex) {}

                if (!network_enabled)
                {
                    startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                }

                final WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                mWifiManager.setWifiEnabled(true);

                IntentFilter filter = new IntentFilter();
                filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

                registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {

                        List<ScanResult> results = mWifiManager.getScanResults();
                        final int Amount = results.size();

                        Log.d(TAG, "Wifi Scan Results Count: " + Amount);
                        Toast.makeText(MainActivity.this, "No. of results:  "+Amount, Toast.LENGTH_SHORT).show();

                        int num = 1;

                        while (num < Amount)
                        {
                            Log.d(TAG, "SSID  =  " + results.get(num));
                            Toast.makeText(MainActivity.this, "SSID  =  " + results.get(num).SSID , Toast.LENGTH_SHORT).show();

                            num = num+1;
                        }
                    }
                }, filter);

                mWifiManager.startScan();
            }
        });
    }
}

