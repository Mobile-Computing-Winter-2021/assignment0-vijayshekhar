package com.example.helloworld.Threads;

import android.util.Log;

import com.example.helloworld.Activities.MainActivity;
import com.example.helloworld.Activities.wardriveActivity;
import com.example.helloworld.ROOM.WifiDB;
import com.example.helloworld.ROOM.WifiEnity;
import com.example.helloworld.ROOM.WifiEntity1;

public class InsertDataThread extends Thread{

    private static final String TAG = "InsertDataThread";
    wardriveActivity wardrive;
    WifiEntity1 wifiEntity1;

    public InsertDataThread(wardriveActivity wardrive, WifiEntity1 wifiEntity1){
        this.wardrive = wardrive;
        this.wifiEntity1 = wifiEntity1;
    }

    public void run(){

        try{
            wardrive.wifiDB.dao().dataInsert1(wifiEntity1);
        }catch (Exception exception){
            Log.d(TAG, "run: "+exception);
        }
    }
}
