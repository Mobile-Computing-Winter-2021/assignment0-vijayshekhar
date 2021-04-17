package com.example.helloworld.Threads;

import android.util.Log;

import com.example.helloworld.Activities.MainActivity;
import com.example.helloworld.ROOM.WifiEnity;

import java.util.List;

public class APInfoRetrive extends Thread{

    private static final String TAG = "APInfoRetrive";
    List<WifiEnity> data;
    MainActivity mainActivity;
    public APInfoRetrive(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    public void run(){

        try{
            data =  mainActivity.wifiDB.dao().getAll();
        }catch (Exception exception){
            Log.d(TAG, "run: " + exception);
        }

        try{
            mainActivity.APInfoFetched(data);
        }
        catch (Exception exception){
            Log.d(TAG, "run: database retrival: "+exception);
        }
    }
}
