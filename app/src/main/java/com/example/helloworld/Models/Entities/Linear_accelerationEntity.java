package com.example.helloworld.Models.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Linear_accelerationEntity{
    @PrimaryKey(autoGenerate = true)
    int lin_id;
    float x,y,z;
    Long time;

    public Linear_accelerationEntity(float x, float y, float z, Long time) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.time = time;
    }

    public int getLin_id() {
        return lin_id;
    }

    public void setLin_id(int lin_id) {
        this.lin_id = lin_id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

}


