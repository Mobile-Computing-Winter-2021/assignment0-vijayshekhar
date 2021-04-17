package com.example.helloworld.ROOM;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WifiEnity {

    @PrimaryKey(autoGenerate = true)
    int data_id;
    String ssid, bssid, roomName, orientation;
    int rssi, signalStrength;

    public WifiEnity(String ssid, String bssid, String roomName, String orientation, int rssi, int signalStrength) {
        this.ssid = ssid;
        this.bssid = bssid;
        this.roomName = roomName;
        this.orientation = orientation;
        this.rssi = rssi;
        this.signalStrength = signalStrength;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
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

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(int signalStrength) {
        this.signalStrength = signalStrength;
    }
}
