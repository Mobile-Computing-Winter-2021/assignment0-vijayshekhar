package com.example.helloworld.Threads;

import android.util.Log;

import com.example.helloworld.Activities.LocationActivity;
import com.example.helloworld.Activities.MainActivity;
import com.example.helloworld.ROOM.WifiDB;
import com.example.helloworld.ROOM.WifiEnity;
import com.example.helloworld.ROOM.WifiEntity1;

import java.util.List;

public class APInfoRetrive extends Thread{

    private final String TAG = "APInfoRetrive";
    List<WifiEntity1> data;
    LocationActivity locationActivity;
    WifiDB wifiDB;
    public APInfoRetrive(LocationActivity locationActivity, WifiDB wifiDB){
        this.locationActivity = locationActivity;
        this.wifiDB = wifiDB;
    }

    public void run(){

        try{
            data = wifiDB.dao().getAll1();
            Log.d(TAG, "run: data downloaded: "+data.size());
        }catch (Exception exception){
            Log.d(TAG, "run: " + exception);
        }

        try{
            locationActivity.wifiInfoFetched(data);
            Log.d(TAG, "run: data sent back: "+data.size());
        }
        catch (Exception exception){
            Log.d(TAG, "run: database retrival: "+exception);
        }
    }
}
