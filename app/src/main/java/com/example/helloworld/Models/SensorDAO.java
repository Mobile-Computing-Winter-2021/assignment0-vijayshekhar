package com.example.helloworld.Models;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.helloworld.Models.Entities.AccelerometerEntity;
import com.example.helloworld.Models.Entities.TemperatureEntity;

import java.util.List;

@Dao
public interface SensorDAO {

    @Insert
    public void accDataInsert(AccelerometerEntity accelerometerEntity);

    @Query("SELECT * FROM AccelerometerEntity")
    List<AccelerometerEntity> getAccData();


    @Query("SELECT * FROM AccelerometerEntity where datetime(time) >= datetime('now','-1 Hour' ) ")
    List<AccelerometerEntity> getAccDataPast1Hour();


    @Insert
    public void tempDataInsert(TemperatureEntity temperatureEntity);

    @Query("SELECT * FROM TemperatureEntity")
    List<TemperatureEntity> getTempData();


    @Query("SELECT * FROM TemperatureEntity where datetime(time) >= datetime('now','-1 Hour' ) ")
    List<TemperatureEntity> getTempDataPast1Hour();
}

