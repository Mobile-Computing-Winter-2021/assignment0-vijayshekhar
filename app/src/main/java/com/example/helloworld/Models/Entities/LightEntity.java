package com.example.helloworld.Models.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LightEntity{
    @PrimaryKey(autoGenerate = true)
    int light_id;
    float light_aux;
    Long time;

    public LightEntity(float light_aux, Long time) {
        this.light_aux = light_aux;
        this.time = time;
    }

    public int getLight_id() {
        return light_id;
    }

    public void setLight_id(int light_id) {
        this.light_id = light_id;
    }

    public float getLight_aux() {
        return light_aux;
    }

    public void setLight_aux(float light_aux) {
        this.light_aux = light_aux;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}

