package com.example.helloworld.ROOM;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WifiEntity1 {
    @PrimaryKey(autoGenerate = true)
    int data_id;
    String roomName,orientation;
    int rssi1, rssi2;

    public WifiEntity1(String roomName, String orientation, int rssi1, int rssi2) {
        this.roomName = roomName;
        this.orientation = orientation;
        this.rssi1 = rssi1;
        this.rssi2 = rssi2;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public int getRssi1() {
        return rssi1;
    }

    public void setRssi1(int rssi1) {
        this.rssi1 = rssi1;
    }

    public int getRssi2() {
        return rssi2;
    }

    public void setRssi2(int rssi2) {
        this.rssi2 = rssi2;
    }
}
