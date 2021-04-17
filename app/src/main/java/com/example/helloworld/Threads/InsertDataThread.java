package com.example.helloworld.Threads;

import android.util.Log;

import com.example.helloworld.Activities.MainActivity;
import com.example.helloworld.ROOM.WifiDB;
import com.example.helloworld.ROOM.WifiEnity;

public class InsertDataThread extends Thread{

    MainActivity mobj;
    WifiEnity wifiEnity;
//    WifiDB wifiDB;

    private static final String TAG = "InsertDataThread";
    public InsertDataThread(WifiEnity wifiEnity, MainActivity mainActivity){
        this.mobj = mainActivity;
        this.wifiEnity = wifiEnity;
    }

    public void run(){
        try{
            mobj.wifiDB.dao().dataInsert(wifiEnity);
            Log.d(TAG, "wifi data inserted !!");

            Log.d("MainActivity", "wifi data inserted !!");

        }
        catch (Exception exception){
            Log.d(TAG, "run: "+exception);
        }

//        try {
//            mobj.InsertThreadFinished();
//        }catch (Exception e){
//            Log.d(TAG, "run: "+e);
//        }
    }
}
