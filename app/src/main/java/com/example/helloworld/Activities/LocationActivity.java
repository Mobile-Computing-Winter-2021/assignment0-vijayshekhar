package com.example.helloworld.Activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.example.helloworld.R;
import com.example.helloworld.ROOM.WifiDB;

import com.example.helloworld.ROOM.WifiEntity1;
import com.example.helloworld.Threads.APInfoRetrive;
import com.example.helloworld.finalResult;


import java.util.List;
import java.util.*;
public class LocationActivity extends AppCompatActivity {


    List<ScanResult> scanResultsMain;
    String location = "";
    int rss1 = 0, rss2 = 0;
    static List<WifiEntity1> dataDownload;
    finalResult dataDownloadSorted[];

    private static final String TAG = "LocationActivity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        TextView tvRoom;
        EditText kedit;
        TextView scanStatus;
        Button bt1,bt2;
         tvRoom = (TextView) findViewById(R.id.textview);
         kedit = (EditText) findViewById(R.id.edittext1);
         scanStatus = findViewById(R.id.textView2);


         bt1 = (Button) findViewById(R.id.bt1);
         bt2 = (Button) findViewById(R.id.bt2);

        WifiManager wifimanager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        final WifiDB wifiDB = Room.databaseBuilder(LocationActivity.this, WifiDB.class, "WifiDataDB").build();

        // switch on the wifi sensor
        wifimanager.setWifiEnabled(true);

        // intent filter for broadcast receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        scanStatus.setText("Scan Status: Scanning ....");

        ///registeting the broadcast receiver
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
//                    Log.d(TAG, " SSID  =  " + res.SSID);
//                    Log.d(TAG, " BSSID  =  " + res.BSSID);
//                    Log.d(TAG, " level (RSS)  =  " + (res.level + 100));
//                    Log.d(TAG, " strength  =  " + signalStrength);

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


        bt1.setOnClickListener(v -> {
//            Toast.makeText(this, dataDownloadSorted[0].roomName, Toast.LENGTH_SHORT).show();
                location = dataDownloadSorted[0].roomName;
                tvRoom.setText(location);

        });

        bt2.setOnClickListener(v -> {
            int k = 4;

       try {
           if ("".equalsIgnoreCase(String.valueOf(kedit.getText()))){
               k = 4;
               Toast.makeText(this, "using default k =4", Toast.LENGTH_SHORT).show();
           }
           else{
               k =  Integer.parseInt(String.valueOf(kedit.getText()));
           }


               int arr [] = new int[k];
               String room [] = new String[k];
               for (int idx=0; idx<k ; idx++){
                   arr[idx] = dataDownloadSorted[idx].dist;
                   room[idx] = dataDownloadSorted[idx].roomName;
               }
               Log.d(TAG, "onCreate: 3");
               int nn = getNN(arr, k);

               for (int idx=0; idx<k ; idx++){
                   if(arr[idx] == nn)
//                    room[idx] = dataDownloadSorted[idx].roomName;
                       location = room[idx];
               }
               Log.d(TAG, "onCreate: 4");
               tvRoom.setText(location);

       }catch (Exception e){
           Log.d(TAG, "k button excep:  "+e);
       }
//

        });


    }

    class Sort {

        finalResult[] sort(finalResult arr[], int n) {

            //overriding the comparator to sort the custom list
            Arrays.sort(arr, new Comparator<finalResult>() {
                @Override
                public int compare(finalResult p1, finalResult p2) {
                    return p1.dist - p2.dist;
                }
            });

            //returning the sorted list
            return  arr;

        }
    }


    public void wifiInfoFetched(List<WifiEntity1> wifiEntity1s) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                dataDownload = wifiEntity1s;

                Log.d(TAG, "wifiInfoFetched: " + wifiEntity1s.size());
//                Toast.makeText(LocationActivity.this, "data size: param" + wifiEntity1s.size(), Toast.LENGTH_SHORT).show();

//                Toast.makeText(LocationActivity.this, "data size: global" + dataDownload.size(), Toast.LENGTH_SHORT).show();


                finalResult arr[] = new finalResult[wifiEntity1s.size()];

                int i = 0;
                for (WifiEntity1 res : wifiEntity1s) {

//                    arr[i].roomName = res.getRoomName();
//                    Log.d(TAG, "run: "+ res.getRoomName());

                    String locat = res.getRoomName();
                    int temp = (res.getRssi1() - rss1) * res.getRssi1() - rss1;


                    temp = temp + ((res.getRssi1() - rss1) * (res.getRssi1() - rss1));
                    arr[i] = new finalResult(locat , (int) Math.sqrt(temp));
                    i = i+1;

                }

                // sorting the distance list
                    // custom sorting helper class initialisation
                Sort obj = new Sort();

                // storing the sorted custom list for locating the room
                dataDownloadSorted = obj.sort(arr, wifiEntity1s.size());
//                Toast.makeText(LocationActivity.this, "room located" + dataDownloadSorted[0].dist+ "---->"+dataDownloadSorted[1].dist, Toast.LENGTH_SHORT).show();
                Toast.makeText(LocationActivity.this, "calibration completed!", Toast.LENGTH_SHORT).show();


                Log.d(TAG, "wifiInfoFetched: sorted"+dataDownloadSorted);

            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
     int getNN(int[] list, int size)
    {
        //to get index of the room name
        int closest = 0;

        int countSize =   Arrays.stream(list).max().getAsInt() + 1;


        int[] count = new int[countSize];
        for (int j = 0; j < countSize; j++)
            count[j] = 0;

        // the frequency of each distance from the current rss values (coordinates)
        for (int j = 0; j < size; j++)
            count[list[j]]++;
        //closest will be associated with the room location (name)
        int k = count[0];
        for (int j = 1; j < countSize; j++) {
            if (count[j] > k) {
                k = count[j];
                closest = j;
            }
        }

        return closest;
    }

}