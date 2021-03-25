package com.example.helloworld.Models.Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GPSEntity{
    @PrimaryKey(autoGenerate = true)
    int loc_id;

}
