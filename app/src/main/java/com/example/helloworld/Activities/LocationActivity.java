package com.example.helloworld.Activities;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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
            Toast.makeText(this, dataDownloadSorted[0].roomName, Toast.LENGTH_SHORT).show();
                location = dataDownloadSorted[0].roomName;
                tvRoom.setText(location);

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

        bt2.setOnClickListener(v -> {
//            EditText kedit = (EditText) findViewById(R.id.edittext1);
            Log.d(TAG, "onCreate: 1");
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

    class Compare {

        finalResult[] compare(finalResult arr[], int n) {
            // Comparator to sort the pair according to second element
            Arrays.sort(arr, new Comparator<finalResult>() {
                @Override
                public int compare(finalResult p1, finalResult p2) {
                    return p1.dist - p2.dist;
                }
            });

//            return arr[n - 1].roomName;
            return  arr;

        }
    }


    public void wifiInfoFetched(List<WifiEntity1> wifiEntity1s) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                dataDownload = wifiEntity1s;

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

                }

//                for (int j=0; j<wifiEntity1s.size();j++){
//                    Log.d(TAG, "room name: "+arr[j].roomName+" distance:  "+arr[j].dist);
//                }

                Compare obj = new Compare();

//                location = obj.compare(arr, wifiEntity1s.size());
                dataDownloadSorted = obj.compare(arr, wifiEntity1s.size());
                Toast.makeText(LocationActivity.this, "room located" + dataDownloadSorted[0].dist+ "---->"+dataDownloadSorted[1].dist, Toast.LENGTH_SHORT).show();


                Log.d(TAG, "wifiInfoFetched: sorted"+dataDownloadSorted);



//                for (int j=0; j<wifiEntity1s.size();j++){
//                    Log.d(TAG, "room name: "+dataDownloadSorted[j].roomName+" distance:  "+dataDownloadSorted[j].dist);
//                }
            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
     int getNN(int[] a, int n)
    {

        int max = Arrays.stream(a).max().getAsInt();

        int t = max + 1;
        int[] count = new int[t];
        for (int i = 0; i < t; i++)
        {
            count[i] = 0;
        }

        for (int i = 0; i < n; i++)
        {
            count[a[i]]++;
        }

        int mode = 0;
        int k = count[0];
        for (int i = 1; i < t; i++)
        {
            if (count[i] > k)
            {
                k = count[i];
                mode = i;
            }
        }

        return mode;
    }

}