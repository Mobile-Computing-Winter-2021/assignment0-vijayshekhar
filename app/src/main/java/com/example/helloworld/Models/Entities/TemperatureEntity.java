package com.example.helloworld.Models.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TemperatureEntity{

    @PrimaryKey(autoGenerate = true)
    int temp_id;
    float temp;
    Long time;

    public TemperatureEntity(float temp, Long time) {
        this.temp = temp;
        this.time = time;
    }

    public int getTemp_id() {
        return temp_id;
    }

    public void setTemp_id(int temp_id) {
        this.temp_id = temp_id;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}


