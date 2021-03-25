package com.example.helloworld.Models.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GPSEntity{
    @PrimaryKey(autoGenerate = true)
    int loc_id;
    float longitude,latitude;
    Long time;

    public GPSEntity(float longitude, float latitude, Long time) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
    }

    public int getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(int loc_id) {
        this.loc_id = loc_id;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
