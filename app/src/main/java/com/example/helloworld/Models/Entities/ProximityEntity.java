package com.example.helloworld.Models.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ProximityEntity{

    @PrimaryKey(autoGenerate = true)
    int prox_id;
    float dist;
    Long time;

    public ProximityEntity(float dist, Long time) {
        this.dist = dist;
        this.time = time;
    }

    public int getProx_id() {
        return prox_id;
    }

    public void setProx_id(int prox_id) {
        this.prox_id = prox_id;
    }

    public float getDist() {
        return dist;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}

