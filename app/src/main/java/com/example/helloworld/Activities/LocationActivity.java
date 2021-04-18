package com.example.helloworld.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.Fragments.ListFragment;
import com.example.helloworld.R;
import com.example.helloworld.ROOM.WifiDB;
import com.example.helloworld.ROOM.WifiDao;
import com.example.helloworld.ROOM.WifiEnity;
import com.example.helloworld.ROOM.WifiEntity1;
import com.example.helloworld.Threads.APInfoRetrive;
import com.example.helloworld.finalResult;

import org.w3c.dom.Text;

import java.sql.Array;
import java.sql.RowId;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
public class LocationActivity extends AppCompatActivity {


    List<ScanResult> scanResultsMain;
    String location = "";
    int rss1 = 0, rss2 = 0;
    static List<WifiEntity1> dataDownload;
    private static final String TAG = "LocationActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        TextView tvRoom = (TextView) findViewById(R.id.textView);
        EditText kedit = (EditText) findViewById(R.id.edittext1);
        TextView scanStatus = findViewById(R.id.textView2);


        Button bt1 = (Button) findViewById(R.id.button1);
        Button bt2 = (Button) findViewById(R.id.button2);

        WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        final WifiDB wifiDB = Room.databaseBuilder(LocationActivity.this, WifiDB.class, "WifiDataDB").build();

        // switch on the wifi sensor
        wifimanager.setWifiEnabled(true);

        // intent filter for broadcast receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        scanStatus.setText("Scan Status: Scanning ....");

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                scanResultsMain = wifimanager.getScanResults();
                final int n = scanResultsMain.size();

                Log.d(TAG, "Wifi Scan Results Count: " + n);
                Toast.makeText(LocationActivity.this, "No. of results:  " + n, Toast.LENGTH_SHORT).show();
                scanStatus.setText("Scan Status: Scanning Complete!");

                for (ScanResult res : scanResultsMain) {
                    int signalStrength = wifimanager.calculateSignalLevel(res.level);
                    Log.d(TAG, " SSID  =  " + res.SSID);
                    Log.d(TAG, " BSSID  =  " + res.BSSID);
                    Log.d(TAG, " level (RSS)  =  " + (res.level + 100));
                    Log.d(TAG, " strength  =  " + signalStrength);

                    if (res.SSID.contains("naraya_vani")) {
                        rss1 = res.level + 100;
                    }
                    if (res.SSID.contains("uamsef")) {
                        rss2 = res.level + 100;
                    }

                }
            }

        }, intentFilter);
        wifimanager.startScan();


        //thread to download data from room database
        APInfoRetrive apInfoRetrive = new APInfoRetrive(LocationActivity.this, wifiDB);
        apInfoRetrive.start();
//            Toast.makeText(this, "data size: "+dataDownload.size(), Toast.LENGTH_SHORT).show();


//            finalResult arr[] = new finalResult[dataDownload.size()];
//            int i=0;
//            for (WifiEntity1 res: dataDownload){
//                arr[i].roomName = res.getRoomName();
//                int temp = (res.getRssi1() - rss1) * res.getRssi1() - rss1;
//                temp =  temp + ((res.getRssi1() - rss1) * (res.getRssi1() - rss1));
//                arr[i].dist = (int) Math.sqrt(temp);
//            }
//            Compare obj = new Compare();
//
//            location = obj.compare(arr, dataDownload.size());
//            Toast.makeText(this, "room location--->"+location, Toast.LENGTH_SHORT).show();


        bt1.setOnClickListener(v -> {
        TextView tv = findViewById(R.id.textview);
        tv.setText(location);

//            WifiDB wifiDB = Room.databaseBuilder(LocationActivity.this, WifiDB.class, "WifiDataDB" ).allowMainThreadQueries().build();
//            List<WifiEntity1> dataDownload=  wifiDB.dao().getAll1();
//            APInfoRetrive apInfoRetrive = new APInfoRetrive(LocationActivity.this, wifiDB);
//            apInfoRetrive.start();
//            Toast.makeText(this, "data size: "+dataDownload.size(), Toast.LENGTH_SHORT).show();
//
//
//            finalResult arr[] = new finalResult[dataDownload.size()];
//            int i=0;
//            for (WifiEntity1 res: dataDownload){
//                arr[i].roomName = res.getRoomName();
//                int temp = (res.getRssi1() - rss1) * res.getRssi1() - rss1;
//                temp =  temp + ((res.getRssi1() - rss1) * (res.getRssi1() - rss1));
//                arr[i].dist = (int) Math.sqrt(temp);
//            }
//            Compare obj = new Compare();
//
//            location = obj.compare(arr, dataDownload.size());
//            Toast.makeText(this, "room location--->"+location, Toast.LENGTH_SHORT).show();

        });


    }

    class Compare {

        String compare(finalResult arr[], int n) {
            // Comparator to sort the pair according to second element
            Arrays.sort(arr, new Comparator<finalResult>() {
                @Override
                public int compare(finalResult p1, finalResult p2) {
                    return p1.dist - p2.dist;
                }
            });

            return arr[n - 1].roomName;

        }
    }


    public void wifiInfoFetched(List<WifiEntity1> wifiEntity1s) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

//                this.dataDownload = wifiEntity1s;

                Log.d(TAG, "wifiInfoFetched: " + wifiEntity1s.size());
                Toast.makeText(LocationActivity.this, "data size: param" + wifiEntity1s.size(), Toast.LENGTH_SHORT).show();

//                Toast.makeText(LocationActivity.this, "data size: global" + dataDownload.size(), Toast.LENGTH_SHORT).show();


                finalResult arr[] = new finalResult[wifiEntity1s.size()];
//                arr = WifiEntity1;
                int i = 0;
                for (WifiEntity1 res : wifiEntity1s) {
                    ;
//                    arr[i].roomName = res.getRoomName();
//                    Log.d(TAG, "run: "+ res.getRoomName());

                    String locat = res.getRoomName();
                    int temp = (res.getRssi1() - rss1) * res.getRssi1() - rss1;


                    temp = temp + ((res.getRssi1() - rss1) * (res.getRssi1() - rss1));
                    arr[i] = new finalResult(locat , (int) Math.sqrt(temp));
                    i = i+1;
//                    arr[i].dist = (int) Math.sqrt(temp);
                }
                Compare obj = new Compare();

                location = obj.compare(arr, wifiEntity1s.size());
                Toast.makeText(LocationActivity.this, "room located" + location, Toast.LENGTH_SHORT).show();
            }


        });


    }

}